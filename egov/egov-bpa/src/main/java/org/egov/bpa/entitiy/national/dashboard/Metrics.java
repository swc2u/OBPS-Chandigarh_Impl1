package org.egov.bpa.entitiy.national.dashboard;

import java.math.BigDecimal;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;


public class Metrics {
//	@JsonProperty("plansScrutinized")
//	private long plansScrutinized;	
	
//	@JsonProperty("totalPermitsIssued")
//	private long totalPermitsIssued;

	@JsonProperty("applicationsSubmitted")
	private int applicationsSubmitted;
	
	@JsonProperty("ocSubmitted")
	private int ocSubmitted;
	
	@JsonProperty("ocIssued")
	private int ocIssued;
	
	@JsonProperty("landAreaAppliedInSystemForBPA")
	private BigDecimal landAreaAppliedInSystemForBPA;
	
	@JsonProperty("averageDaysToIssuePermit")
	private long averageDaysToIssuePermit;
	
	@JsonProperty("averageDaysToIssueOC")
	private long averageDaysToIssueOC;
	
	@JsonProperty("todaysClosedApplicationsOC")
	private int todaysClosedApplicationsOC;
	
	@JsonProperty("todaysCompletedApplicationsWithinSLAOC")
	private String todaysCompletedApplicationsWithinSLAOC = "NA";
	
	@JsonProperty("todaysClosedApplicationsPermit")
	private int todaysClosedApplicationsPermit;
	
	@JsonProperty("todaysCompletedApplicationsWithinSLAPermit")
	private String todaysCompletedApplicationsWithinSLAPermit = "NA";
	
	@JsonProperty("slaComplianceOC")
	private String slaComplianceOC = "NA";
	
	@JsonProperty("slaCompliancePermit")
	private String slaCompliancePermit = "NA";
	
	@JsonProperty("applicationsWithDeviation")
	private int applicationsWithDeviation;
	
	@JsonProperty("averageDeviation")
	private long averageDeviation;
	
	@JsonProperty("ocWithDeviation")
	private int ocWithDeviation;
	
	
	@JsonProperty("todaysCollection")
	private List<GroupBy> todaysCollection;
	
	@JsonProperty("permitsIssued")
	private List<GroupBy> permitsIssued;
	

	public List<GroupBy> getTodaysCollection() {
		return todaysCollection;
	}

	public void setTodaysCollection(List<GroupBy> todaysCollection) {
		this.todaysCollection = todaysCollection;
	}

	public List<GroupBy> getPermitsIssued() {
		return permitsIssued;
	}

	public void setPermitsIssued(List<GroupBy> permitsIssued) {
		this.permitsIssued = permitsIssued;
	}

	public int getApplicationsSubmitted() {
		return applicationsSubmitted;
	}

	public void setApplicationsSubmitted(int applicationsSubmitted) {
		this.applicationsSubmitted = applicationsSubmitted;
	}

	public int getOcSubmitted() {
		return ocSubmitted;
	}

	public void setOcSubmitted(int ocSubmitted) {
		this.ocSubmitted = ocSubmitted;
	}

	public int getOcIssued() {
		return ocIssued;
	}

	public void setOcIssued(int ocIssued) {
		this.ocIssued = ocIssued;
	}

	public int getTodaysClosedApplicationsOC() {
		return todaysClosedApplicationsOC;
	}

	public void setTodaysClosedApplicationsOC(int todaysClosedApplicationsOC) {
		this.todaysClosedApplicationsOC = todaysClosedApplicationsOC;
	}

	public int getTodaysClosedApplicationsPermit() {
		return todaysClosedApplicationsPermit;
	}

	public void setTodaysClosedApplicationsPermit(int todaysClosedApplicationsPermit) {
		this.todaysClosedApplicationsPermit = todaysClosedApplicationsPermit;
	}

	public int getApplicationsWithDeviation() {
		return applicationsWithDeviation;
	}

	public void setApplicationsWithDeviation(int applicationsWithDeviation) {
		this.applicationsWithDeviation = applicationsWithDeviation;
	}

	public int getOcWithDeviation() {
		return ocWithDeviation;
	}

	public void setOcWithDeviation(int ocWithDeviation) {
		this.ocWithDeviation = ocWithDeviation;
	}

	public String getTodaysCompletedApplicationsWithinSLAOC() {
		return todaysCompletedApplicationsWithinSLAOC;
	}

	public void setTodaysCompletedApplicationsWithinSLAOC(String todaysCompletedApplicationsWithinSLAOC) {
		this.todaysCompletedApplicationsWithinSLAOC = todaysCompletedApplicationsWithinSLAOC;
	}

	public String getTodaysCompletedApplicationsWithinSLAPermit() {
		return todaysCompletedApplicationsWithinSLAPermit;
	}

	public void setTodaysCompletedApplicationsWithinSLAPermit(String todaysCompletedApplicationsWithinSLAPermit) {
		this.todaysCompletedApplicationsWithinSLAPermit = todaysCompletedApplicationsWithinSLAPermit;
	}

	public String getSlaComplianceOC() {
		return slaComplianceOC;
	}

	public void setSlaComplianceOC(String slaComplianceOC) {
		this.slaComplianceOC = slaComplianceOC;
	}

	public String getSlaCompliancePermit() {
		return slaCompliancePermit;
	}

	public void setSlaCompliancePermit(String slaCompliancePermit) {
		this.slaCompliancePermit = slaCompliancePermit;
	}

	public BigDecimal getLandAreaAppliedInSystemForBPA() {
		return landAreaAppliedInSystemForBPA;
	}

	public void setLandAreaAppliedInSystemForBPA(BigDecimal landAreaAppliedInSystemForBPA) {
		this.landAreaAppliedInSystemForBPA = landAreaAppliedInSystemForBPA;
	}

	public long getAverageDaysToIssuePermit() {
		return averageDaysToIssuePermit;
	}

	public void setAverageDaysToIssuePermit(long averageDaysToIssuePermit) {
		this.averageDaysToIssuePermit = averageDaysToIssuePermit;
	}

	public long getAverageDaysToIssueOC() {
		return averageDaysToIssueOC;
	}

	public void setAverageDaysToIssueOC(long averageDaysToIssueOC) {
		this.averageDaysToIssueOC = averageDaysToIssueOC;
	}

	public long getAverageDeviation() {
		return averageDeviation;
	}

	public void setAverageDeviation(long averageDeviation) {
		this.averageDeviation = averageDeviation;
	}

}