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
import javax.validation.constraints.NotNull;

import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "EGBPA_PL_INSPECTION")
@SequenceGenerator(name = PLInspection.SEQ_INSPECTION, sequenceName = PLInspection.SEQ_INSPECTION, allocationSize = 1)
public class PLInspection extends AbstractAuditable {
	protected static final String SEQ_INSPECTION = "SEQ_EGBPA_PL_INSPECTION";
    private static final long serialVersionUID = -3889308488871083896L;
    
    @Id
    @GeneratedValue(generator = SEQ_INSPECTION, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "inspection", nullable = false)
    private InspectionCommon inspection;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "plinthlevelcertificate", nullable = false)
    private PlinthLevelCertificate pl;
    
    @Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public InspectionCommon getInspection() {
		return inspection;
	}

	public void setInspection(InspectionCommon inspection) {
		this.inspection = inspection;
	}

	public PlinthLevelCertificate getPl() {
		return pl;
	}

	public void setPl(PlinthLevelCertificate pl) {
		this.pl = pl;
	}
}
