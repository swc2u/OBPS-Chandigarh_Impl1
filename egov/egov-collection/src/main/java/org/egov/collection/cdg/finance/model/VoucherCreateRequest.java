package org.egov.collection.cdg.finance.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoucherCreateRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String tenantId;
	private RequestInfo requestInfo;
	private List<Voucher> vouchers;
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public RequestInfo getRequestInfo() {
		return requestInfo;
	}
	@JsonProperty("RequestInfo")
	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}
	public List<Voucher> getVouchers() {
		return vouchers;
	}
	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}
	
	
}
