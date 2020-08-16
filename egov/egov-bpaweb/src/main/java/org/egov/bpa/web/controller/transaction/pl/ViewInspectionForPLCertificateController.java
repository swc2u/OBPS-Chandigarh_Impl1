package org.egov.bpa.web.controller.transaction.pl;

import org.egov.bpa.transaction.entity.pl.PLInspection;
import org.egov.bpa.transaction.service.pl.PlInspectionService;
import org.egov.infra.custom.CustomImplProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/application/plinth-level-certificate")
public class ViewInspectionForPLCertificateController {
	private static final String SHOW_INSPECTION_DETAILS = "pl-show-inspection";

    private static final String INSPECTION_RESULT = "pl-inspection-success";
    @Autowired
    private CustomImplProvider specificNoticeService;
    @Autowired
    protected ResourceBundleMessageSource messageSource;
    
    @GetMapping("/success/view-inspection-details/{applicationNumber}/{inspectionNumber}")
    public String viewInspection(@PathVariable final String applicationNumber,
            @PathVariable final String inspectionNumber, final Model model) {
		final PlInspectionService plInspectionService = (PlInspectionService) specificNoticeService
                .find(PlInspectionService.class, specificNoticeService.getCityDetails());
        PLInspection plInspection = plInspectionService.findByPlApplicationNoAndInspectionNo(applicationNumber,
                inspectionNumber);
        model.addAttribute("message", messageSource.getMessage("msg.inspection.saved.success", null, null));
        plInspectionService.prepareImagesForView(plInspection);
        model.addAttribute("plInspection", plInspection);
        return INSPECTION_RESULT;
    }

    @GetMapping("/show-inspection-details/{applicationNumber}/{inspectionNumber}")
    public String showInspectionDetails(@PathVariable final String applicationNumber,
            @PathVariable final String inspectionNumber, final Model model) {
    	final PlInspectionService plInspectionService = (PlInspectionService) specificNoticeService
                .find(PlInspectionService.class, specificNoticeService.getCityDetails());
        PLInspection plInspection = plInspectionService.findByPlApplicationNoAndInspectionNo(applicationNumber,
                inspectionNumber);
        plInspectionService.prepareImagesForView(plInspection);
        model.addAttribute("plInspection", plInspection);
        return SHOW_INSPECTION_DETAILS;
    }
}
