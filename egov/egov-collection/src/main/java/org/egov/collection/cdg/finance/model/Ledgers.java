package org.egov.collection.cdg.finance.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Ledgers {

	private Long id;
	private BigDecimal orderId;
	private String glcode;
	private long debitAmount;
	private long creditAmount;
	private Function function;
	private List<SubledgerDetail> subledgerDetails=new ArrayList<SubledgerDetail>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGlcode() {
		return glcode;
	}

	public void setGlcode(String glcode) {
		this.glcode = glcode;
	}

	public BigDecimal getOrderId() {
		return orderId;
	}

	public void setOrderId(BigDecimal orderId) {
		this.orderId = orderId;
	}

	public List<SubledgerDetail> getSubledgerDetails() {
		return subledgerDetails;
	}

	public void setSubledgerDetails(List<SubledgerDetail> subledgerDetails) {
		this.subledgerDetails = subledgerDetails;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public long getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(long debitAmount) {
		this.debitAmount = debitAmount;
	}

	public long getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(long creditAmount) {
		this.creditAmount = creditAmount;
	}

}
