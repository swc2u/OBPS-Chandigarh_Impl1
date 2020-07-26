package org.egov.collection.cdg.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Functionary implements Serializable{

	private Integer id;

	private BigDecimal code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getCode() {
		return code;
	}

	public void setCode(BigDecimal code) {
		this.code = code;
	}
	
	
}
