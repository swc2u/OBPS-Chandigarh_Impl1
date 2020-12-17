package org.egov.bpa.transaction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.egov.infra.persistence.entity.AbstractAuditable;

@Entity
@Table(name = "egbpa_lettertoparty_fee_mstr")
@SequenceGenerator(name = LetterToPartyFeeMaster.SEQ_LETTERTOPARTY_FEE_MASTER, sequenceName = LetterToPartyFeeMaster.SEQ_LETTERTOPARTY_FEE_MASTER, allocationSize = 1)
public class LetterToPartyFeeMaster  extends AbstractAuditable {
	
	private static final long serialVersionUID = 3078684328383202788L;
	
	public static final String SEQ_LETTERTOPARTY_FEE_MASTER = "seq_egbpa_lettertoparty_fee_mstr";

    @Id
    @GeneratedValue(generator = SEQ_LETTERTOPARTY_FEE_MASTER, strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(name = "feename")
    private String feeName;
    
    @Column(name = "floornumber")
    private Long floorNumber;
    
    private Boolean isActive;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public Long getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Long floorNumber) {
		this.floorNumber = floorNumber;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
