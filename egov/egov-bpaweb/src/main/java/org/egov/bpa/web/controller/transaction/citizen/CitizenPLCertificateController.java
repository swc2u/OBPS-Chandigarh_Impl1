package org.egov.bpa.web.controller.transaction.citizen;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.pl.PLAppointmentSchedule;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.service.pl.PLAppointmentScheduleService;
import org.egov.bpa.transaction.service.pl.PlInspectionService;
import org.egov.bpa.transaction.service.pl.PlinthLevelCertificateService;
import org.egov.bpa.utils.BpaUtils;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.commons.entity.Source;
import org.egov.eis.entity.Assignment;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_HISTORY;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_CREATED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SITE_INSPECTED;
import static org.egov.bpa.utils.BpaConstants.WF_CANCELAPPLICATION_BUTTON;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SCHEDULED_FOR_SITE_INSP;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_RESCHEDULED_FOR_SITE_INSP;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/application/citizen")
public class CitizenPLCertificateController extends BpaGenericApplicationController {
	private static final String WORK_FLOW_ACTION = "workFlowAction";
    private static final String TRUE = "TRUE";
    private static final String CITIZEN_OR_BUSINESS_USER = "citizenOrBusinessUser";
    private static final String IS_CITIZEN = "isCitizen";
    private static final String MESSAGE = "message";
    public static final String CITIZEN_PL_APPLY_NEW = "citizen-pl-apply-new";
    public static final String CITIZEN_PL_NEW_FORM = "citizen-pl-certificate-new";
    private static final String OFFICIAL_NOT_EXISTS = "No officials assigned to process this application.";
    private static final String MSG_PORTAL_FORWARD_REGISTRATION = "msg.portal.forward.registration";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String CITIZEN_pl_VIEW = "citizen-pl-certificate-view";
    private static final String CITIZEN_pl_UPDATE = "citizen-pl-certificate-update";
    private static final String BPAAPPLICATION_CITIZEN = "citizen_suceess";
    private static final String APPOINTMENT_LOCATIONS_LIST = "appointmentLocationsList";
    private static final String APPOINTMENT_SCHEDULED_LIST = "appointmentScheduledList";
    
    @Autowired
    private PlinthLevelCertificateService plinthLevelCertificateService;    
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private PLAppointmentScheduleService plAppointmentScheduleService;
    @Autowired
    private CustomImplProvider specificNoticeService;
    
    @GetMapping("/pl-certificate/apply")
    public String applyPLform(final Model model, final HttpServletRequest request) { 
    	PlinthLevelCertificate plinthLevelCertificate = new PlinthLevelCertificate();
        plinthLevelCertificate.setApplicationDate(new Date());
        model.addAttribute("plinthLevelCertificate", plinthLevelCertificate);
        return CITIZEN_PL_APPLY_NEW;
    }
    
    @PostMapping("/pl-certificate/new")
    public String newPLApplicationForm(@Valid @ModelAttribute final PlinthLevelCertificate plinthLevelCertificate, final HttpServletRequest request, final Model model,
            final BindingResult errors, final RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return CITIZEN_PL_APPLY_NEW;
        }
        plinthLevelCertificate.setApplicationDate(new Date());        
        BpaApplication bpaApplication = plinthLevelCertificateService.findByBpaApplicationNumber(plinthLevelCertificate.getPlanApplicationNumber());
        bpaUtils.loadBoundary(bpaApplication);
        if(null!=bpaApplication) {
	        plinthLevelCertificate.setParent(bpaApplication);
	        plinthLevelCertificate.setPlinthLevelCertificateType(bpaApplication.getApplicationType().getName());
	        loadFormData(model, request, plinthLevelCertificate);
	        return CITIZEN_PL_NEW_FORM;
        }else {
        	String message = messageSource.getMessage("msg.bpa.application.not.found", new String[] {plinthLevelCertificate.getPlanApplicationNumber()}, LocaleContextHolder.getLocale());
        	redirectAttributes.addFlashAttribute(MESSAGE, message);
        	return CITIZEN_PL_APPLY_NEW;
        }
    }
    
    private void loadFormData(final Model model, final HttpServletRequest request, PlinthLevelCertificate plinthLevelCertificate) {
    	plinthLevelCertificate.setSource(Source.CITIZENPORTAL);
    	plinthLevelCertificate.setApplicationType("Plinth Level Certificate");
        model.addAttribute("mode", "new");
        setCityName(model, request);
        preparePLCommonModelAttribute(model);
        model.addAttribute("plinthLevelCertificate", plinthLevelCertificate);       
    }
    
    private void preparePLCommonModelAttribute(final Model model) {
        Boolean citizenUser = bpaUtils.logedInuserIsCitizen();
        model.addAttribute("isCitizen", citizenUser);
        model.addAttribute("citizenOrBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());
    }

    private void setCityName(final Model model, final HttpServletRequest request) {
        if (request.getSession().getAttribute("cityname") != null)
            model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
    }
    
    @PostMapping("/pl-certificate/create")
    public String submitPLApplication(@Valid @ModelAttribute final PlinthLevelCertificate plinthLevelCertificate, final HttpServletRequest request, final Model model,
            final BindingResult errors, final RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return CITIZEN_PL_NEW_FORM;
        }
        
        WorkflowBean wfBean = new WorkflowBean();
        Long userPosition = null;
        String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
        Boolean isCitizen = request.getParameter(IS_CITIZEN) != null && request.getParameter(IS_CITIZEN).equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        Boolean citizenOrBusinessUser = request.getParameter(CITIZEN_OR_BUSINESS_USER) != null && request.getParameter(CITIZEN_OR_BUSINESS_USER).equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        
        final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(plinthLevelCertificate.getStateType(), WF_NEW_STATE, plinthLevelCertificate.getPlinthLevelCertificateType());
        if (wfMatrix != null)
            userPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    bpaUtils.getBoundaryForWorkflow(plinthLevelCertificate.getParent().getSiteDetail().get(0)).getId());
        if (citizenOrBusinessUser && workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && (userPosition == 0 || userPosition == null)) {
            model.addAttribute("noJAORSAMessage", OFFICIAL_NOT_EXISTS);
            loadFormData(model, request, plinthLevelCertificate);
            return CITIZEN_PL_NEW_FORM;
        }
        
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        PlinthLevelCertificate plResponse = plinthLevelCertificateService.saveOrUpdate(plinthLevelCertificate, wfBean);
        
        if (citizenOrBusinessUser) {
            if (isCitizen)
                bpaUtils.createPortalUserinbox(plResponse, Arrays.asList(plResponse.getParent().getOwner().getUser(),
                        plResponse.getParent().getStakeHolder().get(0).getStakeHolder()), workFlowAction);
            else
                bpaUtils.createPortalUserinbox(plResponse,
                        Arrays.asList(plResponse.getParent().getOwner().getUser(), securityUtils.getCurrentUser()),
                        workFlowAction);
        }
        String message;
        // When fee collection not require then directly will forward to official
        if (workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)) {
            wfBean.setCurrentState(WF_NEW_STATE);
            bpaUtils.redirectToBpaWorkFlowForPL(plinthLevelCertificate, wfBean);
            
            List<Assignment> assignments;
            if (null == userPosition)
                assignments = bpaWorkFlowService
                        .getAssignmentsByPositionAndDate(plinthLevelCertificate.getCurrentState().getOwnerPosition().getId(),
                                new Date());
            else
                assignments = bpaWorkFlowService.getAssignmentsByPositionAndDate(userPosition, new Date());
            Position pos = assignments.get(0).getPosition();
            User wfUser = assignments.get(0).getEmployee();
            message = messageSource.getMessage(MSG_PORTAL_FORWARD_REGISTRATION, new String[] {
                    wfUser == null ? ""
                            : wfUser.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                                    plinthLevelCertificate.getApplicationNumber() }, LocaleContextHolder.getLocale());

            redirectAttributes.addFlashAttribute(MESSAGE, message);
        } else {
            message = "Successfully saved with ApplicationNumber " + plResponse.getApplicationNumber() + ".";
        }
        redirectAttributes.addFlashAttribute(MESSAGE, message);
        return "redirect:/application/citizen/success/" + plinthLevelCertificate.getApplicationNumber();
    }
    
    @GetMapping("/plinth-level-certificate/update/{applicationNumber}")
    public String updateApplicationForm(final Model model, @PathVariable final String applicationNumber,
            final HttpServletRequest request) {
    	PlinthLevelCertificate pl = plinthLevelCertificateService.findByApplicationNumber(applicationNumber);        
        setCityName(model, request);
        prepareFormData(pl, model);
        preparePLCommonModelAttribute(model);
        loadData(pl, model);
        model.addAttribute("plinthLevelCertificate", pl);
        bpaUtils.loadBoundary(pl.getParent());
        
        if (APPLICATION_STATUS_CREATED.equals(pl.getStatus().getCode()))
            return CITIZEN_pl_UPDATE;
        else {
            model.addAttribute("bpaApplication", pl.getParent());
            return CITIZEN_pl_VIEW;
        }
    }
    
    @PostMapping("/plinth-level-certificate/update-submit")
    public String updatePLDetails(@Valid @ModelAttribute("plinthLevelCertificate") final PlinthLevelCertificate plinthLevelCertificate,
            final HttpServletRequest request, final Model model,
            final BindingResult errors) {
        if (errors.hasErrors())
            return CITIZEN_pl_UPDATE;
        WorkflowBean wfBean = new WorkflowBean();
        Long userPosition = null;
        String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
        Boolean citizenOrBusinessUser = request.getParameter(CITIZEN_OR_BUSINESS_USER) != null
                && request.getParameter(CITIZEN_OR_BUSINESS_USER)
                        .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(plinthLevelCertificate.getStateType(), 
        		WF_NEW_STATE, plinthLevelCertificate.getPlinthLevelCertificateType());
        if (wfMatrix != null)
			userPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
					bpaUtils.getBoundaryForWorkflow(plinthLevelCertificate.getParent().getSiteDetail().get(0)).getId());
        if (citizenOrBusinessUser && workFlowAction != null
                && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && (userPosition == 0 || userPosition == null)) {
            model.addAttribute("noJAORSAMessage", OFFICIAL_NOT_EXISTS);
            return CITIZEN_pl_UPDATE;
        }

        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        PlinthLevelCertificate plResponse = plinthLevelCertificateService.saveOrUpdate(plinthLevelCertificate, wfBean);
        bpaUtils.updatePortalUserinbox(plResponse, null);
        if (workFlowAction != null && workFlowAction.equals(WF_CANCELAPPLICATION_BUTTON)) {
            model.addAttribute(MESSAGE,
                    "Plinth Level Certificate  Application is cancelled by applicant itself successfully with application number "
                            + plResponse.getApplicationNumber());
        } else {
            model.addAttribute(MESSAGE,
                    "Plinth Level Certificate Application is successfully saved with ApplicationNumber "
                            + plResponse.getApplicationNumber());
        }
        return BPAAPPLICATION_CITIZEN;
    }
    
    private void prepareFormData(final PlinthLevelCertificate pl, final Model model) {
        model.addAttribute("stateType", pl.getClass().getSimpleName());
        model.addAttribute(ADDITIONALRULE, pl.getPlinthLevelCertificateType());
        model.addAttribute("currentState", pl.getCurrentState() == null ? "" : pl.getCurrentState().getValue());
    }
    
    private void loadData(PlinthLevelCertificate pl, Model model) {
    	if (APPLICATION_STATUS_SCHEDULED_FOR_SITE_INSP.equals(pl.getStatus().getCode())
                || APPLICATION_STATUS_RESCHEDULED_FOR_SITE_INSP.equals(pl.getStatus().getCode())
                        && !pl.getIsRescheduledByCitizen()) {
            model.addAttribute("mode", "showRescheduleToCitizen");
        }
    	final PlInspectionService plInspectionService = (PlInspectionService) specificNoticeService
                .find(PlInspectionService.class, specificNoticeService.getCityDetails());
        model.addAttribute("inspectionList", plInspectionService.findByPlOrderByIdAsc(pl));
        buildAppointmentDetailsOfScrutinyAndInspection(model, pl);
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistoryForPL(pl.getAppointmentSchedules(), pl.getCurrentState(), pl.getStateHistory()));
        model.addAttribute("plinthLevelCertificate", pl);
    }
    
    private void buildAppointmentDetailsOfScrutinyAndInspection(Model model, PlinthLevelCertificate pl) {
    	List<PLAppointmentSchedule> appointmentScheduledList = plAppointmentScheduleService.findByApplication(pl, AppointmentSchedulePurpose.INSPECTION);
    	model.addAttribute(APPOINTMENT_SCHEDULED_LIST, appointmentScheduledList);
    }
}
