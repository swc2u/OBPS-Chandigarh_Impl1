package org.egov.bpa.entitiy.national.dashboard;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;


public class SearchCriteria {
	private String fromDate;
	private String toDate;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
}
