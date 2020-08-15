package org.egov.bpa.web.controller.transaction.pl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import static org.egov.bpa.utils.PLConstants.PLINTH_LEVEL_CERTIFICATE;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.InspectionFilesCommon;
import org.egov.bpa.transaction.entity.pl.PLInspection;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.service.pl.PlInspectionService;
import org.egov.bpa.transaction.service.pl.PlinthLevelCertificateService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.infra.custom.CustomImplProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/application/plinth-level-certificate")
public class CreateInspectionForPLCertificateController  extends BpaGenericApplicationController {
	private static final String CREATE_INSPECTION = "pl-create-inspection";
	
	@Autowired
	private PlinthLevelCertificateService plinthLevelCertificateService;
	@Autowired
    private CustomImplProvider specificNoticeService;
	
	@GetMapping("/create-inspection/{applicationNumber}")
	public String inspectionDetailForm(final Model model, @PathVariable final String applicationNumber) {
		loadApplication(model, applicationNumber);
		return CREATE_INSPECTION;
	}
	
	private void loadApplication(final Model model, final String applicationNumber) {
		final PlinthLevelCertificate pl = plinthLevelCertificateService.findByApplicationNumber(applicationNumber);
		if (pl != null && pl.getState() != null
			&& pl.getState().getValue().equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_REGISTERED)) {
			prepareWorkflowDataForInspection(model, pl);
			model.addAttribute("loginUser", securityUtils.getCurrentUser());
			model.addAttribute(BpaConstants.APPLICATION_HISTORY,
					workflowHistoryService.getHistoryForPL(pl.getAppointmentSchedules(), pl.getCurrentState(), pl.getStateHistory()));
		}
		final PLInspection plInspection = new PLInspection();
		InspectionCommon inspectionCommon = new InspectionCommon();
		inspectionCommon.setInspectionDate(new Date());
		plInspection.setInspection(inspectionCommon);
		plInspection.setPl(pl);
		model.addAttribute("plInspection", plInspection);
		List<ChecklistServiceTypeMapping> imagesChecklist = checklistServiceTypeService.findByActiveByServiceTypeAndChecklist(pl.getParent().getServiceType().getId(), "PLINSPNIMAGES");
        for (ChecklistServiceTypeMapping serviceChklst : imagesChecklist) {
            InspectionFilesCommon inspectionFile = new InspectionFilesCommon();
            inspectionFile.setServiceChecklist(serviceChklst);
            inspectionFile.setInspection(plInspection.getInspection());
            plInspection.getInspection().getInspectionSupportDocs().add(inspectionFile);
        }
		model.addAttribute(BpaConstants.BPA_APPLICATION, pl.getParent());
		model.addAttribute(PLINTH_LEVEL_CERTIFICATE, pl);
	}
	
	@PostMapping("/create-inspection/{applicationNumber}")
	public String createInspection(@Valid @ModelAttribute final PLInspection plInspection,
								   @PathVariable final String applicationNumber, final Model model, final BindingResult resultBinder) {
		final PlInspectionService plInspectionService = (PlInspectionService) specificNoticeService
                .find(PlInspectionService.class, specificNoticeService.getCityDetails());
		if (resultBinder.hasErrors()) {
			loadApplication(model, applicationNumber);
			return CREATE_INSPECTION;
		}
		final PLInspection savedInspection = plInspectionService.save(plInspection);
		model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
		return "redirect:/application/plinth-level-certificate/success/view-inspection-details/" + applicationNumber + "/" + savedInspection.getInspection().getInspectionNumber();
	}
}
