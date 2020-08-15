package org.egov.bpa.web.controller.transaction.pl;

import java.util.Date;
import org.egov.bpa.transaction.entity.pl.PLInspection;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.service.pl.PlInspectionService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.PLConstants;
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
public class UpdateInspectionForPLCertificateController  extends BpaGenericApplicationController {
	@Autowired
    private CustomImplProvider specificNoticeService;

	@GetMapping("/update-inspection/{applicationNumber}/{inspectionNumber}")
	public String editInspectionAppointment(
			@PathVariable final String applicationNumber, @PathVariable final String inspectionNumber, final Model model) {
		final PlInspectionService plInspectionService = (PlInspectionService) specificNoticeService
                .find(PlInspectionService.class, specificNoticeService.getCityDetails());
		PLInspection plInspection = plInspectionService.findByPlApplicationNoAndInspectionNo(applicationNumber, inspectionNumber);
		loadApplication(model, plInspection);
		model.addAttribute("mode", "editinsp");
		return "pl-inspection-edit";
	}

	@PostMapping("/update-inspection/{applicationNumber}/{inspectionNumber}")
	public String updateInspection(@ModelAttribute("plInspection") final PLInspection plInspection,
								   @PathVariable final String applicationNumber, @PathVariable final String inspectionNumber, final Model model, final BindingResult resultBinder) {
		if (resultBinder.hasErrors()) {
			loadApplication(model, plInspection);
			return "pl-inspection-edit";
		}
		final PlInspectionService plInspectionService = (PlInspectionService) specificNoticeService
                .find(PlInspectionService.class, specificNoticeService.getCityDetails());
		PLInspection plInspectionRes = plInspectionService.save(plInspection);
		model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
		return "redirect:/application/plinth-level-certificate/success/view-inspection-details/" + applicationNumber + "/" + plInspectionRes.getInspection().getInspectionNumber();
	}

	private void loadApplication(final Model model, final PLInspection plInspection) {
		final PlinthLevelCertificate pl = plInspection.getPl();
		if (pl != null && pl.getState() != null
				&& pl.getState().getValue().equalsIgnoreCase(BpaConstants.APPLICATION_STATUS_REGISTERED)) {
			prepareWorkflowDataForInspection(model, pl);
			model.addAttribute("loginUser", securityUtils.getCurrentUser());
			model.addAttribute(BpaConstants.APPLICATION_HISTORY, workflowHistoryService
					.getHistoryForPL(pl.getAppointmentSchedules(), pl.getCurrentState(), pl.getStateHistory()));
		}
		if (plInspection != null)
			plInspection.getInspection().setInspectionDate(new Date());
		final PlInspectionService plInspectionService = (PlInspectionService) specificNoticeService
                .find(PlInspectionService.class, specificNoticeService.getCityDetails());
		plInspectionService.prepareImagesForView(plInspection);
		model.addAttribute("plInspection", plInspection);
		model.addAttribute(BpaConstants.BPA_APPLICATION, pl.getParent());
		model.addAttribute(PLConstants.PLINTH_LEVEL_CERTIFICATE, pl);
	}
}
