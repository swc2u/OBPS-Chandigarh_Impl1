package org.egov.bpa.web.controller.transaction.pl;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_SITE_INS;

import java.util.List;

import javax.validation.Valid;

import org.egov.bpa.master.service.AppointmentLocationsService;
import org.egov.bpa.transaction.entity.common.AppointmentScheduleCommon;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.pl.PLAppointmentSchedule;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.service.common.AppointmentScheduleCommonService;
import org.egov.bpa.transaction.service.pl.PLAppointmentScheduleService;
import org.egov.bpa.transaction.service.pl.PlinthLevelCertificateService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/application/plinth-level-certificate")
public class PLCertificateScheduleAppointmentController extends BpaGenericApplicationController {
	public static final String PL_CERTIFICATE = "plinthLevelCertificate";
	private static final String MSG = "message";
    private static final String APPOINTMENT_LOCATIONS_LIST = "appointmentLocationsList";
    private static final String APPOINTMENT_SCHEDULED_LIST = "appointmentScheduledList";
    private static final String SCHEDULE_APPIONTMENT_RESULT = "pl-scheduled-appointment-result";
    private static final String MESSAGE = MSG;
    private static final String APPLICATION_NUMBER = "applicationNumber";
    private static final String REDIRECT_APPLICATION_VIEW_APPOINTMENT = "redirect:/application/plinth-level-certificate/appointment/view-details/";
    private static final String PL_RESCHEDULE_APPOINTMENT = "pl-reschedule-appointment";
    private static final String PL_APPOINTMENT_SCHEDULE = "plAppointmentSchedule";
    private static final String PL_SCHEDULE_APPOINTMENT_NEW = "pl-schedule-appointment-new";
    public static final String CITIZEN_SUCEESS = "citizen_suceess";
    public static final String COMMON_ERROR = "common-error";
    
    @Autowired
    private PLAppointmentScheduleService plAppointmentScheduleService;
    @Autowired
    private AppointmentLocationsService appointmentLocationsService;
    @Autowired
    private PlinthLevelCertificateService plinthLevelCertificateService;
    @Autowired
    private AppointmentScheduleCommonService appointmentScheduleCommonService;
    
    @GetMapping("/schedule-appointment/{applicationNumber}")
    public String showScheduleAppointmentForPL(@PathVariable final String applicationNumber, final Model model) {
        PlinthLevelCertificate pl = plinthLevelCertificateService.findByApplicationNumber(applicationNumber);
        PLAppointmentSchedule plAppointmentSchedule = new PLAppointmentSchedule();
        AppointmentScheduleCommon appointmentSchedule = new AppointmentScheduleCommon();
        if ((APPLICATION_STATUS_SITE_INS.equalsIgnoreCase(pl.getStatus().getCode())
                    || BpaConstants.APPLICATION_STATUS_REGISTERED.equalsIgnoreCase(pl.getStatus().getCode()))
                   && BpaConstants.FWD_TO_JE_FOR_SITE_INS.equalsIgnoreCase(pl.getCurrentState().getNextAction())) {
            appointmentSchedule.setPurpose(AppointmentSchedulePurpose.INSPECTION);
        }
        plAppointmentSchedule.setAppointmentScheduleCommon(appointmentSchedule);
        plAppointmentSchedule.setPl(pl);
        model.addAttribute(APPOINTMENT_LOCATIONS_LIST, appointmentLocationsService.findAllOrderByOrderNumber());
        model.addAttribute(PL_APPOINTMENT_SCHEDULE, plAppointmentSchedule);
        model.addAttribute(APPLICATION_NUMBER, applicationNumber);
        return PL_SCHEDULE_APPOINTMENT_NEW;
    }
    
    @PostMapping("/schedule-appointment/{applicationNumber}")
    public String scheduleAppointmentForPL(@Valid @ModelAttribute final PLAppointmentSchedule plAppointmentSchedule,
            @PathVariable final String applicationNumber, final Model model, final RedirectAttributes redirectAttributes) {
    	PLAppointmentSchedule schedule = buildAndSaveNewAppointment(plAppointmentSchedule, applicationNumber);
        if (AppointmentSchedulePurpose.INSPECTION.equals(plAppointmentSchedule.getAppointmentScheduleCommon().getPurpose())) {
            redirectAttributes.addFlashAttribute(MESSAGE,
                    messageSource.getMessage("msg.new.appoimt.fieldins", null, null));
        }
        return REDIRECT_APPLICATION_VIEW_APPOINTMENT + schedule.getId();
    }

    private PLAppointmentSchedule buildAndSaveNewAppointment(final PLAppointmentSchedule plAppointmentSchedule, final String applicationNumber) {
    	PLAppointmentSchedule schedule = plAppointmentScheduleService.save(plAppointmentSchedule);
        return schedule;
    }
    
    @GetMapping("/reschedule-appointment/{scheduleType}/{applicationNumber}")
    public String showReScheduleAppointmentForPl(@PathVariable final AppointmentSchedulePurpose scheduleType,
            @PathVariable final String applicationNumber, final Model model) {
    	PlinthLevelCertificate pl = plinthLevelCertificateService.findByApplicationNumber(applicationNumber);
        List<PLAppointmentSchedule> appointmentScheduledList = plAppointmentScheduleService.findByApplication(pl, scheduleType);
        PLAppointmentSchedule appointmentSchedule = new PLAppointmentSchedule();
        AppointmentScheduleCommon appointmentScheduleCommon = new AppointmentScheduleCommon();
        appointmentScheduleCommon.setPurpose(appointmentScheduledList.get(0).getAppointmentScheduleCommon().getPurpose());
        appointmentSchedule.setAppointmentScheduleCommon(appointmentScheduleCommon);
        model.addAttribute(APPOINTMENT_LOCATIONS_LIST, appointmentLocationsService.findAllOrderByOrderNumber());
        model.addAttribute(PL_APPOINTMENT_SCHEDULE, appointmentSchedule);
        model.addAttribute(APPLICATION_NUMBER, applicationNumber);
        model.addAttribute(APPOINTMENT_SCHEDULED_LIST, appointmentScheduledList);
        model.addAttribute("mode", "postponeappointment");
        return PL_RESCHEDULE_APPOINTMENT;
    }

    @PostMapping("/reschedule-appointment/{scheduleType}/{applicationNumber}")
    public String reScheduleAppointmentForPL(@Valid @ModelAttribute final PLAppointmentSchedule plAppointmentSchedule,
            @PathVariable final AppointmentSchedulePurpose scheduleType, @PathVariable final String applicationNumber,
            @RequestParam Long parentAppointmentScheduleId, final RedirectAttributes redirectAttributes) {
    	PlinthLevelCertificate pl = plinthLevelCertificateService.findByApplicationNumber(applicationNumber);
        AppointmentScheduleCommon parent = appointmentScheduleCommonService.findById(parentAppointmentScheduleId);
        plAppointmentSchedule.getAppointmentScheduleCommon().setPostponed(true);
        plAppointmentSchedule.getAppointmentScheduleCommon().setParent(parent);
        plAppointmentSchedule.setPl(pl);
        PLAppointmentSchedule schedule = plAppointmentScheduleService.save(plAppointmentSchedule);
        if (AppointmentSchedulePurpose.INSPECTION.equals(schedule.getAppointmentScheduleCommon().getPurpose())) {
            redirectAttributes.addFlashAttribute(MESSAGE, messageSource.getMessage("msg.update.appoimt.fieldins", null, null));
        }
        return REDIRECT_APPLICATION_VIEW_APPOINTMENT + schedule.getId();
    }
    
    @GetMapping("/appointment/view-details/{id}")
    public String viewScheduledAppointment(@PathVariable final Long id, final Model model) {
        List<PLAppointmentSchedule> appointmentScheduledList = plAppointmentScheduleService.findByIdAsList(id);
        model.addAttribute(APPOINTMENT_SCHEDULED_LIST, appointmentScheduledList);
        return SCHEDULE_APPIONTMENT_RESULT;
    }
}
