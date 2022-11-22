package org.egov.bpa.entitiy.national.dashboard;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;


public class Metrics {
//	@JsonProperty("plansScrutinized")
//	private long plansScrutinized;	
	
//	@JsonProperty("totalPermitsIssued")
//	private long totalPermitsIssued;
	
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

}