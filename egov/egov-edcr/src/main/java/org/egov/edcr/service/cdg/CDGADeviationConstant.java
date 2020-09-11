package org.egov.edcr.service.cdg;

import java.math.BigDecimal;

public class CDGADeviationConstant {

	public static final BigDecimal PLINTH_DEVIATION_MAX=new BigDecimal("0.008");
	public static final BigDecimal TOILET_DEVIATION_AREA=new BigDecimal("-0.01");
	public static final BigDecimal TOILET_DEVIATION_WIDTH=new BigDecimal("0.008");
	public static final BigDecimal TOILET_DEVIATION_HEIGHT=new BigDecimal("0");
	public static final BigDecimal KITCHN_DEVIATION_AREA=new BigDecimal("0.05");
	public static final BigDecimal KITCHN_DEVIATION_WIDTH=new BigDecimal("0");
	public static final BigDecimal ROOM_DEVIATION_WIDTH=new BigDecimal("0.0384");
	public static final BigDecimal ROOM_DEVIATION_AREA=new BigDecimal("-0.21");
	public static final BigDecimal TERRACE_DEVIATION_MIN=new BigDecimal("0.019");//
	public static final BigDecimal TERRACE_DEVIATION_MAX=new BigDecimal("0.048");
	public static final BigDecimal PARAPET_DEVIATION_MIN_FEET=new BigDecimal("-0.03");
	public static final BigDecimal PARAPET_DEVIATION_MAX_FEET=new BigDecimal("0.063");
	
	public static BigDecimal addDeviation(BigDecimal value,BigDecimal deviation) {
		return value.add(deviation);
	}
}
