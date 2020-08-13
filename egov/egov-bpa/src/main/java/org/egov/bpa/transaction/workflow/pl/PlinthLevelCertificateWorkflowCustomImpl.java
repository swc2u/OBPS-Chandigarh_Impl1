package org.egov.bpa.transaction.workflow.pl;

import static org.egov.bpa.utils.BpaConstants.NATURE_OF_WORK_PL;
import static org.egov.bpa.utils.BpaConstants.PL_CREATION_PENDING;

import java.util.Date;
import java.util.List;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.dto.BpaStateInfo;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.workflow.BpaWorkFlowService;
import org.egov.bpa.utils.BpaAppConfigUtil;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.pims.commons.Position;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

public abstract class PlinthLevelCertificateWorkflowCustomImpl implements PlinthLevelCertificateWorkflowCustom{
	private static final Logger LOG = LoggerFactory.getLogger(PlinthLevelCertificateWorkflowCustomImpl.class);
	
	@Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    @Qualifier("workflowService")
    private SimpleWorkflowService<BpaApplication> bpaApplicationWorkflowService;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private BpaWorkFlowService bpaWorkFlowService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private BpaAppConfigUtil bpaAppConfigUtil;
    
    @Autowired
    public PlinthLevelCertificateWorkflowCustomImpl() {}
    
    @Override
    @Transactional
    public void createCommonWorkflowTransition(final PlinthLevelCertificate pl, final WorkflowBean wfBean) {
    	if (LOG.isDebugEnabled())
            LOG.debug(" Create WorkFlow Transition Started  ...");
        final User user = securityUtils.getCurrentUser();
        final DateTime currentDate = new DateTime();
        Position pos = null;
        Assignment wfInitiator = null;
        User ownerUser = null;
        if (pl.getCreatedBy() != null)
            wfInitiator = bpaWorkFlowService.getWorkFlowInitiator(pl.getState(), pl.getCreatedBy());

        if (wfBean.getApproverPositionId() != null && wfBean.getApproverPositionId() > 0) {
            pos = positionMasterService.getPositionById(wfBean.getApproverPositionId());
            ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
        }
        WorkFlowMatrix wfMatrix;
        if (pl.getCurrentState() == null) {
            String pendingAction = null;
            pendingAction = PL_CREATION_PENDING;
            if (bpaUtils.applicationInitiatedByNonEmployee(pl.getCreatedBy()))
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(pl.getStateType(), null,
                        null, wfBean.getAdditionalRule(), BpaConstants.WF_NEW_STATE, pendingAction);
            else
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(pl.getStateType(), null,
                        null, wfBean.getAdditionalRule(), BpaConstants.WF_CREATED_STATE, pendingAction);

            if (wfMatrix != null) {
                if (pos == null) {
                    SiteDetail siteDetail = pl.getParent().getSiteDetail().get(0);
					pos = bpaUtils.getUserPositionByZone(wfMatrix.getNextDesignation(),
							bpaUtils.getBoundaryForWorkflow(siteDetail).getId());
                    List<Assignment> assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date());
                    if (!assignments.isEmpty())
                        ownerUser = assignments.get(0).getEmployee();
                }
                pl.setStatus(getStatusByCurrentMatrixStatus(wfMatrix));
                pl.transition().start()
                        .withSLA(bpaWorkFlowService.calculateDueDate(bpaAppConfigUtil.getSlaOcApplication()))
                        .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                        .withOwner(ownerUser)
                        .withComments(wfBean.getApproverComments())
                        .withInitiator(wfInitiator != null ? wfInitiator.getPosition() : null)
                        .withStateValue(wfMatrix.getNextState()).withDateInfo(new Date()).withOwner(pos)
                        .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_PL);
            }
        } else if (BpaConstants.WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
        	wfMatrix = bpaApplicationWorkflowService.getWfMatrix(pl.getStateType(), null, wfBean.getAmountRule(),
                    wfBean.getAdditionalRule(), pl.getCurrentState().getValue(), pl.getState().getNextAction());
            if (wfMatrix != null) {
                pl.setStatus(getStatusByCurrentMatrixStatus(wfMatrix));
                pl.transition().progressWithStateCopy()
                        .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                        .withComments(wfBean.getApproverComments())
                        .withStateValue(wfMatrix.getNextState()).withDateInfo(currentDate.toDate())
                        .withOwner(pos).withOwner(ownerUser)
                        .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_PL);
            }
        } else if (BpaConstants.WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            wfMatrix = bpaApplicationWorkflowService.getWfMatrix(pl.getStateType(), null,
                    null, wfBean.getAdditionalRule(), BpaConstants.WF_REJECT_STATE, null);
            pl.setStatus(getStatusByPassingCode(BpaConstants.WF_REJECT_STATE));
            pl.transition().progressWithStateCopy()
                    .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                    .withComments(wfBean.getApproverComments())
                    .withStateValue(BpaConstants.WF_REJECT_STATE).withDateInfo(currentDate.toDate())
                    .withOwner(pos).withOwner(ownerUser)
                    .withNextAction(wfMatrix.getNextAction())
                    .withNatureOfTask(NATURE_OF_WORK_PL);
        } else if (BpaConstants.GENERATEREJECTNOTICE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            pl.setStatus(getStatusByPassingCode(BpaConstants.APPLICATION_STATUS_CANCELLED));
            pl.transition().end()
                    .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                    .withComments(wfBean.getApproverComments()).withDateInfo(currentDate.toDate())
                    .withNextAction(BpaConstants.WF_END_STATE).withNatureOfTask(BpaConstants.NATURE_OF_WORK_OC);
        } else {
            Assignment approverAssignment = bpaWorkFlowService.getApproverAssignment(pos);
            if (approverAssignment == null)
                approverAssignment = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0);
            if (BpaConstants.APPLICATION_STATUS_APPROVED.equalsIgnoreCase(pl.getStatus().getCode())
                    && !BpaConstants.APPLICATION_STATUS_RECORD_APPROVED.equalsIgnoreCase(pl.getState().getValue())) {
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(pl.getStateType(), null,
                        wfBean.getAmountRule(), wfBean.getAdditionalRule(),
                        pl.getCurrentState().getValue(), pl.getCurrentState().getNextAction());
            } else {
                wfMatrix = bpaApplicationWorkflowService.getWfMatrix(pl.getStateType(), null,
                        null, wfBean.getAdditionalRule(), pl.getCurrentState().getValue(), pl.getState().getNextAction());
                if (wfMatrix == null)
                    wfMatrix = bpaApplicationWorkflowService.getWfMatrix(pl.getStateType(), null,
                            null, wfBean.getAdditionalRule(), pl.getCurrentState().getValue(), null);
            }
            if (wfMatrix != null) {
                BpaStateInfo bpaStateInfo = bpaWorkFlowService.getBpaStateInfo(pl.getCurrentState(), pl.getStateHistory(),
                        pl.getIsSurveyorInspectionRequire(), new BpaStateInfo(), wfMatrix, wfBean.getWorkFlowAction());
                BpaStatus status = getStatusByCurrentMatrixStatus(wfMatrix);
                ownerUser = bpaWorkFlowService.getAssignmentsByPositionAndDate(pos.getId(), new Date()).get(0).getEmployee();
                if (status != null)
                    pl.setStatus(getStatusByCurrentMatrixStatus(wfMatrix));

                if (BpaConstants.GENERATE_PL_CERTIFICATE.equalsIgnoreCase(wfBean.getWorkFlowAction()))
                    pl.transition().end()
                            .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                            .withComments(wfBean.getApproverComments()).withDateInfo(currentDate.toDate())
                            .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_PL);
                else
                    pl.transition().progressWithStateCopy()
                            .withSenderName(user.getUsername() + BpaConstants.COLON_CONCATE + user.getName())
                            .withComments(wfBean.getApproverComments()).withOwner(ownerUser)
                            .withStateValue(wfMatrix.getNextState()).withDateInfo(currentDate.toDate()).withOwner(pos)
                            .withNextAction(wfMatrix.getNextAction()).withNatureOfTask(NATURE_OF_WORK_PL)
                            .withExtraInfo(bpaStateInfo);
            }
        }
        if (LOG.isDebugEnabled())
            LOG.debug(" WorkFlow Transition Completed ");
        bpaUtils.updatePortalUserinbox(pl, null);
    }
    
    private BpaStatus getStatusByCurrentMatrixStatus(final WorkFlowMatrix wfMatrix) {
        if (wfMatrix != null && wfMatrix.getNextStatus() != null && !"".equals(wfMatrix.getNextStatus()))
            return bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, wfMatrix.getNextStatus());
        return null;
    }

    private BpaStatus getStatusByPassingCode(final String code) {
        if (code != null && !"".equals(code))
            return bpaStatusService
                    .findByModuleTypeAndCode(BpaConstants.BPASTATUS_MODULETYPE, code);
        return null;
    }
}
