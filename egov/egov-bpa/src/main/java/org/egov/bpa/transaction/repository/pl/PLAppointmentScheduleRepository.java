package org.egov.bpa.transaction.repository.pl;

import java.util.List;

import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.bpa.transaction.entity.pl.PLAppointmentSchedule;
import org.egov.bpa.transaction.entity.pl.PlinthLevelCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PLAppointmentScheduleRepository extends JpaRepository<PLAppointmentSchedule, Long>{
	List<PLAppointmentSchedule> findByPlAndAppointmentScheduleCommon_PurposeOrderByIdDesc(PlinthLevelCertificate pl, AppointmentSchedulePurpose purpose);

    List<PLAppointmentSchedule> findByIdOrderByIdAsc(Long id);
}
