package org.egov.bpa.transaction.service.pl;

import java.util.List;

import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.pl.PLAppointmentSchedule;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.egov.bpa.transaction.repository.pl.PLAppointmentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PLAppointmentScheduleService {
	@Autowired
	private PLAppointmentScheduleRepository plAppointmentScheduleRepository;
	
	@Transactional
	public PLAppointmentSchedule save(final PLAppointmentSchedule appointmentSchedule) {
	    return plAppointmentScheduleRepository.save(appointmentSchedule);
	}
	
	public PLAppointmentSchedule findById(Long id) {
	    return plAppointmentScheduleRepository.findOne(id);
	}
	
	public List<PLAppointmentSchedule> findByApplication(PlinthLevelCertificate pl, AppointmentSchedulePurpose type) {
	    return plAppointmentScheduleRepository.findByPlAndAppointmentScheduleCommon_PurposeOrderByIdDesc(pl, type);
	}
	
	public List<PLAppointmentSchedule> findByIdAsList(Long id) {
	    return plAppointmentScheduleRepository.findByIdOrderByIdAsc(id);
	}
}
