/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2017>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.web.controller.transaction;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_APPROVED;
import static org.egov.bpa.utils.BpaConstants.WF_BA_CHIEF_ENG_CHECK_NOC_UPDATION;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CANCELLED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DIGI_SIGNED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFIED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_DOC_VERIFY_COMPLETED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_APPROVAL_PROCESS_INITIATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_NOCUPDATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_PENDING_FOR_RESCHEDULING;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RECORD_APPROVED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REGISTERED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REJECTED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_TS_INS;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_ONEDAYPERMIT;
import static org.egov.bpa.utils.BpaConstants.BPAREJECTIONFILENAME;
import static org.egov.bpa.utils.BpaConstants.BPA_APPLICATION;
import static org.egov.bpa.utils.BpaConstants.DESIGNATION_AE;
import static org.egov.bpa.utils.BpaConstants.DESIGNATION_OVERSEER;
import static org.egov.bpa.utils.BpaConstants.FIELD_INSPECTION_COMPLETED;
import static org.egov.bpa.utils.BpaConstants.FORWARDED_TO_CLERK;
import static org.egov.bpa.utils.BpaConstants.FORWARDED_TO_NOC_UPDATE;
import static org.egov.bpa.utils.BpaConstants.FWDINGTOLPINITIATORPENDING;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_AE_FOR_APPROVAL;
import static org.egov.bpa.utils.BpaConstants.FWD_TO_OVRSR_FOR_FIELD_INS;
import static org.egov.bpa.utils.BpaConstants.GENERATEPERMITORDER;
import static org.egov.bpa.utils.BpaConstants.ACCEPTASSCRUTINIZED;
import static org.egov.bpa.utils.BpaConstants.GENERATEREJECTNOTICE;
import static org.egov.bpa.utils.BpaConstants.GENERATEREVOCATIONNOTICE;
import static org.egov.bpa.utils.BpaConstants.LOWRISK;
import static org.egov.bpa.utils.BpaConstants.MESSAGE;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_FORWARD_FOR_PAYMENT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_CANCELAPPLICATION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_CREATED_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_DOC_SCRUTINY_SCHEDLE_PEND;
import static org.egov.bpa.utils.BpaConstants.WF_DOC_VERIFY_PEND;
import static org.egov.bpa.utils.BpaConstants.WF_INITIATE_REJECTION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_INIT_AUTO_RESCHDLE;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_STATE;
import static org.egov.bpa.utils.BpaConstants.WF_REVERT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_REVERT_TO_PREVIOUS_REVIEWER_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_SAVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_TS_INSPECTION_INITIATED;
import static org.egov.bpa.utils.BpaConstants.WF_FORWARD_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_BA_VARIFICATION_INITIATED;
import static org.egov.bpa.utils.BpaConstants.APPROVED;
import static org.egov.bpa.utils.BpaConstants.NOC_SEND_OBSERVATIONS;
import static org.egov.bpa.utils.BpaConstants.WF_BA_CHECK_NOC_UPDATION;
import static org.egov.bpa.utils.BpaConstants.WF_PERMIT_FEE_COLL_PENDING;
import static org.egov.bpa.utils.BpaConstants.WF_BA_AE_APPROVAL;
import static org.egov.bpa.utils.BpaConstants.WF_BA_SDO_APPROVAL;
import static org.egov.bpa.utils.BpaConstants.WF_BA_VARIFICATION_INITIATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_TYPE_MEDIUMRISK;
import static org.egov.bpa.utils.BpaConstants.REJECTION_INITIATED;
import static org.egov.bpa.utils.BpaConstants.WF_BA_NOC_UPDATION_IN_PROGRESS;
import static org.egov.bpa.utils.BpaConstants.WF_BA_FINAL_APPROVAL_PROCESS_INITIATED;
import static org.egov.bpa.utils.BpaConstants.WF_BA_AEE_APPLICATION_APPROVAL_PENDING;
import static org.egov.bpa.utils.BpaConstants.WF_BA_FORWARD_TO_SDO_BUILDING;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.entity.NocConfiguration;
import org.egov.bpa.master.entity.enums.CalculationType;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.transaction.entity.ApplicationFeeDetail;
import org.egov.bpa.transaction.entity.ApplicationPermitConditions;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BpaAppointmentSchedule;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.InConstructionInspection;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.transaction.entity.PermitInspectionApplication;
import org.egov.bpa.transaction.entity.PermitLetterToParty;
import org.egov.bpa.transaction.entity.PermitNocApplication;
import org.egov.bpa.transaction.entity.PermitNocDocument;
import org.egov.bpa.transaction.entity.SlotApplication;
import org.egov.bpa.transaction.entity.common.NoticeCondition;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.enums.ChecklistValues;
import org.egov.bpa.transaction.entity.enums.ConditionType;
import org.egov.bpa.transaction.entity.enums.NocIntegrationInitiationEnum;
import org.egov.bpa.transaction.entity.enums.NocIntegrationTypeEnum;
import org.egov.bpa.transaction.notice.PermitApplicationNoticesFormat;
import org.egov.bpa.transaction.notice.impl.PermitOrderFormatImpl;
import org.egov.bpa.transaction.notice.impl.PermitRejectionFormatImpl;
import org.egov.bpa.transaction.notice.impl.PermitRevocationFormat;
import org.egov.bpa.transaction.service.ApplicationBpaFeeCalculation;
import org.egov.bpa.transaction.service.ApplicationFeeService;
import org.egov.bpa.transaction.service.BpaApplicationPermitConditionsService;
import org.egov.bpa.transaction.service.BpaDcrService;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.InConstructionInspectionService;
import org.egov.bpa.transaction.service.InspectionApplicationService;
import org.egov.bpa.transaction.service.InspectionService;
import org.egov.bpa.transaction.service.LettertoPartyService;
import org.egov.bpa.transaction.service.NocStatusService;
import org.egov.bpa.transaction.service.PermitFeeCalculationService;
import org.egov.bpa.transaction.service.PermitFeeService;
import org.egov.bpa.transaction.service.PermitNocApplicationService;
import org.egov.bpa.transaction.service.PermitRevocationService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.PositionMasterService;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.pims.commons.Designation;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/application")
public class UpdateBpaApplicationController extends BpaGenericApplicationController {

    public static final String COMMON_ERROR = "common-error";
    private static final String WORK_FLOW_ACTION = "workFlowAction";
    private static final String AMOUNT_RULE = "amountRule";
    private static final String APPRIVALPOSITION = "approvalPosition";
    private static final String APPLICATION_HISTORY = "applicationHistory";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String APPROVAL_COMENT = "approvalComent";
    private static final String MSG_REJECT_FORWARD_REGISTRATION = "msg.reject.forward.registration";
    private static final String MSG_INITIATE_REJECTION = "msg.initiate.reject";
    private static final String MSG_UPDATE_FORWARD_REGISTRATION = "msg.update.forward.registration";
    private static final String MSG_BPA_APPLICATION_ACCEPTED = "msg.bpa.application.accepted";
    private static final String MSG_APPROVE_FORWARD_REGISTRATION = "msg.approve.success";
    private static final String APPLICATION_VIEW = "application-view";
    private static final String CREATEDOCUMENTSCRUTINY_FORM = "createdocumentscrutiny-form";
    private static final String DOCUMENTSCRUTINY_FORM = "documentscrutiny-form";
    private static final String BPAAPPLICATION_FORM = "bpaapplication-Form";
    private static final String BPA_APPLICATION_RESULT = "bpa-application-result";
    private static final String PDFEXTN = ".pdf";
    private static final String BPA_PROCEED_FEE_MSG = "Set the minimal fee using modify fee button and proceed further";
    private static final String PROPERTY_DOCUMENTS_VERIFICATION_INITIATED = "Property documents verification initiated"; 

    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    private LettertoPartyService lettertoPartyService;
    @Autowired
    private BpaApplicationPermitConditionsService bpaApplicationPermitConditionsService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private BpaDcrService bpaDcrService;
    @Autowired
    protected PermitFeeService permitFeeService;
    @Autowired
    private CustomImplProvider findImplementationBean;
    @Autowired
    private PermitRevocationService permitRevocationService;
    @Autowired
    private PermitNocApplicationService permitNocService;
    @Autowired
    private NocConfigurationService nocConfigurationService;
    @Autowired
    private BpaStatusService statusService;
    @Autowired
    private NocStatusService nocStatusService;
    @Autowired
    private InspectionApplicationService inspectionAppService;
    @Autowired
    private InConstructionInspectionService inspectionConstService;
	@Autowired
	@Qualifier("workflowService")
	private SimpleWorkflowService<BpaApplication> bpaApplicationWorkflowService;

    
    @ModelAttribute
    public BpaApplication getBpaApplication(@PathVariable final String applicationNumber) {
        return applicationBpaService.findByApplicationNumber(applicationNumber);
    }

    @GetMapping("/update/{applicationNumber}")
    public String updateApplicationForm(final Model model, @PathVariable final String applicationNumber) {
        final BpaApplication application = getBpaApplication(applicationNumber);
        List<PermitNocApplication> nocApplication = permitNocService.findByPermitApplicationNumber(applicationNumber);
        prepareActions(model, application);
        loadCommonApplicationDetails(model, application);
        buildRejectionReasons(model, application);
        model.addAttribute("workFlowByNonEmp", applicationBpaService.applicationinitiatedByNonEmployee(application));
        model.addAttribute("nocApplication", nocApplication);
        model.addAttribute("citizenOrBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());
        //Added By Narendra For NOC Changes
        model.addAttribute("userType", securityUtils.getCurrentUser().getUsername().substring(0, 4));
        if (application != null) {
            loadFormData(model, application);
            bpaUtils.loadBoundary(application);
            if (application.getState() != null
                    && application.getState().getNextAction().equalsIgnoreCase(FORWARDED_TO_CLERK))
                return APPLICATION_VIEW;
            else if (application.getState() != null
                    && application.getState().getNextAction().equalsIgnoreCase(WF_DOC_SCRUTINY_SCHEDLE_PEND)
                    || application.getState().getNextAction().equalsIgnoreCase(WF_DOC_VERIFY_PEND)
                    || application.getState().getNextAction().equalsIgnoreCase(WF_INIT_AUTO_RESCHDLE))
                return DOCUMENTSCRUTINY_FORM;
        }
        return APPLICATION_VIEW;
    }

    @GetMapping("/documentscrutiny/{applicationNumber}")
    public String documentScrutinyForm(final Model model, @PathVariable final String applicationNumber) {
        final BpaApplication application = getBpaApplication(applicationNumber);
        if (validateOnDocumentScrutiny(model, application.getStatus()) || checkIsRescheduledOnScrutiny(model, application)) {
            return COMMON_ERROR;
        }
        buildRejectionReasons(model, application);
        loadFormData(model, application);
        bpaUtils.loadBoundary(application);
        loadCommonApplicationDetails(model, application);
        model.addAttribute("showDcrDocuments",
                bpaDcrService.isEdcrIntegrationRequireByService(application.getServiceType().getCode()));
        model.addAttribute("documentScrutinyValues", ChecklistValues.values());
        List<ChecklistServiceTypeMapping> docScrutnyChklist = checklistServiceTypeService
                .findByActiveChecklistAndServiceType(application.getServiceType().getDescription(), "PERMITDOCSCRTNY");
        model.addAttribute("documentScrutinyChecklist", docScrutnyChklist);
        model.addAttribute("planScrutinyValues", ChecklistValues.values());
        model.addAttribute("loginUser", securityUtils.getCurrentUser());
        getDcrDocumentsUploadMode(model);
        return CREATEDOCUMENTSCRUTINY_FORM;
    }

    @PostMapping("/documentscrutiny/{applicationNumber}")
    public String documentScrutinyForm(@Valid @ModelAttribute(BPA_APPLICATION) BpaApplication bpaApplication,
            @PathVariable final String applicationNumber, final BindingResult resultBinder,
            final HttpServletRequest request, @RequestParam final BigDecimal amountRule,
            final Model model, final RedirectAttributes redirectAttributes) throws IOException {
        if (resultBinder.hasErrors()) {
            loadFormData(model, bpaApplication);
            loadCommonApplicationDetails(model, bpaApplication);
            return CREATEDOCUMENTSCRUTINY_FORM;
        }

        List<Designation> loginUserDesignations = Collections.emptyList();
        List<Assignment> loginUserAssignments = bpaWorkFlowService.getAllActiveAssignmentsForUser(securityUtils.getCurrentUser().getId());
        if (!loginUserAssignments.isEmpty())
            loginUserDesignations = bpaWorkFlowService.getAllActiveAssignmentsForUser(securityUtils.getCurrentUser().getId())
                    .stream().map(Assignment::getDesignation).collect(Collectors.toList());
        if (!loginUserDesignations.isEmpty() &&
                !loginUserDesignations
                        .contains(bpaApplication.getCurrentState().getOwnerPosition().getDeptDesig().getDesignation())
                &&
                !"Senior Assistant".equalsIgnoreCase(
                        bpaApplication.getCurrentState().getOwnerPosition().getDeptDesig().getDesignation().getName())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.application.scrutiny.processed", new String[] {},
                    LocaleContextHolder.getLocale()));
            return COMMON_ERROR;
        }
        Long approvalPosition;
        String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
        String approvalComent = request.getParameter(APPROVAL_COMENT);
        if (request.getParameter(APPRIVALPOSITION) != null) {
            // In case of one day permit, on reject from clerk should be sent to AE
            if (workFlowAction.equalsIgnoreCase(WF_INITIATE_REJECTION_BUTTON) && bpaApplication.getIsOneDayPermitApplication()
                    && (bpaApplication.getCurrentState().getValue().equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
                            || bpaApplication.getCurrentState().getValue().equalsIgnoreCase(APPLICATION_STATUS_RESCHEDULED))) {
                approvalPosition = bpaUtils.getUserPositionIdByZone(DESIGNATION_AE,
                        bpaUtils.getBoundaryForWorkflow(bpaApplication.getSiteDetail().get(0)).getId());
            } else
                approvalPosition = Long.valueOf(request.getParameter(APPRIVALPOSITION));
            List<Assignment> assignments;
            if (null == approvalPosition)
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(bpaApplication.getCurrentState().getOwnerPosition().getId(), new Date());
            else
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(approvalPosition, new Date());
            Position pos = assignments.get(0).getPosition();
            User user = assignments.get(0).getEmployee();
            if (!bpaApplication.getPermitDocuments().isEmpty())
                applicationBpaService.persistOrUpdateApplicationDocument(bpaApplication);
            bpaApplication.getDocumentScrutiny().get(0).getDocScrutiny().getDocumentScrutinyChecklists().forEach(
                    docScrutiny -> docScrutiny.setDocumentScrutiny(bpaApplication.getDocumentScrutiny().get(0).getDocScrutiny()));
            BpaApplication bpaAppln = applicationBpaService.updateApplication(bpaApplication, approvalPosition, workFlowAction,
                    amountRule);
            String message;
            if (WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(workFlowAction) && !bpaApplication.getIsOneDayPermitApplication()) {
                User userObj = workflowHistoryService
                        .getUserPositionByPassingPosition(bpaAppln.getCurrentState().getOwnerPosition().getId());
                message = getMessageOnRejectionInitiation(approvalComent, bpaAppln, userObj, MSG_INITIATE_REJECTION,
                        bpaAppln.getCurrentState().getOwnerPosition());
            } else if (WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(workFlowAction)
                    && bpaApplication.getIsOneDayPermitApplication()) {
                message = getMessageOnRejectionInitiation(approvalComent, bpaAppln, user, MSG_INITIATE_REJECTION, pos);
            } else
                message = messageSource.getMessage("msg.update.forward.documentscrutiny", new String[] {
                        user == null ? ""
                                : user.getUsername().concat("~")
                                        .concat(getDesinationNameByPosition(pos)),
                        bpaAppln.getApplicationNumber() }, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute(MESSAGE, message);
        }

        return "redirect:/application/success/" + bpaApplication.getApplicationNumber();
    }

    @PostMapping("/update-submit/{applicationNumber}")
    public String updateApplication(@Valid @ModelAttribute(BPA_APPLICATION) BpaApplication bpaApplication,
            @PathVariable final String applicationNumber,
            final BindingResult resultBinder,
            final HttpServletRequest request,
            final Model model,
            final RedirectAttributes redirectAttributes,
            @RequestParam final BigDecimal amountRule) throws IOException {    	
        String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
        String approvalComent = request.getParameter(APPROVAL_COMENT);

        if (resultBinder.hasErrors()) {
            loadFormData(model, bpaApplication);
            return BPAAPPLICATION_FORM;
        }

        Position ownerPosition = bpaApplication.getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;

        if (bpaApplicationValidationService.validateBuildingDetails(bpaApplication, model)) {
            loadFormData(model, bpaApplication);
            loadCommonApplicationDetails(model, bpaApplication);
            return BPAAPPLICATION_FORM;
        }

        String feeCalculationMode = bpaUtils.getBPAFeeCalculationMode();

        if (APPLICATION_STATUS_NOCUPDATED.equals(bpaApplication.getStatus().getCode())
                && feeCalculationMode.equalsIgnoreCase(BpaConstants.MANUAL)) {
            List<PermitFee> permitFeeList = permitFeeService
                    .getPermitFeeListByApplicationId(bpaApplication.getId());
            if (permitFeeList.isEmpty() || permitFeeList == null) {
                model.addAttribute("feeNotDefined", BPA_PROCEED_FEE_MSG);
                loadFormData(model, bpaApplication);
                loadCommonApplicationDetails(model, bpaApplication);
                bpaUtils.loadBoundary(bpaApplication);
                model.addAttribute("workFlowByNonEmp", applicationBpaService.applicationinitiatedByNonEmployee(bpaApplication));
                return APPLICATION_VIEW;
            }
        }

        if ((WF_APPROVE_BUTTON.equalsIgnoreCase(workFlowAction) || WF_FORWARD_FOR_PAYMENT_BUTTON.equalsIgnoreCase(workFlowAction))
                && feeCalculationMode.equalsIgnoreCase(BpaConstants.MANUAL)) {
            List<PermitFee> permitFeeList = permitFeeService
                    .getPermitFeeListByApplicationId(bpaApplication.getId());
            if (permitFeeList.isEmpty() || permitFeeList == null) {
                model.addAttribute("feeNotDefined", BPA_PROCEED_FEE_MSG);
                loadFormData(model, bpaApplication);
                loadCommonApplicationDetails(model, bpaApplication);
                return APPLICATION_VIEW;
            }
        }

        if (WF_CANCELAPPLICATION_BUTTON.equalsIgnoreCase(workFlowAction)) {
            StateHistory<Position> stateHistory = bpaApplication.getStateHistory().stream()
                    .filter(history -> history.getValue().equalsIgnoreCase(APPLICATION_STATUS_REJECTED))
                    .findAny().orElse(null);
            if (stateHistory != null)
                approvalComent = stateHistory.getComments();
        }

        String message;
        Long approvalPosition = null;
        Position pos = null;
        
        if (WF_FORWARD_BUTTON.equalsIgnoreCase(workFlowAction) && 
        		WF_BA_VARIFICATION_INITIATED.equalsIgnoreCase(bpaApplication.getState().getNextAction())) {
        	List<PermitNocApplication> permitNocApplied = permitNocService.findByPermitApplicationNumber(bpaApplication.getApplicationNumber());
            if (permitNocApplied.isEmpty()) {
    	    	boolean isEdcrIntegrationRequire = bpaDcrService.isEdcrIntegrationRequireByService(bpaApplication.getServiceType().getCode());
    	    	if (isEdcrIntegrationRequire)
    	            permitNocService.initiateNoc(bpaApplication);
            }
        }
        
        if (WF_TS_INSPECTION_INITIATED.equalsIgnoreCase(bpaApplication.getStatus().getCode())) {
            pos = positionMasterService.getPositionById(bpaWorkFlowService
                    .getTownSurveyorInspnInitiator(bpaApplication.getStateHistory(), bpaApplication.getCurrentState()));
            approvalPosition = positionMasterService
                    .getPositionById(bpaWorkFlowService.getTownSurveyorInspnInitiator(bpaApplication.getStateHistory(),
                            bpaApplication.getCurrentState()))
                    .getId();
        } else if (WF_REVERT_BUTTON.equalsIgnoreCase(workFlowAction) || WF_REVERT_TO_PREVIOUS_REVIEWER_BUTTON.equalsIgnoreCase(workFlowAction)) {
            pos = bpaApplication.getCurrentState().getPreviousOwner();
            approvalPosition = bpaApplication.getCurrentState().getPreviousOwner().getId();
        } else if (FWDINGTOLPINITIATORPENDING.equalsIgnoreCase(bpaApplication.getState().getNextAction())) {
            List<PermitLetterToParty> lettertoParties = lettertoPartyService.findByBpaApplicationOrderByIdDesc(bpaApplication);
            StateHistory<Position> stateHistory = bpaWorkFlowService.getStateHistoryToGetLPInitiator(
                    bpaApplication.getStateHistory(),
                    lettertoParties.get(0).getLetterToParty().getStateForOwnerPosition());
            approvalPosition = stateHistory.getOwnerPosition().getId();
        } 
        else if ("Revert to HDM".equalsIgnoreCase(workFlowAction)) {
        	WorkFlowMatrix wfMatrix = bpaApplicationWorkflowService.getWfMatrix(bpaApplication.getStateType(), null, amountRule,
        			bpaApplication.getApplicationType().getName(), "Property documents verification initiated",
                    null);
        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaApplication.getSiteDetail().get(0) != null
                            && bpaApplication.getSiteDetail().get(0).getAdminBoundary() != null
                                    ? bpaApplication.getSiteDetail().get(0).getAdminBoundary().getId()
                                    : null);
        }
        else if ("Revert to BA".equalsIgnoreCase(workFlowAction)) {
        	WorkFlowMatrix wfMatrix = bpaApplicationWorkflowService.getWfMatrix(bpaApplication.getStateType(), null, amountRule,
        			bpaApplication.getApplicationType().getName(), "NEW",
                    null);
        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaApplication.getSiteDetail().get(0) != null
                            && bpaApplication.getSiteDetail().get(0).getAdminBoundary() != null
                                    ? bpaApplication.getSiteDetail().get(0).getAdminBoundary().getId()
                                    : null);
        }
        else if ("Revert to SDO".equalsIgnoreCase(workFlowAction)) {
        	WorkFlowMatrix wfMatrix = bpaApplicationWorkflowService.getWfMatrix(bpaApplication.getStateType(), null, amountRule,
        			bpaApplication.getApplicationType().getName(), "NOC updation initiated",
                    null);
        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaApplication.getSiteDetail().get(0) != null
                            && bpaApplication.getSiteDetail().get(0).getAdminBoundary() != null
                                    ? bpaApplication.getSiteDetail().get(0).getAdminBoundary().getId()
                                    : null);
        }
        else if ("Send Back To SDOMC".equalsIgnoreCase(workFlowAction)) {
        	WorkFlowMatrix wfMatrix = bpaApplicationWorkflowService.getWfMatrix(bpaApplication.getStateType(), null, amountRule,
        			bpaApplication.getApplicationType().getName(), "Property documents verification initiated",
        			"Forwarded to property documents verification");
        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaApplication.getSiteDetail().get(0) != null
                            && bpaApplication.getSiteDetail().get(0).getAdminBoundary() != null
                                    ? bpaApplication.getSiteDetail().get(0).getAdminBoundary().getId()
                                    : null);
        }
        else if ("Send Back To MCA".equalsIgnoreCase(workFlowAction)) {
        	WorkFlowMatrix wfMatrix = bpaApplicationWorkflowService.getWfMatrix(bpaApplication.getStateType(), null, amountRule,
        			bpaApplication.getApplicationType().getName(), "Registered",
                    "Forward to tehsildar is pending");
        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaApplication.getSiteDetail().get(0) != null
                            && bpaApplication.getSiteDetail().get(0).getAdminBoundary() != null
                                    ? bpaApplication.getSiteDetail().get(0).getAdminBoundary().getId()
                                    : null);
        }
        else if ("Send Back To Tehsildar".equalsIgnoreCase(workFlowAction)) {
        	WorkFlowMatrix wfMatrix = bpaApplicationWorkflowService.getWfMatrix(bpaApplication.getStateType(), null, amountRule,
        			bpaApplication.getApplicationType().getName(), "Registered",
                    "Forward to junior engineer is pending");
        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaApplication.getSiteDetail().get(0) != null
                            && bpaApplication.getSiteDetail().get(0).getAdminBoundary() != null
                                    ? bpaApplication.getSiteDetail().get(0).getAdminBoundary().getId()
                                    : null);
        }
        else if ("Send Back To JE".equalsIgnoreCase(workFlowAction)) {
        	WorkFlowMatrix wfMatrix = bpaApplicationWorkflowService.getWfMatrix(bpaApplication.getStateType(), null, amountRule,
        			bpaApplication.getApplicationType().getName(), "NEW",
                    "Forward to section clerk is pending");
        	approvalPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaApplication.getSiteDetail().get(0) != null
                            && bpaApplication.getSiteDetail().get(0).getAdminBoundary() != null
                                    ? bpaApplication.getSiteDetail().get(0).getAdminBoundary().getId()
                                    : null);
        }

        else if (StringUtils.isNotBlank(request.getParameter(APPRIVALPOSITION))
                && !WF_REJECT_BUTTON.equalsIgnoreCase(workFlowAction)
                && !GENERATEREJECTNOTICE.equalsIgnoreCase(workFlowAction)) {
            approvalPosition = Long.valueOf(request.getParameter(APPRIVALPOSITION));
        } // For one day permit, on reject from AE it's forwarded to SUP (workflow user)
        else if (WF_REJECT_BUTTON.equalsIgnoreCase(workFlowAction)) {
            BpaStatus status = statusService.findByModuleTypeAndCode(BpaConstants.CHECKLIST_TYPE_NOC,
                    BpaConstants.NOC_APPL_REJECTED);
            List<PermitNocApplication> permitNoc = permitNocService
                    .findByPermitApplicationNumber(bpaApplication.getApplicationNumber());
            if (!permitNoc.isEmpty()) {
                for (PermitNocApplication nocApp : permitNoc) {
                    nocApp.getBpaNocApplication().setStatus(status);
                }
                permitNocService.save(permitNoc);
            }

            if (bpaApplication.getIsOneDayPermitApplication() && null != request.getParameter(APPRIVALPOSITION)
                    && !"".equals(request.getParameter(APPRIVALPOSITION))) {
                approvalPosition = Long.valueOf(request.getParameter(APPRIVALPOSITION));
            } else if (!bpaApplication.getIsOneDayPermitApplication()) {
                pos = bpaWorkFlowService.getApproverPositionOfElectionWardByCurrentState(bpaApplication, WF_REJECT_STATE);
                approvalPosition = pos.getId();
            }
        }
        
        if (!bpaApplication.getPermitDocuments().isEmpty())
            applicationBpaService.persistOrUpdateApplicationDocument(bpaApplication);
        
        if (bpaApplication.getWorkflowFile().getFiles() != null && bpaApplication.getWorkflowFile().getFiles().length > 0)
            applicationBpaService.persistWfDocuments(bpaApplication);

        if (bpaApplication.getCurrentState().getValue().equals(WF_NEW_STATE))
            return applicationBpaService.redirectToCollectionOnForward(bpaApplication, model);

        BpaApplication bpaAppln = applicationBpaService.updateApplication(bpaApplication, approvalPosition, workFlowAction,
                amountRule);
        bpaUtils.updatePortalUserinbox(bpaAppln, null);
        List<Assignment> assignments;
        if (null == approvalPosition)
            assignments = bpaWorkFlowService
                    .getAssignmentsByPositionAndDate(bpaAppln.getCurrentState().getOwnerPosition().getId(), new Date());
        else
            assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(approvalPosition, new Date());
        if (pos == null)
            pos = assignments.get(0).getPosition();
        User user = assignments.get(0).getEmployee();
        if (WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(workFlowAction) && !bpaApplication.getIsOneDayPermitApplication()) {
            User userObj = workflowHistoryService
                    .getUserPositionByPassingPosition(bpaAppln.getCurrentState().getOwnerPosition().getId());
            message = getMessageOnRejectionInitiation(approvalComent, bpaAppln, userObj, MSG_INITIATE_REJECTION,
                    bpaAppln.getCurrentState().getOwnerPosition());
        } else if (WF_INITIATE_REJECTION_BUTTON.equalsIgnoreCase(workFlowAction) && bpaApplication.getIsOneDayPermitApplication())
            message = getMessageOnRejectionInitiation(approvalComent, bpaAppln, user, MSG_INITIATE_REJECTION, pos);
        else if (WF_REJECT_BUTTON.equalsIgnoreCase(workFlowAction))
            message = getMessageOnRejectionInitiation(approvalComent, bpaAppln, user, MSG_REJECT_FORWARD_REGISTRATION, pos);
        else if (WF_SAVE_BUTTON.equalsIgnoreCase(workFlowAction))
            message = messageSource.getMessage("msg.noc.update.success", new String[] {}, LocaleContextHolder.getLocale());
        else if ((WF_APPROVE_BUTTON.equalsIgnoreCase(workFlowAction) || WF_FORWARD_FOR_PAYMENT_BUTTON.equalsIgnoreCase(workFlowAction)) && ! bpaAppln.getApplicationType().getName().equals(BpaConstants.LOWRISK))
            message = messageSource.getMessage(MSG_APPROVE_FORWARD_REGISTRATION, new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    bpaAppln.getApplicationNumber() }, LocaleContextHolder.getLocale());
        else if ((WF_APPROVE_BUTTON.equalsIgnoreCase(workFlowAction) || WF_FORWARD_FOR_PAYMENT_BUTTON.equalsIgnoreCase(workFlowAction)) && bpaAppln.getApplicationType().getName().equals(BpaConstants.LOWRISK))
            message = messageSource.getMessage("msg.nocapplcn.apprvd.succes", new String[] {
                    user == null ? ""
                            : bpaAppln.getApplicationNumber() }, LocaleContextHolder.getLocale());
        
        else
            message = messageSource.getMessage(MSG_UPDATE_FORWARD_REGISTRATION, new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                    bpaAppln.getApplicationNumber() }, LocaleContextHolder.getLocale());

        redirectAttributes.addFlashAttribute(MESSAGE, message);
        if (APPLICATION_STATUS_CANCELLED.equalsIgnoreCase(bpaApplication.getStatus().getCode())) {
            ReportOutput reportOutput = null;
            if (isNotBlank(workFlowAction) && GENERATEREJECTNOTICE.equalsIgnoreCase(workFlowAction)) {
                PermitApplicationNoticesFormat bpaNoticeFeature = (PermitApplicationNoticesFormat) specificNoticeService
                        .find(PermitRejectionFormatImpl.class, specificNoticeService.getCityDetails());
                reportOutput = bpaNoticeFeature
                        .generateNotice(applicationBpaService.findByApplicationNumber(applicationNumber));
            }
            bpaSmsAndEmailService.sendSMSAndEmail(bpaAppln, reportOutput, BPAREJECTIONFILENAME + PDFEXTN);
        }
        if (isNotBlank(workFlowAction) 
        		&& GENERATEPERMITORDER.equalsIgnoreCase(workFlowAction)) {

            PermitApplicationNoticesFormat bpaNoticeFeature = (PermitApplicationNoticesFormat) specificNoticeService
                    .find(PermitOrderFormatImpl.class, specificNoticeService.getCityDetails());
            ReportOutput reportOutput = bpaNoticeFeature
                    .generateNotice(applicationBpaService.findByApplicationNumber(applicationNumber));
            bpaSmsAndEmailService.sendSmsAndEmailOnPermitOrderGeneration(bpaApplication, reportOutput);

            return "redirect:/application/generatepermitorder/" + bpaAppln.getApplicationNumber();
        } else if (isNotBlank(workFlowAction) 
        				&& ACCEPTASSCRUTINIZED.equalsIgnoreCase(workFlowAction)) {

            PermitApplicationNoticesFormat bpaNoticeFeature = (PermitApplicationNoticesFormat) specificNoticeService
                    .find(PermitOrderFormatImpl.class, specificNoticeService.getCityDetails());
            bpaNoticeFeature.generateNotice(applicationBpaService.findByApplicationNumber(applicationNumber));
            //ReportOutput reportOutput = bpaNoticeFeature.generateNotice(applicationBpaService.findByApplicationNumber(applicationNumber));
            //bpaSmsAndEmailService.sendSmsAndEmailOnPermitOrderGeneration(bpaApplication, reportOutput);
            message = messageSource.getMessage(MSG_BPA_APPLICATION_ACCEPTED, null, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute(MESSAGE, message);
        } else if (isNotBlank(workFlowAction) && GENERATEREJECTNOTICE.equalsIgnoreCase(workFlowAction))
            return "redirect:/application/rejectionnotice/" + bpaAppln.getApplicationNumber();

        if (isNotBlank(workFlowAction) && GENERATEREVOCATIONNOTICE.equalsIgnoreCase(workFlowAction)) {
            PermitRevocationFormat permitRevocationFormatFeature = (PermitRevocationFormat) findImplementationBean
                    .find(PermitRevocationFormat.class, findImplementationBean.getCityDetails());
            ReportOutput reportOutput = permitRevocationFormatFeature
                    .generateNotice(bpaApplication.getPermitRevocation().get(0));

            permitRevocationService.sendSmsAndEmail(bpaApplication.getPermitRevocation().get(0), reportOutput);

            return "redirect:/application/revocation/generateorder/"
                    + bpaApplication.getPermitRevocation().get(0).getRevocationNumber();
        }

        return "redirect:/application/success/" + bpaAppln.getApplicationNumber();
    }

    @GetMapping("/success/{applicationNumber}")
    public String success(@PathVariable final String applicationNumber, final Model model) {
        BpaApplication application = getBpaApplication(applicationNumber);
        loadCommonApplicationDetails(model, application);
        bpaUtils.loadBoundary(application);
        List<PermitNocApplication> nocApplication = permitNocService.findByPermitApplicationNumber(applicationNumber);

        model.addAttribute("nocApplication", nocApplication);
        for (PermitNocDocument nocDocument : application.getPermitNocDocuments()) {
            for (PermitNocApplication pna : nocApplication) {
                if (nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode()
                        .equalsIgnoreCase(pna.getBpaNocApplication().getNocType())) {
                    nocDocument.setPermitNoc(pna);
                }
            }
        }

        model.addAttribute(BPA_APPLICATION, application);
        return BPA_APPLICATION_RESULT;
    }

    private void prepareActions(final Model model, final BpaApplication application) {
        State<Position> currentState = application.getCurrentState();
        String currentStatus = application.getStatus().getCode();
        String currentStateValue = currentState.getValue();
        String pendingAction = currentState.getNextAction();
        String mode = null;
        AppointmentSchedulePurpose scheduleType = null;
        List<String> purposeInsList = new ArrayList<>();
        for (BpaAppointmentSchedule schedule : application.getAppointmentSchedule())
            if (AppointmentSchedulePurpose.INSPECTION.equals(schedule.getPurpose()))
                purposeInsList.add(schedule.getPurpose().name());

        Assignment appvrAssignment = bpaWorkFlowService.getApproverAssignment(currentState.getOwnerPosition());
        if (currentState.getOwnerUser() != null) {
            List<Assignment> assignments = bpaWorkFlowService.getAssignmentByPositionAndUserAsOnDate(
                    currentState.getOwnerPosition().getId(), currentState.getOwnerUser().getId(),
                    currentState.getLastModifiedDate());
            if (!assignments.isEmpty())
                appvrAssignment = assignments.get(0);
        }
        if (appvrAssignment == null)
            appvrAssignment = bpaWorkFlowService.getAssignmentsByPositionAndDate(currentState.getOwnerPosition().getId(), new Date()).get(0);

        boolean hasInspectionStatus = hasInspectionStatus(currentStatus);
        boolean hasInspectionPendingAction = FWD_TO_OVRSR_FOR_FIELD_INS.equalsIgnoreCase(pendingAction);
        boolean isAfterTSInspection = DESIGNATION_OVERSEER.equals(appvrAssignment.getDesignation().getName())
                && APPLICATION_STATUS_TS_INS.equalsIgnoreCase(currentStatus);

        // To show reschedule scrutiny button to employee
        if ((APPLICATION_STATUS_SCHEDULED.equals(currentStatus) ||
                APPLICATION_STATUS_RESCHEDULED.equals(currentStatus) ||
                APPLICATION_STATUS_PENDING_FOR_RESCHEDULING.equals(currentStatus))
                && !application.getIsRescheduledByEmployee()) {
            mode = "showRescheduleToEmployee";
        } else if (WF_CREATED_STATE.equalsIgnoreCase(currentStatus)) {
            mode = "view";
        } else if ((hasOneDayPermitRequireInspectionScheduling(application)
                || hasRegularPermitRequireInspectionScheduling(application))
                && hasInspectionStatus && hasInspectionPendingAction
                && purposeInsList.isEmpty()) {
            mode = "newappointment";
        } else if (hasInspectionPendingAction && hasInspectionStatus && application.getPermitInspections().isEmpty()) {
            mode = "captureInspection";
            model.addAttribute("isInspnRescheduleEnabled", hasOneDayPermitRequireInspectionScheduling(application)
                    || hasRegularPermitRequireInspectionScheduling(application));
            scheduleType = AppointmentSchedulePurpose.INSPECTION;
        } else if ((hasInspectionPendingAction && hasInspectionStatus)
                || isAfterTSInspection && !application.getPermitInspections().isEmpty()) {
            mode = "modifyInspection";
            scheduleType = AppointmentSchedulePurpose.INSPECTION;
        } else if (FORWARDED_TO_NOC_UPDATE.equalsIgnoreCase(pendingAction)
                && APPLICATION_STATUS_DOC_VERIFY_COMPLETED.equalsIgnoreCase(currentStatus)
                && !application.getApplicationType().getName().equals(LOWRISK)) {
            model.addAttribute("showUpdateNoc", true);
            nocStatusService.updateNocStatus(application);
        } else if (FWD_TO_AE_FOR_APPROVAL.equalsIgnoreCase(pendingAction)
                && !application.getPermitInspections().isEmpty() && !application.getApplicationType().getName().equals(LOWRISK)) {
            String feeCalculationMode = bpaUtils.getBPAFeeCalculationMode();
            boolean isFeeModifiableIndividual = false;
            for (PermitFee fee : application.getPermitFee()) {
                for (ApplicationFeeDetail feeDtl : fee.getApplicationFee().getApplicationFeeDetail()) {
                    if (CalculationType.OVERRIDE.equals(feeDtl.getBpaFeeMapping().getCalculationType())
                            || CalculationType.MANUAL.equals(feeDtl.getBpaFeeMapping().getCalculationType())) {
                        isFeeModifiableIndividual = true;
                        break;
                    }
                }
            }
            if (isFeeModifiableIndividual && (feeCalculationMode.equalsIgnoreCase(BpaConstants.MANUAL)
                    || feeCalculationMode.equalsIgnoreCase(BpaConstants.AUTOFEECALEDIT)))
                mode = "initiatedForApproval";
        }

        // To show/hide TS inspection required checkbox
        if (!application.getIsOneDayPermitApplication() && !application.getPermitInspections().isEmpty()
                && ((hasInspectionStatus && hasInspectionPendingAction)
                        || (FIELD_INSPECTION_COMPLETED.equalsIgnoreCase(currentStateValue)
                                && APPLICATION_STATUS_DOC_VERIFY_COMPLETED.equalsIgnoreCase(currentStatus))))
            model.addAttribute("isTSInspectionRequired", false);

        if (mode == null)
            mode = "edit";

        model.addAttribute("scheduleType", scheduleType);
        model.addAttribute("mode", mode);
    }

    private boolean hasInspectionStatus(final String status) {
        return APPLICATION_STATUS_DOC_VERIFIED.equalsIgnoreCase(status)
                || APPLICATION_STATUS_REGISTERED.equalsIgnoreCase(status);
    }

    private boolean hasOneDayPermitRequireInspectionScheduling(final BpaApplication application) {
        return application.getIsOneDayPermitApplication() && bpaUtils.isOneDayPermitInspectionSchedulingIntegrationRequired();
    }

    private boolean hasRegularPermitRequireInspectionScheduling(final BpaApplication application) {
        return !application.getIsOneDayPermitApplication() && bpaUtils.isRegularPermitInspectionSchedulingIntegrationRequired();
    }

    private void loadFormData(final Model model, final BpaApplication application) {
        getApplicationConfigurations(model);
        applicationBpaService.buildExistingAndProposedBuildingDetails(application);
        model.addAttribute("stateType", application.getClass().getSimpleName());
        final WorkflowContainer workflowContainer = new WorkflowContainer();
        // added for one day permit. amount rule and pending action needs to be set only for other services.
        if (application.getIsOneDayPermitApplication()) {
            model.addAttribute(ADDITIONALRULE, APPLICATION_TYPE_ONEDAYPERMIT);
            workflowContainer.setAdditionalRule(APPLICATION_TYPE_ONEDAYPERMIT);
            if (application.getState() != null && application.getState().getValue().equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
                    || application.getState().getNextAction().equalsIgnoreCase(FORWARDED_TO_CLERK))
                workflowContainer.setPendingActions(application.getState().getNextAction());

        } else {
            model.addAttribute(ADDITIONALRULE, application.getApplicationType().getName());
            workflowContainer.setAdditionalRule(application.getApplicationType().getName());
            List<PermitLetterToParty> lettertoParties = lettertoPartyService.findByBpaApplicationOrderByIdDesc(application);

            if (application.getState() != null
                    && application.getState().getValue().equalsIgnoreCase(APPLICATION_STATUS_REGISTERED) ||
                    application.getState().getValue().equalsIgnoreCase(APPLICATION_STATUS_SCHEDULED)
                    || application.getState().getValue().equalsIgnoreCase(APPLICATION_STATUS_RESCHEDULED)) {
                workflowContainer.setPendingActions(application.getState().getNextAction());
            }

            // Setting AmountRule to decide no. of level approval cycle
            if (APPLICATION_STATUS_NOCUPDATED.equals(application.getStatus().getCode())
                    || !APPLICATION_STATUS_DIGI_SIGNED.equals(application.getStatus().getCode())
                            && !APPLICATION_STATUS_APPROVED.equals(application.getStatus().getCode())
                            && !lettertoParties.isEmpty()
                            && APPLICATION_STATUS_NOCUPDATED
                                    .equals(lettertoParties.get(0).getLetterToParty().getCurrentApplnStatus().getCode())) {
                if (BpaConstants.APPLICATION_TYPE_REGULAR.equals(application.getApplicationType().getName()))
                    workflowContainer.setAmountRule(bpaWorkFlowService.getAmountRuleByServiceType(application));

                workflowContainer.setPendingActions(application.getState().getNextAction());
            } else if (BpaConstants.APPLICATION_TYPE_REGULAR.equals(application.getApplicationType().getName())
                    && APPLICATION_STATUS_APPROVED.equals(application.getStatus().getCode())
                    && !APPLICATION_STATUS_RECORD_APPROVED.equalsIgnoreCase(application.getState().getValue())) {
                workflowContainer.setAmountRule(bpaWorkFlowService.getAmountRuleByServiceType(application));
            }            
        }
        if (application.getState() != null && application.getState().getValue().equalsIgnoreCase(REJECTION_INITIATED)) {
            workflowContainer.setPendingActions(application.getState().getNextAction());
        }
        //Narendra
        prepareWorkflow(model, application, workflowContainer);
        model.addAttribute("pendingActions", workflowContainer.getPendingActions());
        model.addAttribute(AMOUNT_RULE, workflowContainer.getAmountRule());
        model.addAttribute("currentState", application.getCurrentState().getValue());
        model.addAttribute(BPA_APPLICATION, application);
        model.addAttribute("workFlowBoundary", bpaUtils.getBoundaryForWorkflow(application.getSiteDetail().get(0)).getId());
        model.addAttribute("electionBoundary", application.getSiteDetail().get(0).getElectionBoundary() != null
                ? application.getSiteDetail().get(0).getElectionBoundary().getId()
                : null);
        model.addAttribute("electionBoundaryName", application.getSiteDetail().get(0).getElectionBoundary() != null
                ? application.getSiteDetail().get(0).getElectionBoundary().getName()
                : "");
        model.addAttribute("revenueBoundaryName", application.getSiteDetail().get(0).getAdminBoundary() != null
                ? application.getSiteDetail().get(0).getAdminBoundary().getName()
                : "");
        model.addAttribute("bpaPrimaryDept", bpaUtils.getAppconfigValueByKeyNameForDefaultDept());
        model.addAttribute("applicationDocumentList", application.getPermitDocuments());
        model.addAttribute("isFeeCollected", bpaDemandService.checkAnyTaxIsPendingToCollect(application));
        model.addAttribute("admissionFee", applicationBpaService.setAdmissionFeeAmountWithAmenities(
                application.getServiceType().getId(), application.getApplicationAmenity()));
        model.addAttribute("isEDCRIntegrationRequire",
                bpaDcrService.isEdcrIntegrationRequireByService(application.getServiceType().getCode()));
        Map<String, Object> attributes = model.asMap();
        List<String> actions = Collections.emptyList();
        if (!attributes.isEmpty())
            actions = attributes.get("validActionList") == null ? Collections.emptyList()
                    : (List<String>) attributes.get("validActionList");

        if (!application.getApplicationType().getName().equals(BpaConstants.LOWRISK)
                && ((APPLICATION_STATUS_APPROVED.equals(application.getStatus().getCode())
                        || APPLICATION_STATUS_DIGI_SIGNED.equalsIgnoreCase(application.getStatus().getCode()))
                        || (!actions.isEmpty() && (actions.contains(WF_APPROVE_BUTTON) || actions.contains(WF_FORWARD_FOR_PAYMENT_BUTTON)))))
            buildApplicationPermitConditions(application, model);

        List<PermitNocDocument> nocDocStatus = application.getPermitNocDocuments().stream()
                .filter(pdc -> pdc.getNocDocument().getNocStatus() != null).collect(Collectors.toList());

        model.addAttribute("nocStatusUpdated", application.getPermitNocDocuments().size() == nocDocStatus.size());

        prepareActions(model, application);
        model.addAttribute("nocInitiated", false);
        
        boolean isAllNOCApproved = false;
        boolean isAllCENOCApproved = false;
        String nextAction="";
        if (WF_BA_CHECK_NOC_UPDATION.equalsIgnoreCase(application.getState().getNextAction()) || WF_BA_CHIEF_ENG_CHECK_NOC_UPDATION.equalsIgnoreCase(application.getState().getNextAction())) {
        	nextAction=application.getState().getNextAction();
        	int chiefEngineerVeficationNOCs = 0;
        	
        	List<PermitNocApplication> permitNocApplied = permitNocService.findByPermitApplicationNumber(application.getApplicationNumber());
        	for(PermitNocApplication nocApplication:permitNocApplied) {
        		if(nocApplication.getBpaNocApplication().getNocType().matches(BpaConstants.STRCNOCTYPE+"|"+BpaConstants.ELECNOCTYPE+"|"+BpaConstants.PH7NOCTYPE))
        			chiefEngineerVeficationNOCs++;
        	}
        	
        	if (!permitNocApplied.isEmpty()) {
            	int nocApplicationCount = permitNocApplied.size();
            	int nocApprovedCount = 0;
            	int ceVerificationNOCApprovedCount = 0;
            	//Added By Narendra for Noc Re Initiate Changes
    	    	for(PermitNocApplication nocApplication:permitNocApplied) {
    	    		if(APPROVED.equalsIgnoreCase(nocApplication.getBpaNocApplication().getStatus().getCode()) || 
    	    				NOC_SEND_OBSERVATIONS.equalsIgnoreCase(nocApplication.getBpaNocApplication().getStatus().getCode())) {
    	    			nocApprovedCount++;
    	    		}
    	    		if(nocApplication.getBpaNocApplication().getNocType().matches(BpaConstants.STRCNOCTYPE+"|"+BpaConstants.ELECNOCTYPE+"|"+BpaConstants.PH7NOCTYPE) && (APPROVED.equalsIgnoreCase(nocApplication.getBpaNocApplication().getStatus().getCode()))) {
    	    			ceVerificationNOCApprovedCount++;
    	    		}
    	    	}    	    	
    	    	if(nocApprovedCount==nocApplicationCount) {
    	    		isAllNOCApproved=true;    	    		
    	    	}
    	    	if(ceVerificationNOCApprovedCount==chiefEngineerVeficationNOCs) {
    	    		isAllCENOCApproved=true;    	    		
    	    	}
            }else {
            	isAllNOCApproved = true;
            	isAllCENOCApproved=true;  
            }
        }else {
        	isAllNOCApproved = true;
        	isAllCENOCApproved=true;  
        }
        model.addAttribute("isAllNOCApproved", isAllNOCApproved);
        model.addAttribute("isAllCENOCApproved", isAllCENOCApproved);
        model.addAttribute("nextAction", nextAction);
        
        if (!application.getIsOneDayPermitApplication()
                && ("Forwarded to SDO Building for Approval".equalsIgnoreCase(application.getState().getNextAction())
                	|| "Forwarded to check NOC updation".equalsIgnoreCase(application.getState().getNextAction())
                	|| "Permit Fee Collection Pending".equalsIgnoreCase(application.getState().getNextAction())
                	|| "Forwarded to E- Assistant Estate Officer for Approval".equalsIgnoreCase(application.getState().getNextAction())
                    ) && !("Approved".equalsIgnoreCase(application.getStatus().getCode()))) {
            model.addAttribute("createlettertoparty", true);
        }
        
        if (!BpaConstants.APPROVED.equalsIgnoreCase(application.getStatus().getCode())) {
        	ApplicationBpaFeeCalculation feeCalculation = (ApplicationBpaFeeCalculation) specificNoticeService.find(PermitFeeCalculationService.class, specificNoticeService.getCityDetails());
        	model.addAttribute("tempFees", feeCalculation.calculateAllFees(application));
        }
    }

    private void loadCommonApplicationDetails(Model model, BpaApplication application) {
        model.addAttribute("inspectionList", inspectionService.findByBpaApplicationOrderByIdAsc(application));
        List<InConstructionInspection> inConstInspections = new ArrayList<InConstructionInspection>();
        final List<PermitInspectionApplication> permitInspections = inspectionAppService.findByApplicationNumber(application.getApplicationNumber());

        for( PermitInspectionApplication permitInspection : permitInspections) {
        	List<InConstructionInspection> inspApp = inspectionConstService.findByInspectionApplicationOrderByIdAsc(permitInspection.getInspectionApplication());
        	inConstInspections.addAll(inspApp);
        }        
        model.addAttribute("inconstinspectionList",inConstInspections);
        model.addAttribute("lettertopartylist", lettertoPartyService.findByBpaApplicationOrderByIdDesc(application));
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistory(application.getAppointmentSchedule(), application.getCurrentState(),
                        application.getStateHistory()));
        //Added By Narendra For NOC Changes
        model.addAttribute("nocWorkflowHistory",
                workflowHistoryService.getNocWorkflowHistory(application));
        if(null!=application.getDemand()) {
        	buildReceiptDetails(application.getDemand().getEgDemandDetails(), application.getReceipts());
        }
        List<PermitNocApplication> permitNoc = permitNocService.findByPermitApplicationNumber(application.getApplicationNumber());

        Map<String, String> edcrNocMandatory = permitNocService.getEdcrNocMandatory(application.geteDcrNumber());
        Map<String, String> nocAutoMap = new ConcurrentHashMap<>();
        Map<String, String> nocConfigMap = new ConcurrentHashMap<>();
        Map<String, String> nocTypeApplMap = new ConcurrentHashMap<>();

        for (PermitNocDocument nocDocument : application.getPermitNocDocuments()) {
            String code = nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode();
            NocConfiguration nocConfig = nocConfigurationService
                    .findByDepartmentAndType(code, BpaConstants.PERMIT);
            if (permitNocService.findByApplicationNumberAndType(application.getApplicationNumber(), code) != null)
                nocTypeApplMap.put(code, "initiated");
            if(null != edcrNocMandatory && !edcrNocMandatory.isEmpty()) {
	            if (nocConfig != null && nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.PERMIT)
	                    && (nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.INTERNAL.toString()) || 
	                    		nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.THIRD_PARTY.toString()))
	                    && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.MANUAL.toString()) &&
	                    edcrNocMandatory.get(nocConfig.getDepartment()).equalsIgnoreCase("YES") && nocDocument.getNocDocument().getNocSupportDocs().isEmpty())
	                nocConfigMap.put(nocConfig.getDepartment(), "initiate");
	            if (nocConfig != null && nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.PERMIT)
	                    && (nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.INTERNAL.toString()) || 
	            		nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.THIRD_PARTY.toString()))
	                    && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.AUTO.toString()) &&
	                    edcrNocMandatory.get(nocConfig.getDepartment()).equalsIgnoreCase("YES") && nocDocument.getNocDocument().getNocSupportDocs().isEmpty())
	                nocAutoMap.put(nocConfig.getDepartment(), "autoinitiate");
            }
            for (PermitNocApplication pna : permitNoc) {
                if (nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode()
                        .equalsIgnoreCase(pna.getBpaNocApplication().getNocType())) {
                    nocDocument.setPermitNoc(pna);
                }
            }
        }
        
        model.addAttribute("nocTypeApplMap", nocTypeApplMap);
        model.addAttribute("nocConfigMap", nocConfigMap);
        model.addAttribute("nocAutoMap", nocAutoMap);
        model.addAttribute("isPermitApplFeeReq", "NO");
        model.addAttribute("permitApplFeeCollected", "NO");
        if (bpaUtils.isApplicationFeeCollectionRequired()) {
            model.addAttribute("isPermitApplFeeReq", "YES");
        }
        if (application.getDemand() != null
                && application.getDemand().getAmtCollected().compareTo(application.getAdmissionfeeAmount()) >= 0) {
            model.addAttribute("permitApplFeeCollected", "YES");
        }
    }

    private void buildRejectionReasons(Model model, BpaApplication application) {
        if (application.getIsOneDayPermitApplication()
                && APPLICATION_STATUS_DOC_VERIFY_COMPLETED.equalsIgnoreCase(application.getStatus().getCode())
                || APPLICATION_STATUS_NOCUPDATED.equals(application.getStatus().getCode())
                || APPLICATION_STATUS_REJECTED.equalsIgnoreCase(application.getStatus().getCode())
                || APPLICATION_STATUS_SCHEDULED.equalsIgnoreCase(application.getStatus().getCode())
                || APPLICATION_STATUS_RESCHEDULED.equalsIgnoreCase(application.getStatus().getCode())
                || (APPLICATION_STATUS_REGISTERED.equalsIgnoreCase(application.getStatus().getCode())
                        && FORWARDED_TO_CLERK.equalsIgnoreCase(application.getCurrentState().getNextAction()))) {
            model.addAttribute("showRejectionReasons", true);

            List<ChecklistServiceTypeMapping> additionalRejectionReasonList = checklistServiceTypeService
                    .findByActiveChecklistAndServiceType(application.getServiceType().getDescription(),
                            "ADDITIONALREJECTIONREASONS");

            List<ChecklistServiceTypeMapping> rejectionReasonList = checklistServiceTypeService
                    .findByActiveChecklistAndServiceType(
                            application.getServiceType().getDescription(), "PERMITREJECTIONREASONS");

            List<ApplicationPermitConditions> rejectionApplnPermitConditions = new ArrayList<>();
            List<ApplicationPermitConditions> rejectionReasons = bpaApplicationPermitConditionsService
                    .findAllByApplicationAndPermitConditionType(application, ConditionType.REJECTIONREASONS);
            if (rejectionReasons == null || rejectionReasons.isEmpty()) {
                for (ChecklistServiceTypeMapping checklistServicetype : rejectionReasonList) {
                    ApplicationPermitConditions condition = new ApplicationPermitConditions();
                    NoticeCondition noticeCondtion = new NoticeCondition();
                    noticeCondtion.setChecklistServicetype(checklistServicetype);
                    condition.setNoticeCondition(noticeCondtion);
                    condition.setApplication(application);
                    rejectionApplnPermitConditions.add(condition);
                }
                application.setRejectionReasonsTemp(rejectionApplnPermitConditions);
            } else {
                for (ApplicationPermitConditions apc : application.getRejectionReasons()) {
                    if (apc.getNoticeCondition().getType().name().equals("REJECTIONREASONS"))
                        rejectionApplnPermitConditions.add(apc);
                }
                application.setRejectionReasonsTemp(rejectionApplnPermitConditions);
            }

            List<ApplicationPermitConditions> additionalRejectionApplnPermitConditions = new ArrayList<>();
            List<ApplicationPermitConditions> additionalRejectionReasons = bpaApplicationPermitConditionsService
                    .findAllByApplicationAndPermitConditionType(application, ConditionType.ADDITIONALREJECTIONREASONS);
            if (additionalRejectionReasons == null || additionalRejectionReasons.isEmpty()) {
                for (ChecklistServiceTypeMapping checklistServicetype : additionalRejectionReasonList) {
                    ApplicationPermitConditions condition = new ApplicationPermitConditions();
                    NoticeCondition noticeCondtion = new NoticeCondition();
                    noticeCondtion.setChecklistServicetype(checklistServicetype);
                    condition.setNoticeCondition(noticeCondtion);
                    condition.setApplication(application);
                    additionalRejectionApplnPermitConditions.add(condition);
                }
                application.setAdditionalRejectReasonsTemp(additionalRejectionApplnPermitConditions);
            } else {
                for (ApplicationPermitConditions apc : application.getAdditionalPermitConditions()) {
                    if (apc.getNoticeCondition().getType().name().equals("ADDITIONALREJECTIONREASONS"))
                        additionalRejectionApplnPermitConditions.add(apc);
                }
                application.setAdditionalRejectReasonsTemp(additionalRejectionApplnPermitConditions);
            }
        }
        
        if (WF_BA_CHECK_NOC_UPDATION.equals(application.getCurrentState().getNextAction())
                || WF_PERMIT_FEE_COLL_PENDING.equalsIgnoreCase(application.getCurrentState().getNextAction())
                	|| WF_BA_AE_APPROVAL.equalsIgnoreCase(application.getCurrentState().getNextAction())
                		|| WF_BA_SDO_APPROVAL.equalsIgnoreCase(application.getCurrentState().getNextAction())
                			|| (APPLICATION_TYPE_MEDIUMRISK.equalsIgnoreCase(application.getApplicationType().getName()) && WF_BA_VARIFICATION_INITIATED.equalsIgnoreCase(application.getCurrentState().getNextAction()))) {
        	model.addAttribute("showRejectionReasons", true);

            List<ChecklistServiceTypeMapping> additionalRejectionReasonList = checklistServiceTypeService
                    .findByActiveChecklistAndServiceType(application.getServiceType().getDescription(),
                            "ADDITIONALREJECTIONREASONS");

            List<ChecklistServiceTypeMapping> rejectionReasonList = checklistServiceTypeService
                    .findByActiveChecklistAndServiceType(
                            application.getServiceType().getDescription(), "PERMITREJECTIONREASONS");

            List<ApplicationPermitConditions> rejectionApplnPermitConditions = new ArrayList<>();
            List<ApplicationPermitConditions> rejectionReasons = bpaApplicationPermitConditionsService
                    .findAllByApplicationAndPermitConditionType(application, ConditionType.REJECTIONREASONS);
            if (rejectionReasons == null || rejectionReasons.isEmpty()) {
                for (ChecklistServiceTypeMapping checklistServicetype : rejectionReasonList) {
                    ApplicationPermitConditions condition = new ApplicationPermitConditions();
                    NoticeCondition noticeCondtion = new NoticeCondition();
                    noticeCondtion.setChecklistServicetype(checklistServicetype);
                    condition.setNoticeCondition(noticeCondtion);
                    condition.setApplication(application);
                    rejectionApplnPermitConditions.add(condition);
                }
                application.setRejectionReasonsTemp(rejectionApplnPermitConditions);
            } else {
                for (ApplicationPermitConditions apc : application.getRejectionReasons()) {
                    if (apc.getNoticeCondition().getType().name().equals("REJECTIONREASONS"))
                        rejectionApplnPermitConditions.add(apc);
                }
                application.setRejectionReasonsTemp(rejectionApplnPermitConditions);
            }

            List<ApplicationPermitConditions> additionalRejectionApplnPermitConditions = new ArrayList<>();
            List<ApplicationPermitConditions> additionalRejectionReasons = bpaApplicationPermitConditionsService
                    .findAllByApplicationAndPermitConditionType(application, ConditionType.ADDITIONALREJECTIONREASONS);
            if (additionalRejectionReasons == null || additionalRejectionReasons.isEmpty()) {
                for (ChecklistServiceTypeMapping checklistServicetype : additionalRejectionReasonList) {
                    ApplicationPermitConditions condition = new ApplicationPermitConditions();
                    NoticeCondition noticeCondtion = new NoticeCondition();
                    noticeCondtion.setChecklistServicetype(checklistServicetype);
                    condition.setNoticeCondition(noticeCondtion);
                    condition.setApplication(application);
                    additionalRejectionApplnPermitConditions.add(condition);
                }
                application.setAdditionalRejectReasonsTemp(additionalRejectionApplnPermitConditions);
            } else {
                for (ApplicationPermitConditions apc : application.getAdditionalPermitConditions()) {
                    if (apc.getNoticeCondition().getType().name().equals("ADDITIONALREJECTIONREASONS"))
                        additionalRejectionApplnPermitConditions.add(apc);
                }
                application.setAdditionalRejectReasonsTemp(additionalRejectionApplnPermitConditions);
            }
        }
    }

    private void buildApplicationPermitConditions(final BpaApplication application, final Model model) {
        model.addAttribute("showpermitconditions", true);

        model.addAttribute("permitConditions", checklistServiceTypeService
                .findByActiveChecklistAndServiceType(application.getServiceType().getDescription(), "PERMITGENERALCONDITIONS"));

        model.addAttribute("modifiablePermitConditions", checklistServiceTypeService
                .findByActiveChecklistAndServiceType(application.getServiceType().getDescription(), "PERMITNOCCONDITIONS"));

        model.addAttribute("additionalPermitCondition", checklistServiceTypeService
                .findByActiveChecklistAndServiceType(application.getServiceType().getDescription(),
                        "PERMITADDITIONALCONDITIONS"));

        application.setDynamicPermitConditionsTemp(bpaApplicationPermitConditionsService
                .findAllByApplicationAndPermitConditionType(application, ConditionType.NOCCONDITIONS));
        application.setStaticPermitConditionsTemp(bpaApplicationPermitConditionsService
                .findAllByApplicationAndPermitConditionType(application, ConditionType.GENERALCONDITIONS));
        application.setAdditionalPermitConditionsTemp(bpaApplicationPermitConditionsService
                .findAllByApplicationAndPermitConditionType(application, ConditionType.ADDITIONALCONDITIONS));
    }

    private Boolean checkIsRescheduledOnScrutiny(final Model model, final BpaApplication application) {
        Optional<SlotApplication> actvSltApp = application.getSlotApplications().stream()
                .reduce((slotApp1, slotApp2) -> slotApp2);
        if (actvSltApp.isPresent()
                && actvSltApp.get().getSlotDetail().getSlot().getAppointmentDate().after(new Date())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.validate.doc.scrutiny", new String[] {
                    application.getApplicationNumber(), DateUtils.getDefaultFormattedDate(
                            actvSltApp.get().getSlotDetail().getSlot().getAppointmentDate()) },
                    LocaleContextHolder.getLocale()));
            return true;
        } else {
            return false;
        }
    }

    private String getMessageOnRejectionInitiation(String approvalComent, BpaApplication bpaAppln, User userObj,
            String msgInitiateRjctn, Position ownerPosition) {
        return messageSource.getMessage(msgInitiateRjctn, new String[] {
                userObj == null ? ""
                        : userObj.getUsername().concat("~")
                                .concat(getDesinationNameByPosition(ownerPosition)),
                bpaAppln.getApplicationNumber(), approvalComent }, LocaleContextHolder.getLocale());
    }
}