package org.egov.collection.cdg.finance.model;

import java.util.ArrayList;

public class FinanceUserRequest {
	private float id;
	private String uuid;
	private String userName;
	private String name;
	private String mobileNumber;
	private String emailId;
	private String locale = null;
	private String type;
	ArrayList<Object> roles = new ArrayList<Object>();
	private boolean active;
	private String tenantId;

	// Getter Methods

	public float getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUserName() {
		return userName;
	}

	public String getName() {
		return name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getLocale() {
		return locale;
	}

	public String getType() {
		return type;
	}

	public boolean getActive() {
		return active;
	}

	public String getTenantId() {
		return tenantId;
	}

	// Setter Methods

	public void setId(float id) {
		this.id = id;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
