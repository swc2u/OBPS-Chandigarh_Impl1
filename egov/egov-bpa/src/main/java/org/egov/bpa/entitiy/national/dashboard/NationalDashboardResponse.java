package org.egov.bpa.entitiy.national.dashboard;

import java.util.List;

import org.springframework.data.web.config.EnableSpringDataWebSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NationalDashboardResponse {
	@JsonProperty("state")
	private String state="Chandigarh";	
	
//	@JsonProperty("plansScrutinized")
//	private long plansScrutinized;	
	
	@JsonProperty("totalPermitsIssued")
	private long totalPermitsIssued;
	
	@JsonProperty("todaysCollection")
	private List<GroupBy> todaysCollection;
	
	@JsonProperty("permitsIssuedByRiskType")
	private List<GroupBy> permitsIssuedByRiskType;
	
	@JsonProperty("permitsIssuedByOccupancyType")
	private List<GroupBy> permitsIssuedByOccupancyType;
	
	@JsonProperty("permitsIssuedBySubOccupancyType")
	private List<GroupBy> permitsIssuedBySubOccupancyType;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

//	public long getPlansScrutinized() {
//		return plansScrutinized;
//	}
//
//	public void setPlansScrutinized(long plansScrutinized) {
//		this.plansScrutinized = plansScrutinized;
//	}

	public List<GroupBy> getTodaysCollection() {
		return todaysCollection;
	}

	public void setTodaysCollection(List<GroupBy> todaysCollection) {
		this.todaysCollection = todaysCollection;
	}

	public List<GroupBy> getPermitsIssuedByRiskType() {
		return permitsIssuedByRiskType;
	}

	public void setPermitsIssuedByRiskType(List<GroupBy> permitsIssuedByRiskType) {
		this.permitsIssuedByRiskType = permitsIssuedByRiskType;
	}

	public List<GroupBy> getPermitsIssuedBySubOccupancyType() {
		return permitsIssuedBySubOccupancyType;
	}

	public void setPermitsIssuedBySubOccupancyType(List<GroupBy> permitsIssuedBySubOccupancyType) {
		this.permitsIssuedBySubOccupancyType = permitsIssuedBySubOccupancyType;
	}

	public List<GroupBy> getPermitsIssuedByOccupancyType() {
		return permitsIssuedByOccupancyType;
	}

	public void setPermitsIssuedByOccupancyType(List<GroupBy> permitsIssuedByOccupancyType) {
		this.permitsIssuedByOccupancyType = permitsIssuedByOccupancyType;
	}

	public long getTotalPermitsIssued() {
		return totalPermitsIssued;
	}

	public void setTotalPermitsIssued(long totalPermitsIssued) {
		this.totalPermitsIssued = totalPermitsIssued;
	}

}
