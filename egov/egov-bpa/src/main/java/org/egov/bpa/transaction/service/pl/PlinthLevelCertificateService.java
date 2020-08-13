package org.egov.bpa.transaction.service.pl;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SUBMITTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_TS_INS_INITIATED;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_CANCELAPPLICATION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;

import java.util.Date;
import java.util.List;

import org.egov.bpa.autonumber.PLCertificateNumberGenerator;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.repository.ApplicationBpaRepository;
import org.egov.bpa.transaction.repository.pl.PlinthLevelCertificateRepository;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.utils.BpaUtils;
import org.egov.commons.entity.Source;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlinthLevelCertificateService {
	
	@Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
	@Autowired
	private PlinthLevelCertificateRepository plinthLevelCertificateRepository;
	@Autowired
    private AutonumberServiceBeanResolver beanResolver;
	@Autowired
	private ApplicationBpaRepository applicationBpaRepository;
	@Autowired
	private ApplicationBpaService applicationBpaService;
	
	public List<PlinthLevelCertificate> findByPermitNumber(String permitNumber) {
        return plinthLevelCertificateRepository.findByPermitNumber(permitNumber);
    }
	
	public PlinthLevelCertificate findByApplicationNumber(final String applicationNumber) {
        return plinthLevelCertificateRepository.findByApplicationNumber(applicationNumber);
    }
	
	public PlinthLevelCertificate findById(Long id) {
        return plinthLevelCertificateRepository.findOne(id);
    }

    public List<PlinthLevelCertificate> findByStatusListOrderByCreatedDate(List<BpaStatus> listOfBpaStatus) {
        return plinthLevelCertificateRepository.findByStatusListOrderByCreatedDateAsc(listOfBpaStatus);
    }
    
    public BpaApplication findByBpaApplicationNumber(final String applicationNumber) {
        return applicationBpaRepository.findByApplicationNumber(applicationNumber);
    }
	
	private String generatePLCertificateNumber() {
        final PLCertificateNumberGenerator plCertificateNumberGenerator = beanResolver
                .getAutoNumberServiceFor(PLCertificateNumberGenerator.class);
        return plCertificateNumberGenerator.generatePLCertificateNumber();
    }

    private void setSource(final PlinthLevelCertificate pl) {
        if (bpaUtils.logedInuseCitizenOrBusinessUser())
            pl.setSource(Source.CITIZENPORTAL);
        else
            pl.setSource(Source.SYSTEM);
    }
    
    @Transactional
    public void savePlinthLevelCertificate(PlinthLevelCertificate plinthLevelCertificate) {
    	plinthLevelCertificateRepository.saveAndFlush(plinthLevelCertificate);
    }
    
    @Transactional
    public PlinthLevelCertificate saveOrUpdate(final PlinthLevelCertificate pl, final WorkflowBean wfBean) {
    	if (pl.getApplicationDate() == null)
    		pl.setApplicationDate(new Date());
        setSource(pl);
        if (pl.getApplicationNumber() == null)
        	pl.setApplicationNumber(applicationNumberGenerator.generate());
        pl.setApplicationType("Plinth Level Certificate");
        
        if (wfBean.getWorkFlowAction() != null && wfBean.getWorkFlowAction().equals(WF_LBE_SUBMIT_BUTTON)) {
            final BpaStatus bpaStatus = applicationBpaService.getStatusByCodeAndModuleType(APPLICATION_STATUS_SUBMITTED);
            pl.setStatus(bpaStatus);
        } else {
            final BpaStatus bpaStatus = applicationBpaService.getStatusByCodeAndModuleType(APPLICATION_STATUS_CREATED);
            pl.setStatus(bpaStatus);
        }
        
        if (wfBean.getWorkFlowAction() != null && wfBean.getWorkFlowAction().equals(WF_LBE_SUBMIT_BUTTON)
                && !bpaUtils.logedInuseCitizenOrBusinessUser()) {

            WorkFlowMatrix wfMatrix = null;
            wfMatrix = bpaUtils.getWfMatrixByCurrentState(pl.getStateType(), WF_NEW_STATE, pl.getPlinthLevelCertificateType());
            String currentState = WF_NEW_STATE;
            if (wfMatrix != null)
                wfBean.setApproverPositionId(bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                        bpaUtils.getBoundaryForWorkflow(pl.getParent().getSiteDetail().get(0)).getId()));
            wfBean.setCurrentState(currentState);
            bpaUtils.redirectToBpaWorkFlowForPL(pl, wfBean);

        } else if (wfBean.getWorkFlowAction() != null
                && WF_CANCELAPPLICATION_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            pl.setStatus(applicationBpaService.getStatusByCodeAndModuleType(APPLICATION_STATUS_CANCELLED));
        }
        PlinthLevelCertificate plResult = plinthLevelCertificateRepository.saveAndFlush(pl);
        plinthLevelCertificateRepository.save(plResult); 
        return plResult;
    }
    
    @Transactional
    public PlinthLevelCertificate update(final PlinthLevelCertificate pl, final WorkflowBean wfBean) {
        if (WF_APPROVE_BUTTON.equals(wfBean.getWorkFlowAction())) {
            pl.setApprovalDate(new Date());
            pl.setApproverPosition(pl.getState().getOwnerPosition());
            pl.setApproverUser(pl.getState().getOwnerUser());
            pl.setPlinthLevelCertificateNumber(generatePLCertificateNumber());
        }
//        if (WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
//                || WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
//                || APPLICATION_STATUS_REJECTED.equalsIgnoreCase(oc.getStatus().getCode())
//                || APPLICATION_STATUS_NOCUPDATED.equals(oc.getStatus().getCode())) {
//            buildRejectionReasons(oc);
//        }
        
        if (APPLICATION_STATUS_TS_INS_INITIATED.equals(pl.getStatus().getCode())) {
            pl.setIsSurveyorInspectionRequire(false);
        }
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            bpaUtils.redirectToBpaWorkFlowForPL(pl, wfBean);
        plinthLevelCertificateRepository.saveAndFlush(pl);
        return pl;
    }
}
