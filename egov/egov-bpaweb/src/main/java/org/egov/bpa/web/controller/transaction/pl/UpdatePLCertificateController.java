package org.egov.bpa.web.controller.transaction.pl;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_HISTORY;
import static org.egov.bpa.utils.BpaConstants.GENERATEREJECTNOTICE;
import static org.egov.bpa.utils.BpaConstants.WF_APPROVE_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_DOC_SCRUTINY_SCHEDLE_PEND;
import static org.egov.bpa.utils.BpaConstants.WF_DOC_VERIFY_PEND;
import static org.egov.bpa.utils.BpaConstants.WF_INIT_AUTO_RESCHDLE;
import static org.egov.bpa.utils.BpaConstants.WF_REJECT_BUTTON;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.notice.PlinthLevelCertificateNoticesFormat;
import org.egov.bpa.transaction.notice.impl.PlinthLevelCertificateFormatImpl;
import org.egov.bpa.transaction.service.OwnershipTransferService;
import org.egov.bpa.transaction.service.pl.PlinthLevelCertificateService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.infra.reporting.engine.ReportOutput;
import org.egov.eis.service.PositionMasterService;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.custom.CustomImplProvider;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/application/plinth-level-certificate")
public class UpdatePLCertificateController extends BpaGenericApplicationController {
	
	public static final String PL_CERTIFICATE_VIEW = "pl-certificate-view";
    public static final String PL_CREATE_DOCUMENT_SCRUTINY_FORM = "pl-create-document-scrutiny-form";
    public static final String PL_CERTIFICATE_RESULT = "pl-certificate-result";
    public static final String BPA_APPLICATION = "bpaApplication";
    public static final String PL_CERTIFICATE = "plinthLevelCertificate";
    public static final String REDIRECT_APPLICATION_PL_SUCCESS = "redirect:/application/plinth-level-certificate/success/";
    private static final String AMOUNT_RULE = "amountRule";
    private static final String WORK_FLOW_ACTION = "workFlowAction";
    private static final String MSG_UPDATE_FORWARD_REGISTRATION = "msg.update.forward.registration";
    private static final String MSG_REJECT_FORWARD_REGISTRATION = "msg.reject.forward.registration";
    private static final String MSG_INITIATE_REJECTION = "msg.initiate.reject";
    private static final String MESSAGE = "message";
    private static final String COMMON_ERROR = "common-error";
    private static final String ADDITIONALRULE = "additionalRule";
    private static final String APPRIVALPOSITION = "approvalPosition";
    private static final String PDFEXTN = ".pdf";
    private static final String MSG_APPROVE_FORWARD_REGISTRATION = "msg.approve.success";
    public static final String GENERATE_PL_CERTIFICATE = "Generate Plinth Level Certificate";
    
    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    private PlinthLevelCertificateService plinthLevelCertificateService;  
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    private OwnershipTransferService ownershipTransferService;
    
    @GetMapping("/update/{applicationNumber}")
    public String editPLCertificateApplication(@PathVariable final String applicationNumber, final Model model,
            final HttpServletRequest request) {
        PlinthLevelCertificate pl = plinthLevelCertificateService.findByApplicationNumber(applicationNumber);        
        setCityName(model, request);
        prepareFormData(pl, model);
        loadData(pl, model);
        bpaUtils.loadBoundary(pl.getParent());
        getActionsForPLApplication(model, pl);
        
        if (pl.getState() != null
                && pl.getState().getNextAction().equalsIgnoreCase(WF_DOC_SCRUTINY_SCHEDLE_PEND)
                || pl.getState().getNextAction().equalsIgnoreCase(WF_DOC_VERIFY_PEND)
                || pl.getState().getNextAction().equalsIgnoreCase(WF_INIT_AUTO_RESCHDLE))
            return "pl-document-scrutiny-form";

        return PL_CERTIFICATE_VIEW;
    }
    
    private void setCityName(final Model model, final HttpServletRequest request) {
        if (request.getSession().getAttribute("cityname") != null)
            model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
    }
    
    private void prepareFormData(final PlinthLevelCertificate pl, final Model model) {
        model.addAttribute("stateType", pl.getClass().getSimpleName());
        model.addAttribute(ADDITIONALRULE, pl.getPlinthLevelCertificateType());
        model.addAttribute("currentState", pl.getCurrentState() == null ? "" : pl.getCurrentState().getValue());
    }
    
    private void loadData(PlinthLevelCertificate pl, Model model) {        
        model.addAttribute("citizenOrBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());

        final WorkflowContainer workflowContainer = new WorkflowContainer();		
        workflowContainer.setAmountRule(null);
        model.addAttribute(ADDITIONALRULE, pl.getPlinthLevelCertificateType());
        workflowContainer.setAdditionalRule(pl.getPlinthLevelCertificateType());
        workflowContainer.setPendingActions(pl.getState().getNextAction());

        prepareWorkflow(model, pl, workflowContainer);
        model.addAttribute("pendingActions", workflowContainer.getPendingActions());
        model.addAttribute("currentState", pl.getCurrentState().getValue());
        model.addAttribute(AMOUNT_RULE, workflowContainer.getAmountRule());
        model.addAttribute("workFlowBoundary", bpaUtils.getBoundaryForWorkflow(pl.getParent().getSiteDetail().get(0)).getId());
        model.addAttribute("electionBoundary", pl.getParent().getSiteDetail().get(0).getElectionBoundary() != null
                ? pl.getParent().getSiteDetail().get(0).getElectionBoundary().getId()
                : null);
        model.addAttribute("electionBoundaryName", pl.getParent().getSiteDetail().get(0).getElectionBoundary() != null
                ? pl.getParent().getSiteDetail().get(0).getElectionBoundary().getName()
                : "");
        model.addAttribute("revenueBoundaryName", pl.getParent().getSiteDetail().get(0).getAdminBoundary() != null
                ? pl.getParent().getSiteDetail().get(0).getAdminBoundary().getName()
                : "");
        model.addAttribute("bpaPrimaryDept", bpaUtils.getAppconfigValueByKeyNameForDefaultDept());
        model.addAttribute("loginUser", securityUtils.getCurrentUser());
        model.addAttribute(APPLICATION_HISTORY, workflowHistoryService.getHistoryForPL(pl.getCurrentState(), pl.getStateHistory()));
        model.addAttribute(BPA_APPLICATION, pl.getParent());
        model.addAttribute(PL_CERTIFICATE, pl);
    }
    
    private void getActionsForPLApplication(Model model, PlinthLevelCertificate pl) {
        String mode = StringUtils.EMPTY;
        if (mode == null)
            mode = "edit";
        
        model.addAttribute("mode", mode);
    }
    
    @PostMapping("/update-submit")
    public String updatePLCertificateApplication(@Valid @ModelAttribute final PlinthLevelCertificate plinthLevelCertificate,
            final HttpServletRequest request, final Model model,
            final BindingResult errors, final RedirectAttributes redirectAttributes,
            @RequestParam final BigDecimal amountRule) {
        if (errors.hasErrors())
            return PL_CERTIFICATE_VIEW;

        Position ownerPosition = plinthLevelCertificate.getCurrentState().getOwnerPosition();
        if (validateLoginUserAndOwnerIsSame(model, securityUtils.getCurrentUser(), ownerPosition))
            return COMMON_ERROR;

        WorkflowBean wfBean = new WorkflowBean();
        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        wfBean.setAmountRule(null);
        Position pos = null;
        Long approvalPosition = null;
        
        if (StringUtils.isNotBlank(request.getParameter(APPRIVALPOSITION))
                && !WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())
                && !GENERATEREJECTNOTICE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            approvalPosition = Long.valueOf(request.getParameter(APPRIVALPOSITION));
        } else if (WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            pos = bpaWorkFlowService.getApproverPositionOfElectionWardByCurrentStateForPL(plinthLevelCertificate, BpaConstants.REJECTION_INITIATED);
            approvalPosition = pos.getId();
        }

        wfBean.setApproverPositionId(approvalPosition);
        wfBean.setApproverComments(plinthLevelCertificate.getApprovalComent());
        if (plinthLevelCertificate.getState().getValue() != null)
            wfBean.setCurrentState(plinthLevelCertificate.getState().getValue());
        PlinthLevelCertificate plResponse = plinthLevelCertificateService.update(plinthLevelCertificate, wfBean);
        bpaUtils.updatePortalUserinbox(plResponse, null);
        if (null != approvalPosition) {
            pos = positionMasterService.getPositionById(approvalPosition);
        }
        if (null == approvalPosition) {
            pos = positionMasterService.getPositionById(plResponse.getCurrentState().getOwnerPosition().getId());
        }
        User user = workflowHistoryService
                .getUserPositionByPassingPosition(approvalPosition == null ? pos.getId() : approvalPosition);
        String message;
        if (WF_REJECT_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            message = getMessageOnRejectionInitiation(wfBean.getApproverComments(), plinthLevelCertificate, user,
                    MSG_REJECT_FORWARD_REGISTRATION, pos);
        } else if (WF_APPROVE_BUTTON.equalsIgnoreCase(wfBean.getWorkFlowAction()))
            message = messageSource.getMessage(MSG_APPROVE_FORWARD_REGISTRATION, new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                                    plResponse.getApplicationNumber() }, LocaleContextHolder.getLocale());
        else {
            message = messageSource.getMessage(MSG_UPDATE_FORWARD_REGISTRATION, new String[] {
                    user == null ? ""
                            : user.getUsername().concat("~")
                                    .concat(getDesinationNameByPosition(pos)),
                                    plResponse.getApplicationNumber() }, LocaleContextHolder.getLocale());
        }

        redirectAttributes.addFlashAttribute(MESSAGE, message);
        if (StringUtils.isNotBlank(wfBean.getWorkFlowAction())
                && GENERATE_PL_CERTIFICATE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
            PlinthLevelCertificateNoticesFormat plNoticeFeature = (PlinthLevelCertificateNoticesFormat) specificNoticeService
                    .find(PlinthLevelCertificateFormatImpl.class, specificNoticeService.getCityDetails());
            ReportOutput reportOutput = plNoticeFeature.generateNotice(plinthLevelCertificateService.findByApplicationNumber(plinthLevelCertificate.getApplicationNumber()));

            return "redirect:/application/plinth-level-certificate/generate-plinth-level-certificate/" + plinthLevelCertificate.getApplicationNumber();
        } 
//        else if (StringUtils.isNotBlank(wfBean.getWorkFlowAction()) && GENERATEREJECTNOTICE.equalsIgnoreCase(wfBean.getWorkFlowAction())) {
//            OccupancyCertificateNoticesFormat ocNoticeFeature = (OccupancyCertificateNoticesFormat) specificNoticeService
//                    .find(OccupancyRejectionFormatImpl.class, specificNoticeService.getCityDetails());
//            ReportOutput reportOutput = ocNoticeFeature
//                    .generateNotice(
//                            occupancyCertificateService.findByApplicationNumber(occupancyCertificate.getApplicationNumber()));
//            ocSmsAndEmailService.sendSMSAndEmail(occupancyCertificate, reportOutput, OCREJECTIONFILENAME + PDFEXTN);
//            return "redirect:/application/occupancy-certificate/rejectionnotice/" + occupancyCertificate.getApplicationNumber();
//        }

        return REDIRECT_APPLICATION_PL_SUCCESS + plResponse.getApplicationNumber();
    }

    @GetMapping("/success/{applicationNumber}")
    public String success(@PathVariable final String applicationNumber, final Model model, final HttpServletRequest request) {
        PlinthLevelCertificate pl = plinthLevelCertificateService.findByApplicationNumber(applicationNumber);
        model.addAttribute(APPLICATION_HISTORY,
                workflowHistoryService.getHistoryForPL(pl.getCurrentState(), pl.getStateHistory()));
        model.addAttribute(BPA_APPLICATION, pl.getParent());
        model.addAttribute(PL_CERTIFICATE, pl);
        return PL_CERTIFICATE_RESULT;
    }
    
    private String getMessageOnRejectionInitiation(String approvalComent, PlinthLevelCertificate pl, User userObj,
            String msgInitiateRjctn, Position ownerPosition) {
        return messageSource.getMessage(msgInitiateRjctn, new String[] {
                userObj == null ? ""
                        : userObj.getUsername().concat("~")
                                .concat(getDesinationNameByPosition(ownerPosition)),
                pl.getApplicationNumber(), approvalComent }, LocaleContextHolder.getLocale());
    }
}
