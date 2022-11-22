package org.egov.bpa.entitiy.national.dashboard;


import java.util.List;

import org.springframework.data.web.config.EnableSpringDataWebSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;

public class NationalDashboardResponse {
	@JsonProperty("date")
	private String date;
	
	@JsonProperty("module")
	private String module="OBPS";
	
	@JsonProperty("ward")
	private String ward="Chandigarh";	
	
	@JsonProperty("ulb")
	private String ulb="Chandigarh";	
	
	@JsonProperty("region")
	private String region="Chandigarh";	
	
	@JsonProperty("state")
	private String state="Chandigarh";
	
	@JsonProperty("metrics")
	private Metrics metrics;
	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getUlb() {
		return ulb;
	}

	public void setUlb(String ulb) {
		this.ulb = ulb;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Metrics getMetrics() {
		return metrics;
	}

	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}

}
