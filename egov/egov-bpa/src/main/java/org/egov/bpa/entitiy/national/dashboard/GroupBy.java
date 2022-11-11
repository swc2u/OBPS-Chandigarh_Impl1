package org.egov.bpa.entitiy.national.dashboard;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;


public class GroupBy {
	private String groupBy;
	private List<JSONObject> buckets;
	
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public List<JSONObject> getBuckets() {
		return buckets;
	}
	public void setBuckets(List<JSONObject> buckets) {
		this.buckets = buckets;
	}
}
