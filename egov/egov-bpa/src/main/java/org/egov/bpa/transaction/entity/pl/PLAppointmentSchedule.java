package org.egov.bpa.transaction.entity.pl;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.egov.bpa.transaction.entity.common.AppointmentScheduleCommon;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "EGBPA_PL_APPOINTMENT_SCHEDULE")
@SequenceGenerator(name = PLAppointmentSchedule.SEQ_EGBPA_APPOINTMENT_SCHEDULE, sequenceName = PLAppointmentSchedule.SEQ_EGBPA_APPOINTMENT_SCHEDULE, allocationSize = 1)
public class PLAppointmentSchedule extends AbstractAuditable {

    public static final String SEQ_EGBPA_APPOINTMENT_SCHEDULE = "SEQ_EGBPA_PL_APPOINTMENT_SCHEDULE";
    private static final long serialVersionUID = -1344489436357479689L;

    @Id
    @GeneratedValue(generator = SEQ_EGBPA_APPOINTMENT_SCHEDULE, strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointmentScheduleCommon")
    private AppointmentScheduleCommon appointmentScheduleCommon;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plinthlevelcertificate")
    private PlinthLevelCertificate pl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppointmentScheduleCommon getAppointmentScheduleCommon() {
		return appointmentScheduleCommon;
	}

	public void setAppointmentScheduleCommon(AppointmentScheduleCommon appointmentScheduleCommon) {
		this.appointmentScheduleCommon = appointmentScheduleCommon;
	}

	public PlinthLevelCertificate getPl() {
		return pl;
	}

	public void setPl(PlinthLevelCertificate pl) {
		this.pl = pl;
	}
}
