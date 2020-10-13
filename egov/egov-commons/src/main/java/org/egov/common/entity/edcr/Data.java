package org.egov.common.entity.edcr;

import java.io.Serializable;
import java.math.BigDecimal;

public class Data implements Serializable{

	private static final long serialVersionUID = 1L;
	private BigDecimal oc;
	private BigDecimal permit;
	private BigDecimal deviation;
	
	public Data() {
		super();
	}

	public Data(BigDecimal oc, BigDecimal permit, BigDecimal deviation) {
		super();
		this.oc = oc;
		this.permit = permit;
		this.deviation = deviation;
	}

	public BigDecimal getOc() {
		return oc;
	}

	public void setOc(BigDecimal oc) {
		this.oc = oc;
	}

	public BigDecimal getPermit() {
		return permit;
	}

	public void setPermit(BigDecimal permit) {
		this.permit = permit;
	}

	public BigDecimal getDeviation() {
		return deviation;
	}

	public void setDeviation(BigDecimal deviation) {
		this.deviation = deviation;
	}

}