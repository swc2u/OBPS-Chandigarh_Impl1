package org.egov.bpa.entitiy.national.dashboard;

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
	
	@JsonProperty("todaysClosedApplicationsOC")
	private int todaysClosedApplicationsOC;
	
	@JsonProperty("todaysCompletedApplicationsWithinSLAOC")
	private int todaysCompletedApplicationsWithinSLAOC;
	
	@JsonProperty("todaysClosedApplicationsPermit")
	private int todaysClosedApplicationsPermit;
	
	@JsonProperty("todaysCompletedApplicationsWithinSLAPermit")
	private int todaysCompletedApplicationsWithinSLAPermit;
	
	@JsonProperty("slaComplianceOC")
	private int slaComplianceOC;
	
	@JsonProperty("slaCompliancePermit")
	private int slaCompliancePermit;
	
	@JsonProperty("applicationsWithDeviation")
	private int applicationsWithDeviation;
	
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

	public int getTodaysCompletedApplicationsWithinSLAOC() {
		return todaysCompletedApplicationsWithinSLAOC;
	}

	public void setTodaysCompletedApplicationsWithinSLAOC(int todaysCompletedApplicationsWithinSLAOC) {
		this.todaysCompletedApplicationsWithinSLAOC = todaysCompletedApplicationsWithinSLAOC;
	}

	public int getTodaysCompletedApplicationsWithinSLAPermit() {
		return todaysCompletedApplicationsWithinSLAPermit;
	}

	public void setTodaysCompletedApplicationsWithinSLAPermit(int todaysCompletedApplicationsWithinSLAPermit) {
		this.todaysCompletedApplicationsWithinSLAPermit = todaysCompletedApplicationsWithinSLAPermit;
	}

	public int getSlaComplianceOC() {
		return slaComplianceOC;
	}

	public void setSlaComplianceOC(int slaComplianceOC) {
		this.slaComplianceOC = slaComplianceOC;
	}

	public int getSlaCompliancePermit() {
		return slaCompliancePermit;
	}

	public void setSlaCompliancePermit(int slaCompliancePermit) {
		this.slaCompliancePermit = slaCompliancePermit;
	}

}