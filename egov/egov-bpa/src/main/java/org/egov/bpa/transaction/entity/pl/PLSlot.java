package org.egov.bpa.transaction.entity.pl;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.bpa.transaction.entity.SlotDetail;
import org.egov.bpa.transaction.entity.enums.ScheduleAppointmentType;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "egbpa_pl_slot")
@SequenceGenerator(name = PLSlot.SEQ, sequenceName = PLSlot.SEQ, allocationSize = 1)
public class PLSlot extends AbstractAuditable {
	protected static final String SEQ = "seq_egbpa_pl_slot";
	private static final long serialVersionUID = 3649900317247159528L;

	@Id
	@GeneratedValue(generator = SEQ, strategy = GenerationType.SEQUENCE)
	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ScheduleAppointmentType scheduleAppointmentType;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "plinthlevelcertificate", nullable = false)
	private PlinthLevelCertificate pl;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "slotdetailid", nullable = false)
	private SlotDetail slotDetail;

	private Boolean isActive;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public ScheduleAppointmentType getScheduleAppointmentType() {
		return scheduleAppointmentType;
	}

	public void setScheduleAppointmentType(ScheduleAppointmentType scheduleAppointmentType) {
		this.scheduleAppointmentType = scheduleAppointmentType;
	}
	
	public SlotDetail getSlotDetail() {
		return slotDetail;
	}

	public void setSlotDetail(SlotDetail slotDetail) {
		this.slotDetail = slotDetail;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	public PlinthLevelCertificate getPl() {
		return pl;
	}

	public void setPl(PlinthLevelCertificate pl) {
		this.pl = pl;
	}
}
