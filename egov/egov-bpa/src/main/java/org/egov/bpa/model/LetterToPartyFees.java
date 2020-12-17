package org.egov.bpa.model;

import java.math.BigDecimal;

public class LetterToPartyFees {	
	private Long lpFeeId;
	private Long applicationId;
	private Long lpFeeDetailsId;
	private Long feeMstrId;
    private String feeName;
    private Long floorNumber;
    private BigDecimal floorarea;
    private String remarks;
    private Boolean isActive = true;
    private Boolean isMandatory = false;
	public Long getLpFeeId() {
		return lpFeeId;
	}
	public void setLpFeeId(Long lpFeeId) {
		this.lpFeeId = lpFeeId;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public Long getLpFeeDetailsId() {
		return lpFeeDetailsId;
	}
	public void setLpFeeDetailsId(Long lpFeeDetailsId) {
		this.lpFeeDetailsId = lpFeeDetailsId;
	}
	public Long getFeeMstrId() {
		return feeMstrId;
	}
	public void setFeeMstrId(Long feeMstrId) {
		this.feeMstrId = feeMstrId;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}   
}
