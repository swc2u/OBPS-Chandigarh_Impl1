package com.pwc.XlsxReader.entity;

public class JobNumber {
	
	public static final String JOB_NUMBER_PREFIX="jn";
	private String key;
	private String jobNumber;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	public static String getJobNumberPrefix() {
		return JOB_NUMBER_PREFIX;
	}
	
	
}
