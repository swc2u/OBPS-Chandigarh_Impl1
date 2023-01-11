/****************************************************************
@author: Narendra Maravi
Date   : 01-JAN-2023
Class  : BpaNocApplicationHistory.java
******************************************************************/
package org.egov.bpa.transaction.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.infra.admin.master.entity.User;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "EGBPA_NOCAPPLICATION_HISTORY")
@SequenceGenerator(name = BpaNocApplicationHistory.SEQ_NOCAPPLICATION, sequenceName = BpaNocApplicationHistory.SEQ_NOCAPPLICATION, allocationSize = 1)
public class BpaNocApplicationHistory extends AbstractAuditable {

    public static final String SEQ_NOCAPPLICATION = "SEQ_EGBPA_NOCAPPLICATION_HISTORY";
    public static final String ORDER_BY_ID_ASC = "id ASC";
    private static final long serialVersionUID = -361205348191992869L;
    public static final String ORDER_BY_NUMBER_ASC = "orderNumber ASC";
    @Id
    @GeneratedValue(generator = SEQ_NOCAPPLICATION, strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @SafeHtml
    @Column(name= "noc_id")
    private Long nocId;

    @SafeHtml
    @Length(min = 1, max = 128)
    private String nocApplicationNumber;

    @SafeHtml
    @Length(min = 1, max = 256)
    private String nocType;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private BpaStatus status;

    @SafeHtml
    private String remarks;
    private Date slaEndDate;
    private Date deemedApprovedDate;
    
    @SafeHtml
    @Length(min = 1, max = 2000)
    private String comments;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNocType() {
		return nocType;
	}
	public void setNocType(String nocType) {
		this.nocType = nocType;
	}
	public BpaStatus getStatus() {
		return status;
	}
	public void setStatus(BpaStatus status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getSlaEndDate() {
		return slaEndDate;
	}
	public void setSlaEndDate(Date slaEndDate) {
		this.slaEndDate = slaEndDate;
	}
	public Date getDeemedApprovedDate() {
		return deemedApprovedDate;
	}
	public void setDeemedApprovedDate(Date deemedApprovedDate) {
		this.deemedApprovedDate = deemedApprovedDate;
	}
	
	public String getNocApplicationNumber() {
		return nocApplicationNumber;
	}
	public void setNocApplicationNumber(String nocApplicationNumber) {
		this.nocApplicationNumber = nocApplicationNumber;
	}
	
	public Long getNocId() {
		return nocId;
	}
	public void setNocId(Long nocId) {
		this.nocId = nocId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}