package org.egov.common.entity.bpa;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.SafeHtml;

//@Entity
//@Table(name = "eg_noc_evaluation")
//@SequenceGenerator(name = NocEvaluation.SEQ_NOC_EVALUATION, sequenceName = NocEvaluation.SEQ_NOC_EVALUATION, allocationSize = 1)
public class NocEvaluation extends AbstractAuditable {
	
	private static final long serialVersionUID = 3078684328383202788L;
    public static final String SEQ_NOC_EVALUATION = "seq_eg_noc_evaluation";
    
    @Id
    @GeneratedValue(generator = SEQ_NOC_EVALUATION, strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist")
    private Checklist checklist;
    
    @SafeHtml
    private String remarks;
    
    @SafeHtml
    private String comment;
    
    private Long nocapplication;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getNocapplication() {
		return nocapplication;
	}

	public void setNocapplication(Long nocapplication) {
		this.nocapplication = nocapplication;
	}
    
    
}
