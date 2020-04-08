package org.egov.commons.cdg;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;

import org.springframework.stereotype.Service;

public class CDGAdditionalService {
	public static HashMap<String, String> planInfo=new HashMap<String, String>();
	
	public static BigDecimal roundBigDecimal(BigDecimal number, int mathContext) {
		BigDecimal b=number.setScale(mathContext, BigDecimal.ROUND_HALF_EVEN);
		return b;

	}
	
	public static BigDecimal roundBigDecimal(BigDecimal number) {
		BigDecimal b=number.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return b;
	}

}
