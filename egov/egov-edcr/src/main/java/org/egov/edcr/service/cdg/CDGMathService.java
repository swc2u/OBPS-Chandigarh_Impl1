package org.egov.edcr.service.cdg;

import java.math.BigDecimal;

public class CDGMathService {
	public static BigDecimal ZERO_POINT_FOUR_INCH_IN_FEET=new BigDecimal("0.033");
	
	public static int compare(BigDecimal value1,BigDecimal value2,BigDecimal delta) {
		int result=0;
		value1=value1.setScale(2,BigDecimal.ROUND_HALF_UP);
		value2=value2.setScale(2,BigDecimal.ROUND_HALF_UP);
		delta=delta.setScale(2,BigDecimal.ROUND_HALF_UP);
		if(doublesAreEqual(value1.doubleValue(), value2.doubleValue(), delta.doubleValue()))
			result=0;
		else
			result=value1.compareTo(value2);
		return result;
	}
	static boolean doublesAreEqual(double value1, double value2, double delta) {
		validDelta(delta);
		return doublesAreEqual(value1, value2) || Math.abs(value1 - value2) <= delta;
	}
	
	static boolean doublesAreEqual(double value1, double value2) {
		return Double.doubleToLongBits(value1) == Double.doubleToLongBits(value2);
	}
	
	static void validDelta(double delta) {
		if (Double.isNaN(delta) || delta < 0.0) {
			throw new RuntimeException("positive delta expected but was: <" + delta + ">");
		}
	}
}
