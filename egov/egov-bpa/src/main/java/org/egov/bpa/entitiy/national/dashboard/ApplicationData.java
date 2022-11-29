package org.egov.bpa.entitiy.national.dashboard;

import java.util.Date;

public class ApplicationData {

	private String applicationNumber;
	private String applicationSubType;
	private String edcrNumber;
	 private Date planPermissionDate;
	 private Date applicationDate;
	
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String getApplicationSubType() {
		return applicationSubType;
	}
	public void setApplicationSubType(String applicationSubType) {
		this.applicationSubType = applicationSubType;
	}
	public String getEdcrNumber() {
		return edcrNumber;
	}
	public void setEdcrNumber(String edcrNumber) {
		this.edcrNumber = edcrNumber;
	}
	public Date getPlanPermissionDate() {
		return planPermissionDate;
	}
	public void setPlanPermissionDate(Date planPermissionDate) {
		this.planPermissionDate = planPermissionDate;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
}
