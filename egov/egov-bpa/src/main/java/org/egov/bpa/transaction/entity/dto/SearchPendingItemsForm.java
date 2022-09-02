package org.egov.bpa.transaction.entity.dto;

import java.util.Date;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.infra.web.support.search.DataTableSearchRequest;
import org.hibernate.validator.constraints.SafeHtml;

public class SearchPendingItemsForm extends DataTableSearchRequest {
	private Long id;
    @SafeHtml
    private String applicationNumber;
    private Date applicationDate;
    private Long applicationTypeId;
    @SafeHtml
    private String applicantType;
    private Long serviceTypeId;
    @SafeHtml
    private String serviceType;
    @SafeHtml
    private String currentOwner;    
    @SafeHtml
    private String occupancy;
    @SafeHtml
    private String serviceCode;
    private Date fromDate;
    private Date toDate;
    @SafeHtml
    private String status;   
    @SafeHtml
    private String applicantName;    
    @SafeHtml
    private String pendingAction; 
    @SafeHtml
    private String applicationType;
    private int ellapseTime;
    @SafeHtml
    private String currentOwnerDesg; 
    
    private String sector;
    private String plotNumber;
    private String ownerName;
    private String bPAApplicationType;
    
    public SearchPendingItemsForm() {}

	public SearchPendingItemsForm(BpaApplication application, String currentOwnerName, String currentOwnerDesg, String pendingAction, int ellapseDays) {
        setId(application.getId());
        setApplicationNumber(application.getApplicationNumber());
        setApplicantName(application.getApplicantName());
        setApplicationDate(application.getApplicationDate());
        setApplicationType(application.getApplicationType() != null ? application.getApplicationType().getDescription() : "");
        setOccupancy(application.getOccupanciesName());
        setServiceType(application.getServiceType().getDescription());
        setServiceCode(application.getServiceType().getCode());        
        setStatus(application.getStatus().getCode());
        setCurrentOwner(currentOwnerName);
        setCurrentOwnerDesg(currentOwnerDesg);
        setPendingAction(pendingAction);
        setEllapseTime(ellapseDays);
    }
	
	 public SearchPendingItemsForm(OccupancyCertificate occupancyCertificate, String currentOwnerName, String currentOwnerDesg, String pendingAction, int ellapseDays) {
        setId(occupancyCertificate.getId());
        setApplicationNumber(occupancyCertificate.getApplicationNumber());
        setApplicantName(occupancyCertificate.getParent().getOwner().getName());
        setApplicationDate(occupancyCertificate.getApplicationDate());
        setApplicationType(occupancyCertificate.getApplicationType());
        setOccupancy(occupancyCertificate.getParent().getOccupanciesName());
        setServiceType(occupancyCertificate.getParent().getServiceType().getDescription());
        setServiceCode(occupancyCertificate.getParent().getServiceType().getCode());
        setStatus(occupancyCertificate.getStatus().getCode());
        setCurrentOwner(currentOwnerName);
        setCurrentOwnerDesg(currentOwnerDesg);
        setPendingAction(pendingAction);
        setEllapseTime(ellapseDays);
        setSector(occupancyCertificate.getParent().getSector());
        setPlotNumber(occupancyCertificate.getParent().getPlotNumber());
        setBPAApplicationType(occupancyCertificate.getParent().getApplicationType().getDescription());
	}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getCurrentOwner() {
		return currentOwner;
	}
	public void setCurrentOwner(String currentOwner) {
		this.currentOwner = currentOwner;
	}
	public String getOccupancy() {
		return occupancy;
	}
	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getPendingAction() {
		return pendingAction;
	}
	public void setPendingAction(String pendingAction) {
		this.pendingAction = pendingAction;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public int getEllapseTime() {
		return ellapseTime;
	}
	public void setEllapseTime(int ellapseTime) {
		this.ellapseTime = ellapseTime;
	}

	public Long getApplicationTypeId() {
		return applicationTypeId;
	}

	public void setApplicationTypeId(Long applicationTypeId) {
		this.applicationTypeId = applicationTypeId;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getCurrentOwnerDesg() {
		return currentOwnerDesg;
	}

	public void setCurrentOwnerDesg(String currentOwnerDesg) {
		this.currentOwnerDesg = currentOwnerDesg;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getPlotNumber() {
		return plotNumber;
	}

	public void setPlotNumber(String plotNumber) {
		this.plotNumber = plotNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getBPAApplicationType() {
		return bPAApplicationType;
	}

	public void setBPAApplicationType(String bPAApplicationType) {
		this.bPAApplicationType = bPAApplicationType;
	}	
}
