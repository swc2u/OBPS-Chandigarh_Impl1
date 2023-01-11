/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.transaction.service;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_ACCEPTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_APPROVED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DIGI_SIGNED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_NOCUPDATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_PREV_PLAN_UPDATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SECTION_CLRK_APPROVED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SUBMITTED;
import static org.egov.bpa.utils.BpaConstants.BPASTATUS_MODULETYPE;
import static org.egov.bpa.utils.BpaConstants.COMPOUND_WALL;
import static org.egov.bpa.utils.BpaConstants.FILESTORE_MODULECODE;
import static org.egov.bpa.utils.BpaConstants.FORWARDED_TO_CLERK;
import static org.egov.bpa.utils.BpaConstants.FWDINGTOLPINITIATORPENDING;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVRSR_FOR_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.GENERATEREVOCATIONNOTICE;
import static org.egov.bpa.utils.BpaConstants.LOWRISK;
import static org.egov.bpa.utils.BpaConstants.PERMIT_DEFAULT_CONDITIONS;
import static org.egov.bpa.utils.BpaConstants.ROLE_CITIZEN;
import static org.egov.bpa.utils.BpaConstants.ROOF_CONVERSION;
import static org.egov.bpa.utils.BpaConstants.SHUTTER_DOOR_CONVERSION;
import static org.egov.bpa.utils.BpaConstants.WELL;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_CREATED_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_FORWARD_FOR_PAYMENT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_INITIATE_REJECTION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.autonumber.PlanPermissionNumberGenerator;
import org.egov.bpa.autonumber.RevocationNumberGenerator;
import org.egov.bpa.autonumber.impl.PlanPermissionNumberGeneratorImpl;
import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.entity.PermitRevocation;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.entity.enums.FeeSubType;
import org.egov.bpa.master.service.BpaSchemeLandUsageService;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.master.service.PostalAddressService;
import org.egov.bpa.master.service.RegistrarOfficeVillageService;
import org.egov.bpa.master.service.ServiceTypeService;
import org.egov.bpa.service.es.BpaIndexService;
import org.egov.bpa.transaction.entity.Applicant;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.ApplicationPermitConditions;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.BuildingSubUsage;
import org.egov.bpa.transaction.entity.BuildingSubUsageDetails;
import org.egov.bpa.transaction.entity.ExistingBuildingFloorDetail;
import org.egov.bpa.transaction.entity.PermitCoApplicant;
import org.egov.bpa.transaction.entity.PermitDcrDocument;
import org.egov.bpa.transaction.entity.PermitDocument;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.transaction.entity.PermitNocDocument;
import org.egov.bpa.transaction.entity.common.DcrDocument;
import org.egov.bpa.transaction.entity.common.GeneralDocument;
import org.egov.bpa.transaction.entity.common.NocDocument;
import org.egov.bpa.transaction.entity.common.NoticeCondition;
import org.egov.bpa.transaction.entity.common.StoreDcrFiles;
import org.egov.bpa.transaction.entity.common.WorkflowFile;
import org.egov.bpa.transaction.entity.enums.ConditionType;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.notice.PermitApplicationNoticesFormat;
import org.egov.bpa.transaction.notice.impl.DemandDetailsFormatImpl;
import org.egov.bpa.transaction.repository.ApplicationBpaRepository;
import org.egov.bpa.transaction.repository.DcrDocumentRepository;
import org.egov.bpa.transaction.repository.PermitFeeRepository;
import org.egov.bpa.transaction.service.collection.ApplicationBpaBillService;
import org.egov.bpa.transaction.service.collection.BpaDemandService;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.transaction.service.messaging.BPASmsAndEmailService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.common.entity.bpa.Occupancy;
import org.egov.common.entity.dcr.helper.EdcrApplicationInfo;
import org.egov.common.entity.edcr.OccupancyTypeHelper;
import org.egov.common.entity.edcr.Plan;
import org.egov.commons.entity.Source;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.RoleService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.EnvironmentSettings;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.persistence.entity.PermanentAddress;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.ApplicationConstant;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.egov.infra.utils.FileStoreUtils;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.portal.entity.Citizen;
import org.egov.portal.service.CitizenService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ApplicationBpaService extends GenericBillGeneratorService {

    private static final String APPLICATION_FEES_FOR_SHUTTER_OR_DOOR_CONVERSION = "Application Fees for Shutter or Door conversion";
    private static final String APPLICATION_FEES_FOR_ROOF_CONVERSION = "Application Fees for Roof conversion";
    private static final String APPLICATION_FEES_FOR_COMPOUND_WALL = "Application Fees for compound wall";
    private static final String APPLICATION_FEES_FOR_AMENITIES = "Application Fees for Amenities";
    private static final String APPLICATION_FEES_FOR_WELL_CONSTURCTION = "Application Fees for Well consturction";
    private static final Logger LOG = getLogger(BpaUtils.class);
    private static final String APPLICATION_STATUS = "application.status";
    private static final String NOC_UPDATION_IN_PROGRESS = "NOC updation in progress";
    private static final String APPLICATION_APPROVAL_PENDING = "Application Approval Pending";
    private static final String PROPERTY_DOCUMENTS_VERIFICATION_INITIATED = "Property documents verification initiated";    
    public static final String UNCHECKED = "unchecked";
    public static final String ERROR_OCCURRED_WHILE_GETTING_INPUTSTREAM = "Error occurred while getting inputstream";
    private static final String MODULE_NAME = "BPA";
    private static final String PDF_QR_ENBLD = "DCRPDFQRCODEENABLED";

    @Autowired
    protected ApplicationFeeService applicationFeeService;
    @Autowired
    protected BpaDemandService bpaDemandService;
    @Autowired
    private ApplicationBpaRepository applicationBpaRepository;
    @Autowired
    private BpaStatusService bpaStatusService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private ApplicationBpaBillService applicationBpaBillService;
    @Autowired
    private GenericBillGeneratorService genericBillGeneratorService;
    @Autowired
    private ApplicationNumberGenerator applicationNumberGenerator;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private ChecklistServicetypeMappingService checklistServicetypeMappingService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private EnvironmentSettings environmentSettings;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CitizenService citizenService;
    @Autowired
    private PostalAddressService postalAddressService;
    @Autowired
    private BpaSchemeLandUsageService bpaSchemeLandUsageService;
    @Autowired
    private BuildingFloorDetailsService buildingFloorDetailsService;
    @Autowired
    private RegistrarOfficeVillageService registrarOfficeVillageService;
    @Autowired
    private ExistingBuildingFloorDetailsService existingBuildingFloorDetailsService;
    @Autowired
    private BpaApplicationPermitConditionsService bpaApplicationPermitConditionsService;
    @Autowired
    private BpaIndexService bpaIndexService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private DcrDocumentRepository dcrDocumentRepository;
    @Autowired
    private AppConfigValueService appConfigValuesService;
    @Autowired
    private BPASmsAndEmailService bpaSmsAndEmailService;
    @Autowired
    private FileStoreUtils fileStoreUtils;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource bpaMessageSource;
    @Autowired
    private PermitFeeRepository permitFeeRepository;
    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private RevocationNumberGenerator revocationNumberGenerator;
    @Autowired
    private PermitCoApplicantService coApplicantService;
    
    @Autowired
	private PermitFeeCalculationService permitFeeCalculationService;
    @Autowired
    private DcrRestService drcRestService;
    
    @Autowired
    private WorkflowFileService workflowFileService;
    
    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional
    public BpaApplication createNewApplication(final BpaApplication application, String workFlowAction) {
        final Boundary boundaryObj = bpaUtils.getBoundaryById(application.getWardId() != null ? application.getWardId()
                : getZone(application));
        buildSiteDetails(application, boundaryObj);
        application.getApplicationAmenity().clear();
        application.setApplicationAmenity(application.getApplicationAmenityTemp());
        application.getPermitOccupancies().clear();
        application.setPermitOccupancies(application.getPermitOccupanciesTemp());
        application.setApplicationNumber(applicationNumberGenerator.generate());
        // buildRegistrarOfficeForVillage(application);
        persistBpaNocDocuments(application);
        List<PermitDcrDocument> permitDcrDocuments = application.getPermitDcrDocuments();
        application.getPermitDcrDocuments().forEach(dcrDocument -> dcrDocument.setApplication(application));
        buildBuildingSubUsage(application);
        /*
         * if (bpaApplicationValidationService.isEdcrInetgrationRequired(application.getServiceType().getCode(),
         * application.getOccupancy().getDescription())) { buildApplicationDcrDocs(application, request); }
         */
        if (!application.getBuildingDetail().isEmpty())
            application.setTotalBuiltUpArea(bpaUtils.getBlockWiseOccupancyAndBuiltupArea(application.getBuildingDetail())
                    .entrySet().stream().map(Map.Entry::getValue).reduce(BigDecimal.ZERO, BigDecimal::add));
        if (workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)) {
            final BpaStatus bpaStatus = getStatusByCodeAndModuleType(APPLICATION_STATUS_SUBMITTED);
            application.setStatus(bpaStatus);
        }else if(workFlowAction !=null && workFlowAction.equals(APPLICATION_STATUS_PREV_PLAN_UPDATED)) { 
        	 final BpaStatus bpaStatus = getStatusByCodeAndModuleType(APPLICATION_STATUS_PREV_PLAN_UPDATED);
             application.setStatus(bpaStatus);
    	}else {
            final BpaStatus bpaStatus = getStatusByCodeAndModuleType(APPLICATION_STATUS_CREATED);
            application.setStatus(bpaStatus);
        }
        setSource(application);
        Long approvalPosition = null;
        application.setCoApplicants(buildCoApplicantDetails(application));

        if (!bpaUtils.logedInuseCitizenOrBusinessUser()) {
            WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(application.getIsOneDayPermitApplication(),
                    application.getStateType(), WF_CREATED_STATE, application.getApplicationType().getName());
            String currentState = WF_CREATED_STATE;
            if (application.getAdmissionfeeAmount() != null
                    && application.getAdmissionfeeAmount().compareTo(BigDecimal.ZERO) == 0) {
                wfMatrix = bpaUtils.getWfMatrixByCurrentState(application.getIsOneDayPermitApplication(),
                        application.getStateType(), WF_NEW_STATE, application.getApplicationType().getName());
                currentState = WF_NEW_STATE;
            }
            if (wfMatrix != null)
                approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                        bpaUtils.getBoundaryForWorkflow(application.getSiteDetail().get(0)).getId());
            bpaUtils.redirectToBpaWorkFlow(approvalPosition, application, currentState, null, null,
                    null);
        }
        buildDefaultPermitConditionsList(application);
        BpaApplication bpaApplicationResponse = applicationBpaRepository.saveAndFlush(application);
        application.setPermitDcrDocuments(persistApplnDCRDocuments(permitDcrDocuments));
        applicationBpaRepository.save(bpaApplicationResponse);
        
//        ApplicationBpaFeeCalculation feeCalculation = (ApplicationBpaFeeCalculation) specificNoticeService
//                .find(PermitFeeCalculationService.class, specificNoticeService.getCityDetails());
//        if (bpaUtils.isApplicationFeeCollectionRequired())
//            application.setDemand(feeCalculation.createDemand(application));
//        else
//            application.setDemand(feeCalculation.createDemandWhenFeeCollectionNotRequire(application));

        bpaIndexService.updateIndexes(bpaApplicationResponse);
        return bpaApplicationResponse;
    }

    public void buildBuildingSubUsage(BpaApplication application) {
        application.getBuildingSubUsages().forEach(subUsage -> {
            subUsage.setApplication(application);
            subUsage.getSubUsageDetails().forEach(subUsageDtl -> {
                subUsageDtl.setBuildingSubUsage(subUsage);
            });
        });
    }

    private void buildSiteDetails(final BpaApplication application, final Boundary boundaryObj) {
        application.getSiteDetail().get(0).setAdminBoundary(boundaryObj);
        application.getSiteDetail().get(0).setApplication(application);
        if (application.getSiteDetail().get(0).getLandUsageId() != null)
            application.getSiteDetail().get(0)
                    .setLandUsage((bpaSchemeLandUsageService.findById(application.getSiteDetail().get(0).getLandUsageId())));
        /*
         * application.getSiteDetail().get(0)
         * .setPostalAddress(postalAddressService.findById(application.getSiteDetail().get(0).getPostalId()));
         */
    }

    private void setSource(final BpaApplication application) {
        if (bpaUtils.logedInuseCitizenOrBusinessUser())
            application.setSource(Source.CITIZENPORTAL);
        else
            application.setSource(Source.SYSTEM);
    }

    private void buildRegistrarOfficeForVillage(final BpaApplication application) {
        if (application.getSiteDetail().get(0).getRegistrarVillageId() != null)
            application.getSiteDetail().get(0).setRegistrarOffice(
                    registrarOfficeVillageService.findById(application.getSiteDetail().get(0).getRegistrarVillageId()));
    }

    private Long getZone(final BpaApplication application) {
        return application.getZoneId();
    }
    
    public Plan getPlanInfo(final String edcrNumber) {		
		EdcrApplicationInfo edcrPlanInfo = drcRestService.getDcrPlanInfo(edcrNumber, ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest());		 
        return edcrPlanInfo.getPlan();
    }

    public void buildDefaultPermitConditionsList(final BpaApplication application) {
        List<ChecklistServiceTypeMapping> defaultChecklist = checklistServicetypeMappingService
                .findByActiveByServiceTypeAndChecklist(application.getServiceType().getId(), PERMIT_DEFAULT_CONDITIONS);
        List<ApplicationPermitConditions> appPermitConditiontemp = new ArrayList<ApplicationPermitConditions>();
        if (application.getApplicationType().getName().equals(LOWRISK)) {
            bpaApplicationPermitConditionsService.delete(application.getDefaultPermitConditions());
            application.getDefaultPermitConditions().clear();
        }
        if (!application.getApplicationType().getName().equals(LOWRISK))
            appPermitConditiontemp = application.getDefaultPermitConditions();
        for (ChecklistServiceTypeMapping permitDefaultChecklist : defaultChecklist) {
            ApplicationPermitConditions appPermitCondition = new ApplicationPermitConditions();
            appPermitCondition.setApplication(application);
            NoticeCondition nc = new NoticeCondition();
            nc.setType(ConditionType.PERMITDEFAULTCONDITIONS);
            nc.setRequired(true);
            nc.setChecklistServicetype(permitDefaultChecklist);
            appPermitCondition.setNoticeCondition(nc);
            appPermitConditiontemp.add(appPermitCondition);
        }
        application.setDefaultPermitConditions(appPermitConditiontemp);

    }

    private void buildPermitConditions(final BpaApplication application) {
        if (!application.getDynamicPermitConditionsTemp().isEmpty() || !application.getStaticPermitConditionsTemp().isEmpty()) {
            bpaApplicationPermitConditionsService.delete(application.getDynamicPermitConditions());
            bpaApplicationPermitConditionsService.delete(application.getStaticPermitConditions());
            bpaApplicationPermitConditionsService.delete(application.getAdditionalPermitConditions());
            application.getDynamicPermitConditions().clear();
            application.getStaticPermitConditions().clear();
            application.getAdditionalPermitConditions().clear();
            List<ApplicationPermitConditions> additionalPermitConditions = new ArrayList<>();
            for (ApplicationPermitConditions addnlCondition : application.getAdditionalPermitConditionsTemp()) {
                if (addnlCondition != null && addnlCondition.getNoticeCondition().getAdditionalCondition() != null)
                    additionalPermitConditions.add(addnlCondition);
            }
            application.setDynamicPermitConditions(application.getDynamicPermitConditionsTemp());
            application.setStaticPermitConditions(application.getStaticPermitConditionsTemp());
            application.setAdditionalPermitConditions(additionalPermitConditions);
        }
    }

    private void buildRejectionReasons(final BpaApplication application) {
        bpaApplicationPermitConditionsService.delete(application.getRejectionReasons());
        bpaApplicationPermitConditionsService.delete(application.getAdditionalPermitConditions());
        application.getAdditionalPermitConditions().clear();
        application.getRejectionReasons().clear();
        List<ApplicationPermitConditions> additionalRejectReasons = new ArrayList<>();
        for (ApplicationPermitConditions addnlReason : application.getAdditionalRejectReasonsTemp()) {
            addnlReason.setApplication(application);
            addnlReason.getNoticeCondition().setChecklistServicetype(
                    application.getAdditionalRejectReasonsTemp().get(0).getNoticeCondition().getChecklistServicetype());
            if (addnlReason != null && addnlReason.getNoticeCondition().getAdditionalCondition() != null)
                additionalRejectReasons.add(addnlReason);
        }
        application.setRejectionReasons(application.getRejectionReasonsTemp());
        application.setAdditionalPermitConditions(additionalRejectReasons);
    }

    private void persistBpaNocDocuments(final BpaApplication application) {

        processAndStoreNocDocuments(application);
    }

    public BpaStatus getStatusByCodeAndModuleType(final String code) {
        return bpaStatusService.findByModuleTypeAndCode(BPASTATUS_MODULETYPE, code);
    }

    @Transactional
    public void saveAndFlushApplication(final BpaApplication application, String workFlowAction) {

        if (!application.getBuildingSubUsages().isEmpty())
            for (BuildingSubUsage subUsage : application.getBuildingSubUsages())
                for (BuildingSubUsageDetails subUsageDetails : subUsage.getSubUsageDetails()) {
                    if (!subUsageDetails.getSubUsagesTemp().isEmpty()) {
                        subUsageDetails.getSubUsages().clear();
                        subUsageDetails.setSubUsages(subUsageDetails.getSubUsagesTemp());
                    }
                }
        application.setCoApplicants(buildCoApplicantDetails(application));
        buildBuildingSubUsage(application);
        persistBpaNocDocuments(application);
        buildPermitConditions(application);
        application.setPermitDcrDocuments(persistApplnDCRDocuments(application.getPermitDcrDocuments()));
        // persistPostalAddress(application);
        // buildRegistrarOfficeForVillage(application);
        buildSchemeLandUsage(application);
        applicationBpaRepository.saveAndFlush(application);

        if (workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && (bpaUtils.logedInuseCitizenOrBusinessUser())) {
            bpaIndexService.updateIndexes(application);
        }
    }

    @Transactional
    public void saveAndFlushApplication(final BpaApplication application) {
        appendQrCodeWithDcrDocuments(application);
        persistBpaNocDocuments(application);
        buildPermitConditions(application);
        // persistPostalAddress(application);
        // buildRegistrarOfficeForVillage(application);
        buildSchemeLandUsage(application);
        /*if (application.getApplicationType() != null && application.getApplicationType().getName() != null
                && application.getApplicationType().getName().equals(BpaConstants.APPLICATION_TYPE_LOWRISK)
                && application.getStatus() != null && application.getStatus().getCode() != null
                && application.getStatus().getCode().equals("Registered")) {
            application.setPlanPermissionNumber(generatePlanPermissionNumber(application));
            application.setPlanPermissionDate(new Date());
            PermitApplicationNoticesFormat bpaNoticeFeature = (PermitApplicationNoticesFormat) specificNoticeService
                    .find(PermitOrderFormatImpl.class, specificNoticeService.getCityDetails());
            try {
                ReportOutput reportOutput = bpaNoticeFeature
                        .generateNotice(findByApplicationNumber(application.getApplicationNumber()));
            } catch (IOException e) {
                if (LOG.isDebugEnabled())
                    LOG.debug(e.getMessage());
            }
        }*/
        applicationBpaRepository.saveAndFlush(application);
    }

    private void persistPostalAddress(final BpaApplication application) {
        if (application.getSiteDetail().get(0).getPostalId() != null) {
            application.getSiteDetail().get(0)
                    .setPostalAddress(postalAddressService.findById(application.getSiteDetail().get(0).getPostalId()));
        }
    }

    private void buildSchemeLandUsage(final BpaApplication application) {
        if (application.getSiteDetail() != null && application.getSiteDetail().get(0) != null
                && application.getSiteDetail().get(0).getLandUsageId() != null) {
            application.getSiteDetail().get(0)
                    .setLandUsage(bpaSchemeLandUsageService.findById(application.getSiteDetail().get(0).getLandUsageId()));
        }
    }

    @Transactional
    public String redirectToCollectionOnForward(final BpaApplication application, Model model) {
        persistBpaNocDocuments(application);
        buildExistingAndProposedBuildingDetails(application);
        return genericBillGeneratorService.generateBillAndRedirectToCollection(application, model);
    }

    public void buildExistingAndProposedBuildingDetails(final BpaApplication application) {
        existingBuildingFloorDetailsService.buildExistingBuildingFloorDetails(application);
        buildingFloorDetailsService.buildProposedBuildingFloorDetails(application);
    }

    @Transactional
    public BpaApplication updateApplication(final BpaApplication application, Long approvalPosition,
            String workFlowAction, BigDecimal amountRule) throws IOException {
        application.setSentToPreviousOwner(false);
        application.setPermitDcrDocuments(persistApplnDCRDocuments(application.getPermitDcrDocuments()));
        persistBpaNocDocuments(application);
        buildExistingAndProposedBuildingDetails(application);
        // persistPostalAddress(application);
        buildSchemeLandUsage(application);
        // For one day permit
        appendQrCodeWithDcrDocuments(application);
        if (workFlowAction.equals(BpaConstants.GENERATEREVOCATIONNOTICE)) {
            PermitRevocation permitRevocation = new PermitRevocation();
            permitRevocation.setRevocationNumber(revocationNumberGenerator.generatePermitRevocationNumber());
            permitRevocation.setApplication(application);
            permitRevocation.setApplicationNumber(application.getApplicationNumber());
            permitRevocation.setApplicationDate(new Date());
            permitRevocation.setApproveCancelRemarks(application.getApprovalComent());
            List<PermitRevocation> list = application.getPermitRevocation();
            list.add(permitRevocation);
            application.setPermitRevocation(list);
            final BpaStatus bpaStatus = getStatusByCodeAndModuleType(BpaConstants.APPLICATION_STATUS_REVOKED);
            application.setStatus(bpaStatus);
        }

        if (application.getIsOneDayPermitApplication() && 
        		(APPLICATION_STATUS_DOC_VERIFIED.equalsIgnoreCase(application.getState().getValue())
                || APPLICATION_STATUS_SECTION_CLRK_APPROVED.equalsIgnoreCase(application.getState().getValue()))) {

            String feeCalculationMode = bpaUtils.getBPAFeeCalculationMode();

            if (feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECAL)
                    || feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECALEDIT)) {
                ApplicationBpaFeeCalculation feeCalculation = (ApplicationBpaFeeCalculation) specificNoticeService
                        .find(PermitFeeCalculationService.class, specificNoticeService.getCityDetails());

                PermitFee permitFee = feeCalculation.calculateBpaSanctionFees(application);

                ApplicationFee applicationFee = applicationFeeService.saveApplicationFee(permitFee.getApplicationFee());
                permitFee.setApplicationFee(applicationFee);
                permitFeeRepository.save(permitFee);
                application.setDemand(bpaDemandService.generateDemandUsingSanctionFeeList(permitFee.getApplicationFee(),
                        permitFee.getApplication().getDemand()));
            }
        }
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(workFlowAction)
        		&& !WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(workFlowAction)
        		//&& PROPERTY_DOCUMENTS_VERIFICATION_INITIATED.equalsIgnoreCase(application.getState().getValue())
                //&& APPLICATION_STATUS_REGISTERED.equalsIgnoreCase(application.getStatus().getCode())
        		//&& BpaConstants.APPLICATION_STATUS_RECORD_APPROVED.equalsIgnoreCase(application.getState().getValue())
                //&& BpaConstants.APPROVED.equalsIgnoreCase(application.getStatus().getCode())
        		&& (WF_APPROVE_BUTTON.equals(workFlowAction) || WF_FORWARD_FOR_PAYMENT_BUTTON.equals(workFlowAction))
        	) {
            String feeCalculationMode = bpaUtils.getBPAFeeCalculationMode();
            LOG.info("1 feeCalculationMode "+feeCalculationMode);
            if (feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECAL) ||
                    feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECALEDIT)) {
                ApplicationBpaFeeCalculation feeCalculation = (ApplicationBpaFeeCalculation) specificNoticeService
                        .find(PermitFeeCalculationService.class, specificNoticeService.getCityDetails());
                PermitFee permitFee = feeCalculation.calculateBpaSanctionFees(application);
                ApplicationFee applicationFee = applicationFeeService.saveApplicationFee(permitFee.getApplicationFee());
                permitFee.setApplicationFee(applicationFee);
                LOG.info("applicationFee "+applicationFee);
                permitFeeRepository.save(permitFee);
                application.setDemand(bpaDemandService.generateDemandUsingSanctionFeeList(permitFee.getApplicationFee(),
                        permitFee.getApplication().getDemand()));
                LOG.info("2 "+permitFee.getApplication().getDemand());
            }
        }
        if (WF_APPROVE_BUTTON.equals(workFlowAction) || WF_FORWARD_FOR_PAYMENT_BUTTON.equals(workFlowAction)) {

            if (application.getPlanPermissionNumber() == null) {
                application.setPlanPermissionNumber(generatePlanPermissionNumber(application));
                application.setPlanPermissionDate(new Date());
            }
            application.setApproverPosition(application.getState().getOwnerPosition());
            application.setApproverUser(
                    application.getState().getOwnerUser() == null ? securityUtils.getCurrentUser()
                            : application.getState().getOwnerUser());
            PermitApplicationNoticesFormat bpaNoticeFeature = (PermitApplicationNoticesFormat) specificNoticeService.find(
                    DemandDetailsFormatImpl.class,
                    specificNoticeService.getCityDetails());
            ReportOutput reportOutput = bpaNoticeFeature
                    .generateNotice(findByApplicationNumber(application.getApplicationNumber()));
            bpaSmsAndEmailService.sendSmsAndEmailOnApplicationApproval(application, reportOutput);

        }
        if (APPLICATION_STATUS_APPROVED.equals(application.getStatus().getCode())
                || APPLICATION_STATUS_DIGI_SIGNED.equalsIgnoreCase(application.getStatus().getCode())
                || (WF_APPROVE_BUTTON.equals(workFlowAction) || WF_FORWARD_FOR_PAYMENT_BUTTON.equals(workFlowAction))) {
            buildPermitConditions(application);
        }
        if (application.getFiles() != null && application.getFiles().length > 0) {
            Set<FileStoreMapper> tsInspnDocs = new HashSet<>();
            tsInspnDocs.addAll(application.getTsInspnSupportDocs());
            tsInspnDocs.addAll(addToFileStore(application.getFiles()));
            application.setTsInspnSupportDocs(tsInspnDocs);
        }
        if (WF_REJECT_BUTTON.equalsIgnoreCase(workFlowAction)
                || WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(workFlowAction)
                || APPLICATION_STATUS_REJECTED.equalsIgnoreCase(application.getStatus().getCode())
                || ((!WF_APPROVE_BUTTON.equals(workFlowAction) || !WF_FORWARD_FOR_PAYMENT_BUTTON.equals(workFlowAction))
                        && APPLICATION_STATUS_NOCUPDATED.equals(application.getStatus().getCode()))
                || (GENERATEREVOCATIONNOTICE.equalsIgnoreCase(workFlowAction)
                        && application.getApplicationType().getName().equals(LOWRISK))) {
            buildRejectionReasons(application);
        }
        application.setLPRequestInitiated(FWDINGTOLPINITIATORPENDING.equalsIgnoreCase(application.getState().getNextAction()));
        List<ApplicationPermitConditions> defaultPermitCondition = bpaApplicationPermitConditionsService
                .findAllByApplicationAndPermitConditionType(application, ConditionType.PERMITDEFAULTCONDITIONS);
        if (defaultPermitCondition == null
                || defaultPermitCondition.isEmpty() && !GENERATEREVOCATIONNOTICE.equalsIgnoreCase(workFlowAction))
            buildDefaultPermitConditionsList(application);
        final BpaApplication updatedApplication = applicationBpaRepository.save(application);
        if (!WF_SAVE_BUTTON.equalsIgnoreCase(workFlowAction) && updatedApplication.getCurrentState() != null
                && !updatedApplication.getCurrentState().getValue().equals(WF_NEW_STATE)) {
            bpaUtils.redirectToBpaWorkFlow(approvalPosition, application, application.getCurrentState().getValue(),
                    application.getApprovalComent(), workFlowAction, amountRule);
            bpaIndexService.updateIndexes(application);
        }
        return updatedApplication;
    }

    private void appendQrCodeWithDcrDocuments(BpaApplication application) {
        if (Boolean.valueOf(appConfigValuesService.getConfigValuesByModuleAndKey(MODULE_NAME,
                PDF_QR_ENBLD).get(0).getValue())
                && (application.getStatus().getCode().equals(APPLICATION_STATUS_APPROVED)
                        || application.getStatus().getCode().equals(APPLICATION_STATUS_NOCUPDATED) || application.getStatus().getCode().equals(APPLICATION_STATUS_ACCEPTED))
                && !bpaDemandService.checkAnyTaxIsPendingToCollect(application)) {
            List<PermitDcrDocument> dcrDocuments = dcrDocumentRepository.findByApplication(application);
            for (PermitDcrDocument dcrDocument : dcrDocuments) {
                if (LOG.isInfoEnabled())
                    LOG.info("#### Dcr Document ####", dcrDocument.getId());
                for (StoreDcrFiles file : dcrDocument.getDcrDocument().getDcrAttachments()) {
                    if (LOG.isInfoEnabled())
                        LOG.info("#### file ####", file.getId());
                    bpaUtils.addQrCodeToPdfDocuments(file.getFileStoreMapper(), application);
                }
            }
        }
    }

    public void persistOrUpdateApplicationDocument(final BpaApplication bpaApplication) {
        processAndStoreGeneralDocuments(bpaApplication);
    }
    
    public void persistWfDocuments(final BpaApplication bpaApplication) {
        processAndStoreWfDocuments(bpaApplication);
    }
    
    public void persistWfDocuments(final OccupancyCertificate occupancyCertificate) {
        processAndStoreWfDocuments(occupancyCertificate);
    }
    
    public void persistWfDocuments(final PlinthLevelCertificate plinthLevelCertificate) {
        processAndStoreWfDocuments(plinthLevelCertificate);
    }

    public BigDecimal setAdmissionFeeAmountForRegistrationWithAmenities(final Long serviceType, List<ServiceType> amenityList) {
        BigDecimal admissionfeeAmount;
        String feeType;
        if (serviceType != null && bpaUtils.isApplicationFeeCollectionRequired()) {
            String serviceTyp = serviceTypeService.findById(serviceType).getDescription();
            if (serviceTyp.equals(WELL))
                feeType = APPLICATION_FEES_FOR_WELL_CONSTURCTION;
            else if (serviceTyp.equals(BpaConstants.AMENITIES))
                feeType = APPLICATION_FEES_FOR_AMENITIES;
            else if (serviceTyp.equals(COMPOUND_WALL))
                feeType = APPLICATION_FEES_FOR_COMPOUND_WALL;
            else if (serviceTyp.equals(ROOF_CONVERSION))
                feeType = APPLICATION_FEES_FOR_ROOF_CONVERSION;
            else if (serviceTyp.equals(SHUTTER_DOOR_CONVERSION))
                feeType = APPLICATION_FEES_FOR_SHUTTER_OR_DOOR_CONVERSION;
            else
                feeType = BpaConstants.BPA_APP_FEE;

            admissionfeeAmount = getTotalFeeAmountByPassingServiceTypeandArea(serviceType, amenityList,
                    feeType, FeeSubType.APPLICATION_FEE);
        } else
            admissionfeeAmount = BigDecimal.ZERO;
        return admissionfeeAmount;
    }

    public BigDecimal setAdmissionFeeAmountWithAmenities(final Long serviceType, List<ServiceType> amenityList) {
        BigDecimal admissionfeeAmount = BigDecimal.ZERO;
        String feeType;
        if (serviceType != null && bpaUtils.isApplicationFeeCollectionRequired()) {
            Criteria feeCrit = bpaDemandService.createCriteriaforApplicationFee(serviceType, BpaConstants.BPA_APP_FEE,
                    FeeSubType.APPLICATION_FEE);
            final List<BpaFeeMapping> bpaFeeMap = feeCrit.list();
            for (final BpaFeeMapping feeMap : bpaFeeMap)
                admissionfeeAmount = admissionfeeAmount.add(BigDecimal.valueOf(feeMap.getAmount()));
            for (ServiceType serviceTyp : amenityList) {
                String serviceName = serviceTypeService.findById(serviceTyp.getId()).getDescription();
                if (serviceName.equals(WELL))
                    feeType = APPLICATION_FEES_FOR_WELL_CONSTURCTION;
                else if (serviceName.equals(COMPOUND_WALL))
                    feeType = APPLICATION_FEES_FOR_COMPOUND_WALL;
                else if (serviceName.equals(ROOF_CONVERSION))
                    feeType = APPLICATION_FEES_FOR_ROOF_CONVERSION;
                else if (serviceName.equals(SHUTTER_DOOR_CONVERSION))
                    feeType = APPLICATION_FEES_FOR_SHUTTER_OR_DOOR_CONVERSION;
                else
                    feeType = BpaConstants.BPA_APP_FEE;

                Criteria amenityCrit = bpaDemandService.createCriteriaforApplicationFee(serviceTyp.getId(), feeType,
                        FeeSubType.APPLICATION_FEE);
                final List<BpaFeeMapping> amenityMap = amenityCrit.list();
                for (final BpaFeeMapping feeMap : amenityMap)
                    admissionfeeAmount = admissionfeeAmount.add(BigDecimal.valueOf(feeMap.getAmount()));
            }
        } else
            admissionfeeAmount = BigDecimal.ZERO;

        return admissionfeeAmount;
    }

    public BigDecimal setRegistrationAmountForRegistrationWithAmenities(final Long serviceType, List<ServiceType> amenityList) {
        BigDecimal admissionfeeAmount;
        if (serviceType != null)
            admissionfeeAmount = getTotalFeeAmountByPassingServiceTypeandArea(serviceType, amenityList,
                    BpaConstants.BPA_REGISTRATION_FEE, FeeSubType.APPLICATION_FEE);
        else
            admissionfeeAmount = BigDecimal.ZERO;
        return admissionfeeAmount;
    }

    private BigDecimal getTotalFeeAmountByPassingServiceTypeandArea(final Long serviceTypeId, List<ServiceType> amenityList,
            final String feeType, final FeeSubType feeSubType) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<Long> serviceTypeList = new ArrayList<>();
        serviceTypeList.add(serviceTypeId);
        for (ServiceType temp : amenityList) {
            serviceTypeList.add(temp.getId());
        }
        if (serviceTypeId != null) {
            final Criteria feeCrit = applicationBpaBillService.getBpaFeeCriteria(serviceTypeList, feeType, feeSubType);
            @SuppressWarnings(UNCHECKED)
            final List<BpaFeeMapping> bpaFeeMap = feeCrit.list();
            for (final BpaFeeMapping feeMap : bpaFeeMap)
                totalAmount = totalAmount.add(BigDecimal.valueOf(feeMap.getAmount()));
        } else
            throw new ApplicationRuntimeException("Service Type Id is mandatory.");

        return totalAmount;
    }

    public BigDecimal getTotalFeeAmountByPassingServiceTypeAndAmenities(BpaApplication application) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (application != null) {	    		
    		Plan plan = getPlanInfo(application.geteDcrNumber());	    		
    		if(null!=plan) {
    			OccupancyTypeHelper mostRestrictiveFarHelper = plan.getVirtualBuilding() != null
						? plan.getVirtualBuilding().getMostRestrictiveFarHelper()
						: null;
						totalAmount.add(permitFeeCalculationService.getTotalSecurityFee(plan, mostRestrictiveFarHelper,null));
    		}
    	}
        return totalAmount;
    }

    public BpaApplication getApplicationByDemand(final EgDemand demand) {
        return applicationBpaRepository.findByDemand(demand);
    }

    public BpaApplication findByApplicationNumber(final String applicationNumber) {
        return applicationBpaRepository.findByApplicationNumber(applicationNumber);
    }

    public BpaApplication findById(final Long applicationId) {
        return applicationBpaRepository.findOne(applicationId);
    }

    public List<BpaApplication> findApplicationByEDCRNumber(final String eDcrNumber) {
        return applicationBpaRepository.findApplicationByEDcrNumberOrderByIdDesc(eDcrNumber);
    }
    
    public List<BpaApplication> findApplicationByPlotNumberAndSectorOrderByIdDesc(String plotNumber,String sector){
    	 return applicationBpaRepository.findApplicationByPlotNumberAndSectorOrderByIdDesc(plotNumber,sector);
    }

    public BpaApplication findByPermitNumber(final String permitNumber) {
        return applicationBpaRepository.findByPlanPermissionNumber(permitNumber);
    }
    
    private void processAndStoreNocDocuments(final BpaApplication application) {
        if (!application.getPermitNocDocuments().isEmpty()
                && null == application.getPermitNocDocuments().get(0).getId())
            application.getPermitNocDocuments().forEach(nocDocument -> {
                nocDocument.setApplication(application);
                NocDocument nocDocumentsCommon = nocDocument.getNocDocument();
                nocDocumentsCommon.setServiceChecklist(
                        checklistServicetypeMappingService.load(nocDocumentsCommon.getServiceChecklist().getId()));
                nocDocumentsCommon.setCreatedUser(securityUtils.getCurrentUser());
                buildNocFiles(nocDocumentsCommon);
                nocDocument.setNocDocument(nocDocumentsCommon);
            });
        else
            for (final PermitNocDocument nocDocument : application.getPermitNocDocuments())
                buildNocFiles(nocDocument.getNocDocument());
    }

    private void buildNocFiles(final NocDocument nocDoc) {
        if (nocDoc.getFiles() != null && nocDoc.getFiles().length > 0) {
            Set<FileStoreMapper> existingFiles = new HashSet<>();
            existingFiles.addAll(nocDoc.getNocSupportDocs());
            existingFiles.addAll(addToFileStore(nocDoc.getFiles()));
            nocDoc.setNocSupportDocs(existingFiles);
            nocDoc.setSubmitted(true);
        }
    }

    private void processAndStoreGeneralDocuments(final BpaApplication application) {
        if (!application.getPermitDocuments().isEmpty()
                && null == application.getPermitDocuments().get(0).getId())
            application.getPermitDocuments().forEach(document -> {
                document.setApplication(application);
                GeneralDocument documentsCommon = document.getDocument();
                documentsCommon.setServiceChecklist(
                        checklistServicetypeMappingService.load(document.getDocument().getServiceChecklist().getId()));
                documentsCommon.setCreatedUser(securityUtils.getCurrentUser());
                buildGeneralFiles(documentsCommon);
                document.setDocument(documentsCommon);
            });
        else
            for (final PermitDocument ocDocuments : application.getPermitDocuments())
                buildGeneralFiles(ocDocuments.getDocument());
    }
    
    private void processAndStoreWfDocuments(final BpaApplication application) {
    	if (application.getWorkflowFile().getFiles() != null && application.getWorkflowFile().getFiles().length > 0) {
    		UUID uuid = UUID.randomUUID();
    		String wfFileRefId = uuid.toString();
    		for(MultipartFile doc:application.getWorkflowFile().getFiles()) {
    			saveWfDocuments(doc, wfFileRefId);
    		}
    		application.setWfFileRefId(wfFileRefId);
    	}
    }
    
    private void processAndStoreWfDocuments(final OccupancyCertificate occupancyCertificate) {
    	if (occupancyCertificate.getWorkflowFile().getFiles() != null && occupancyCertificate.getWorkflowFile().getFiles().length > 0) {
    		UUID uuid = UUID.randomUUID();
    		String wfFileRefId = uuid.toString();
    		for(MultipartFile doc:occupancyCertificate.getWorkflowFile().getFiles()) {
    			saveWfDocuments(doc, wfFileRefId);
    		}
    		occupancyCertificate.setWfFileRefId(wfFileRefId);
    	}
    }
    
    private void processAndStoreWfDocuments(final PlinthLevelCertificate plinthLevelCertificate) {
    	if (plinthLevelCertificate.getWorkflowFile().getFiles() != null && plinthLevelCertificate.getWorkflowFile().getFiles().length > 0) {
    		UUID uuid = UUID.randomUUID();
    		String wfFileRefId = uuid.toString();
    		for(MultipartFile doc:plinthLevelCertificate.getWorkflowFile().getFiles()) {
    			saveWfDocuments(doc, wfFileRefId);
    		}
    		plinthLevelCertificate.setWfFileRefId(wfFileRefId);
    	}
    }
    
    private void saveWfDocuments(MultipartFile file, String stateRefId) {
    	FileStoreMapper fileStoreMapper = addToFileStore(file);
        WorkflowFile workflowFile = new WorkflowFile();
        workflowFile.setStateRefId(stateRefId);
        workflowFile.setFileStoreMapper(fileStoreMapper);
        workflowFile.setCreatedBy(securityUtils.getCurrentUser());
        workflowFile.setCreatedDate(new Date());
        workflowFileService.save(workflowFile);
    }
    
    private void buildGeneralFiles(final GeneralDocument commonDoc) {
        if (commonDoc.getFiles() != null && commonDoc.getFiles().length > 0) {
            Set<FileStoreMapper> existingFiles = new HashSet<>();
            existingFiles.addAll(commonDoc.getSupportDocs());
            existingFiles.addAll(addToFileStore(commonDoc.getFiles()));
            commonDoc.setSupportDocs(existingFiles);
            commonDoc.setSubmitted(true);
        }
    }

    private List<PermitDcrDocument> persistApplnDCRDocuments(final List<PermitDcrDocument> uploadDcrDocuments) {
        List<PermitDcrDocument> permitDcrDocuments = new ArrayList<>();
        if (!uploadDcrDocuments.isEmpty() && null == uploadDcrDocuments.get(0).getId())
            for (final PermitDcrDocument permitDcrDoc : uploadDcrDocuments) {
                DcrDocument dcrDocument = permitDcrDoc.getDcrDocument();
                dcrDocument.setServiceChecklist(
                        checklistServicetypeMappingService.load(dcrDocument.getServiceChecklist().getId()));
                DcrDocument dcrDocumentRes = buildAutoPopualtedDCRFiles(dcrDocument);
                buildDCRFiles(dcrDocumentRes);
                permitDcrDoc.setDcrDocument(dcrDocumentRes);
                permitDcrDocuments.add(permitDcrDoc);
            }
        else
            for (final PermitDcrDocument permitDcrDoc : uploadDcrDocuments) {
                DcrDocument dcrDocumentRes = buildAutoPopualtedDCRFiles(permitDcrDoc.getDcrDocument());
                buildDCRFiles(dcrDocumentRes);
                permitDcrDoc.setDcrDocument(dcrDocumentRes);
                permitDcrDocuments.add(permitDcrDoc);
            }
        return permitDcrDocuments;
    }

    // Will save manually uploaded dcr document pdf's
    private DcrDocument buildDCRFiles(DcrDocument dcrDocument) {
        Set<StoreDcrFiles> storeDCRFiles = new HashSet<>();
        storeDCRFiles.addAll(dcrDocument.getDcrAttachments());
        if (dcrDocument.getFiles() != null
                && dcrDocument.getFiles().length > 0) {
            for (MultipartFile file : dcrDocument.getFiles()) {
                if (!file.isEmpty()) {
                    StoreDcrFiles storeDCRFile = new StoreDcrFiles();
                    storeDCRFile.setDcrDocument(dcrDocument);
                    storeDCRFile.setFileStoreMapper(addToFileStore(file));
                    storeDCRFile.setAutoPopulated(false);
                    storeDCRFiles.add(storeDCRFile);
                }
            }
        }
        if (!storeDCRFiles.isEmpty())
            dcrDocument.setDcrAttachments(storeDCRFiles);
        return dcrDocument;
    }

    // Will save auto populated dcr document pdf's from dcr system
    private DcrDocument buildAutoPopualtedDCRFiles(DcrDocument dcrDocument) {
        Set<StoreDcrFiles> storeDCRFiles = new HashSet<>();
        storeDCRFiles.addAll(dcrDocument.getDcrAttachments());
        if (dcrDocument.getFileStoreIds() != null &&
                dcrDocument.getFileStoreIds().length > 0) {
            for (String fileStoreId : dcrDocument.getFileStoreIds()) {
                if (!fileStoreId.isEmpty()) {
                    Path file = fileStoreService.fetchAsPath(fileStoreId, "Digit DCR");
                    Optional<FileStoreMapper> fileStoreMapper = fileStoreUtils.getFileStoreMapper(fileStoreId);
                    FileStoreMapper savedFileStoreMapper = null;
                    try {
                        savedFileStoreMapper = fileStoreService.store(new ByteArrayInputStream(Files.readAllBytes(file)),
                                fileStoreMapper.isPresent() ? fileStoreMapper.get().getFileName() : file.toFile().getName(),
                                "application/pdf",
                                FILESTORE_MODULECODE);
                    } catch (IOException e) {
                        throw new ApplicationRuntimeException("Error occurred, while saving dcr documents!!!!!!", e);
                    }
                    StoreDcrFiles storeDCRFile = new StoreDcrFiles();
                    storeDCRFile.setDcrDocument(dcrDocument);
                    storeDCRFile.setFileStoreMapper(savedFileStoreMapper);
                    storeDCRFile.setAutoPopulated(true);
                    storeDCRFiles.add(storeDCRFile);
                }
            }
        }

        if (!storeDCRFiles.isEmpty())
            dcrDocument.setDcrAttachments(storeDCRFiles);
        return dcrDocument;
    }

    public Set<FileStoreMapper> addToFileStore(final MultipartFile... files) {
        if (ArrayUtils.isNotEmpty(files))
            return Arrays.asList(files).stream().filter(file -> !file.isEmpty()).map(file -> {
                try {
                    return fileStoreService.store(file.getInputStream(), file.getOriginalFilename(),
                            file.getContentType(), FILESTORE_MODULECODE);
                } catch (final Exception e) {
                    throw new ApplicationRuntimeException(ERROR_OCCURRED_WHILE_GETTING_INPUTSTREAM, e);
                }
            }).collect(Collectors.toSet());
        else
            return Collections.emptySet();
    }

    public FileStoreMapper addToFileStore(final MultipartFile file) {
        FileStoreMapper fileStoreMapper = null;
        try {
            fileStoreMapper = fileStoreService.store(file.getInputStream(), file.getOriginalFilename(),
                    file.getContentType(), FILESTORE_MODULECODE);
        } catch (final IOException e) {
            throw new ApplicationRuntimeException(ERROR_OCCURRED_WHILE_GETTING_INPUTSTREAM, e);
        }
        return fileStoreMapper;
    }

    public String generatePlanPermissionNumber(final BpaApplication application) {
        PlanPermissionNumberGenerator planPermissionNumber = (PlanPermissionNumberGenerator) specificNoticeService
                .find(PlanPermissionNumberGeneratorImpl.class, specificNoticeService.getCityDetails());
        return planPermissionNumber.generatePlanPermissionNumber(application);
    }

    public Boolean checkAnyTaxIsPendingToCollect(BpaApplication bpaApplication) {
        return bpaUtils.checkAnyTaxIsPendingToCollect(bpaApplication.getDemand());
    }

    public Boolean applicationinitiatedByNonEmployee(BpaApplication bpaApplication) {
        return bpaUtils.applicationInitiatedByNonEmployee(bpaApplication.getCreatedBy());
    }

    public void buildOwnerDetails(final BpaApplication bpaApplication) {
        Applicant existApplicant = applicantService.findByNameAndMobileNumberAndGenderAndType(bpaApplication.getOwner().getName(),
                bpaApplication.getOwner().getUser().getMobileNumber(), bpaApplication.getOwner().getGender(),
                UserType.CITIZEN);
        if (existApplicant == null) {
            Applicant applicant = new Applicant();
            applicant.setName(bpaApplication.getOwner().getName());
            applicant.setAddress(bpaApplication.getOwner().getAddress());
            applicant.setGender(bpaApplication.getOwner().getGender());
            applicant.setAadhaarNumber(bpaApplication.getOwner().getAadhaarNumber());
            applicant.setEmailId(bpaApplication.getOwner().getEmailId());
            applicant.setUser(getCitizen(bpaApplication));
            bpaApplication.setOwner(applicant);
        } else {
        	existApplicant.setName(bpaApplication.getOwner().getName());
        	existApplicant.setAddress(bpaApplication.getOwner().getAddress());
        	existApplicant.setGender(bpaApplication.getOwner().getGender());
            bpaApplication.setOwner(existApplicant);
            bpaApplication.setMailPwdRequired(false);
        }
        if (!bpaApplication.getOwner().getUser().isActive())
            bpaApplication.getOwner().getUser().setActive(true);
    }

    private Citizen getCitizen(BpaApplication bpaApplication) {
        Citizen citizen;
        List<Citizen> citizensWithMobNo = citizenService
                .getCitizenByMobileNumberAndType(bpaApplication.getOwner().getUser().getMobileNumber(), UserType.CITIZEN);
        Citizen existingCitizen = null;
        if (!citizensWithMobNo.isEmpty())
            existingCitizen = citizensWithMobNo.get(0);
        if (existingCitizen == null) {
            List<User> busUsersWithAadhaar = userService
                    .getUserByAadhaarNumberAndType(bpaApplication.getOwner().getAadhaarNumber(), UserType.BUSINESS);
            List<User> citizensWithAadhaar = userService
                    .getUserByAadhaarNumberAndType(bpaApplication.getOwner().getAadhaarNumber(), UserType.CITIZEN);
            if (!busUsersWithAadhaar.isEmpty() || !citizensWithAadhaar.isEmpty()) {
                bpaApplication.getOwner().setAadhaarNumber(StringUtils.EMPTY);
            }
            List<User> citizensWithEmail = userService.getUsersByTypeAndEmailId(bpaApplication.getOwner().getEmailId(),
                    UserType.CITIZEN);
            List<User> busUsersWithEmail = userService.getUsersByTypeAndEmailId(bpaApplication.getOwner().getEmailId(),
                    UserType.BUSINESS);
            if (!busUsersWithEmail.isEmpty() || !citizensWithEmail.isEmpty()) {
                bpaApplication.getOwner().setEmailId(StringUtils.EMPTY);
            }
            citizen = createApplicantAsCitizen(bpaApplication.getOwner());
            bpaApplication.setMailPwdRequired(true);
        } else {
            citizen = existingCitizen;
            bpaApplication.setMailPwdRequired(false);
        }
        return citizen;
    }

    /**
     * @param bpaApplication
     * @return citizen
     */
    public Citizen createApplicantAsCitizen(BpaApplication bpaApplication) {
        Citizen citizen = new Citizen();
        citizen.setMobileNumber(bpaApplication.getOwner().getUser().getMobileNumber());
        citizen.setEmailId(bpaApplication.getOwner().getEmailId());
        citizen.setGender(bpaApplication.getOwner().getGender());
        citizen.setName(bpaApplication.getOwner().getName());
        String userName = bpaUtils.generateUserName(bpaApplication.getOwner().getName());
        User isUserExist = userService.getUserByUsername(userName);
        if (isUserExist == null)
            citizen.setUsername(userName);
        else
            citizen.setUsername(bpaUtils.generateUserName(bpaApplication.getOwner().getName()));
        citizen.setPassword(passwordEncoder.encode(bpaApplication.getOwner().getUser().getMobileNumber()));
        PermanentAddress address = new PermanentAddress();
        address.setStreetRoadLine(bpaApplication.getOwner().getAddress());
        citizen.addAddress(address);
        citizen.updateNextPwdExpiryDate(environmentSettings.userPasswordExpiryInDays());
        citizen.setAadhaarNumber(bpaApplication.getOwner().getAadhaarNumber());
        citizen.setTenantId(ApplicationConstant.STATE_TENANTID);
        citizen.setActive(true);
        citizen.addRole(roleService.getRoleByName(ROLE_CITIZEN));
        return citizen;
    }

    private List<PermitCoApplicant> buildCoApplicantDetails(final BpaApplication application) {
        List<PermitCoApplicant> coApplicants = new LinkedList<>();
        List<PermitCoApplicant> deleteCoApplicants = new LinkedList<>();
        for (PermitCoApplicant applicant : application.getCoApplicants()) {
            if (applicant.getCoApplicant().getName() != null) {
                applicant.setApplication(application);
                coApplicants.add(applicant);
            } else if (applicant.getId() != null) {
                deleteCoApplicants.add(applicant);
            }
        }
        if (coApplicants.isEmpty())
            application.getCoApplicants().clear();
        if (!deleteCoApplicants.isEmpty())
            coApplicantService.delete(deleteCoApplicants);
        return coApplicants;
    }

    @Transactional
    public void saveBpaApplication(BpaApplication bpaApp) {
        applicationBpaRepository.saveAndFlush(bpaApp);
    }

    public void saveApplicationForScheduler(BpaApplication bpaApp) {
        applicationBpaRepository.save(bpaApp);
    }

    public List<BpaApplication> findByStatusListOrderByCreatedDate(List<BpaStatus> listOfBpaStatus) {
        return applicationBpaRepository.findByStatusListOrderByCreatedDateAsc(listOfBpaStatus);
    }

    @SuppressWarnings(UNCHECKED)
    public List<BpaApplication> getBpaApplicationsForScheduleAndReSchedule(List<BpaStatus> bpaStatusList,
            List<Boundary> boundaryList,
            Integer totalAvailableSlots) {
        final Criteria criteria = entityManager.unwrap(Session.class)
                .createCriteria(BpaApplication.class, "application")
                .createAlias("application.siteDetail", "siteDetail")
                .createAlias("application.demand", "demand");
        criteria.add(Restrictions.in(APPLICATION_STATUS, bpaStatusList));
        criteria.createAlias("application.state", "state")
                .add(Restrictions.not(Restrictions.in("state.nextAction", FORWARDED_TO_CLERK, FWD_TO_OVRSR_FOR_FIELD_INS)));
        criteria.add(Restrictions.in("siteDetail.adminBoundary", boundaryList));
        criteria.add(Restrictions.eq("application.isOneDayPermitApplication", false));
        criteria.add(Restrictions.eq("application.failureInScheduler", false));

        criteria.add(Restrictions.leProperty("demand.baseDemand", "demand.amtCollected"));
        criteria.addOrder(Order.desc(APPLICATION_STATUS));
        criteria.addOrder(Order.asc("application.applicationDate"));
        criteria.addOrder(Order.asc("application.createdDate"));
        criteria.setMaxResults(totalAvailableSlots);
        return criteria.list();

    }

    @SuppressWarnings(UNCHECKED)
    public List<BpaApplication> getOneDayPermitAppForAppointment(BpaStatus bpaStatus, Boundary ward, List<Boundary> boundaryList,
            Integer totalAvailableSlots) {
        final Criteria criteria = entityManager.unwrap(Session.class)
                .createCriteria(BpaApplication.class, "application")
                .createAlias("application.siteDetail", "siteDetail")
                .createAlias("application.demand", "demand");
        criteria.add(Restrictions.in("siteDetail.adminBoundary", boundaryList));
        criteria.add(Restrictions.eq(APPLICATION_STATUS, bpaStatus));
        criteria.add(Restrictions.eq("application.isOneDayPermitApplication", true));
        criteria.add(Restrictions.eq("application.failureInScheduler", false));
        criteria.add(Restrictions.eq("siteDetail.electionBoundary", ward));
        criteria.add(Restrictions.leProperty("demand.baseDemand", "demand.amtCollected"));
        criteria.addOrder(Order.desc(APPLICATION_STATUS));
        criteria.addOrder(Order.asc("application.applicationDate"));
        criteria.addOrder(Order.asc("application.createdDate"));
        criteria.setMaxResults(totalAvailableSlots);
        return criteria.list();

    }

    public BigDecimal getTotalFloorArea(final BpaApplication application) {
        BigDecimal totalFloorArea = BigDecimal.ZERO;
        for (BuildingDetail buildingDetail : application.getBuildingDetail())
            for (ApplicationFloorDetail floorDetails : buildingDetail.getApplicationFloorDetails())
                totalFloorArea = totalFloorArea.add(floorDetails.getFloorArea());
        return totalFloorArea;
    }

    public BigDecimal getExistBldgTotalFloorArea(final BpaApplication application) {
        BigDecimal totalFloorArea = BigDecimal.ZERO;
        if (!application.getExistingBuildingDetails().isEmpty()
                && application.getExistingBuildingDetails().get(0).getTotalPlintArea() != null)
            for (ExistingBuildingFloorDetail floor : application.getExistingBuildingDetails().get(0)
                    .getExistingBuildingFloorDetails()) {
                totalFloorArea = totalFloorArea.add(floor.getFloorArea());
            }
        return totalFloorArea;
    }

    public boolean isOccupancyContains(final List<Occupancy> occupancies, final String occupancy) {
        Optional<Occupancy> occ = occupancies.stream().filter(o -> o.getCode().equalsIgnoreCase(occupancy)).findAny();
        return occ.isPresent();
    }

    /**
     * @param bpaApplication
     * @return citizen
     */
    public Citizen createApplicantAsCitizen(Applicant owner) {
        Citizen citizen = new Citizen();
        citizen.setMobileNumber(owner.getUser().getMobileNumber());
        citizen.setEmailId(owner.getEmailId());
        citizen.setGender(owner.getGender());
        citizen.setName(owner.getName());
        String userName = bpaUtils.generateUserName(owner.getName());
        User isUserExist = userService.getUserByUsername(userName);
        if (isUserExist == null)
            citizen.setUsername(userName);
        else
            citizen.setUsername(bpaUtils.generateUserName(owner.getName()));
        citizen.setPassword(passwordEncoder.encode(owner.getUser().getMobileNumber()));
        PermanentAddress address = new PermanentAddress();
        address.setStreetRoadLine(owner.getAddress());
        citizen.addAddress(address);
        citizen.updateNextPwdExpiryDate(environmentSettings.userPasswordExpiryInDays());
        citizen.setAadhaarNumber(owner.getAadhaarNumber());
        citizen.setTenantId(ApplicationConstant.CHANDIGARH_TENANTID);
        citizen.setActive(true);
        citizen.addRole(roleService.getRoleByName(ROLE_CITIZEN));
        return citizen;
    }

}
