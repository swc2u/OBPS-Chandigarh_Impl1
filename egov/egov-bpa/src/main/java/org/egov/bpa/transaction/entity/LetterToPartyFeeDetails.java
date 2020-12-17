package org.egov.bpa.transaction.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
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

import org.egov.bpa.transaction.entity.LetterToPartyFee;
import org.egov.bpa.transaction.entity.LetterToPartyFeeMaster;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "egbpa_permit_lettertoparty_fee_details")
@SequenceGenerator(name = LetterToPartyFeeDetails.SEQ_LETTERTOPARTY_FEE_DETAILS, sequenceName = LetterToPartyFeeDetails.SEQ_LETTERTOPARTY_FEE_DETAILS, allocationSize = 1)
public class LetterToPartyFeeDetails extends AbstractAuditable {
	private static final long serialVersionUID = 3078684328383202788L;
    public static final String SEQ_LETTERTOPARTY_FEE_DETAILS = "seq_egbpa_permit_lettertoparty_fee_details";
    
    @Id
    @GeneratedValue(generator = SEQ_LETTERTOPARTY_FEE_DETAILS, strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lettertopartyfee", nullable = false)
    private LetterToPartyFee letterToPartyFee;
    
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "feemstr", nullable = false)
    private LetterToPartyFeeMaster letterToPartyFeeMaster;
    
    @Column(name = "floorarea")
    private BigDecimal floorarea;
    
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "ismandatory")
    private Boolean isMandatory;
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

	public LetterToPartyFee getLetterToPartyFee() {
		return letterToPartyFee;
	}

	public void setLetterToPartyFee(LetterToPartyFee letterToPartyFee) {
		this.letterToPartyFee = letterToPartyFee;
	}

	public LetterToPartyFeeMaster getLetterToPartyFeeMaster() {
		return letterToPartyFeeMaster;
	}

	public void setLetterToPartyFeeMaster(LetterToPartyFeeMaster letterToPartyFeeMaster) {
		this.letterToPartyFeeMaster = letterToPartyFeeMaster;
	}

	public BigDecimal getFloorarea() {
		return floorarea;
	}

	public void setFloorarea(BigDecimal floorarea) {
		this.floorarea = floorarea;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}    
}
