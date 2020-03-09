package org.egov.bpa.service;

import java.math.BigDecimal;
import java.util.Date;

public class testCode {
	private static final BigDecimal TEN = BigDecimal.valueOf(10);
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal FORTY = BigDecimal.valueOf(40);
    private static final BigDecimal FIFTEEN = BigDecimal.valueOf(15);
    private static final BigDecimal TWO_FIVE = BigDecimal.valueOf(2.5);
    private static final BigDecimal EIGHTEEN = BigDecimal.valueOf(18);
    private static final BigDecimal SQMT_SQFT_MULTIPLIER = BigDecimal.valueOf(10.764);
	public static void main(String []args){
		/*
		 * BigDecimal totalAmount = BigDecimal.ZERO; BigDecimal a = (new
		 * BigDecimal(173.36)).multiply(SQMT_SQFT_MULTIPLIER).multiply(TWO_FIVE).
		 * setScale(2, BigDecimal.ROUND_HALF_UP); totalAmount = totalAmount.add((new
		 * BigDecimal(173.36)).multiply(SQMT_SQFT_MULTIPLIER).multiply(TWO_FIVE).
		 * setScale(2, BigDecimal.ROUND_HALF_UP)); System.out.println("Hello World " +
		 * totalAmount.toString() + " --- " + a.toString());
		 */
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalAreaInHalfAcre = new BigDecimal(464000.5234) .divide(new BigDecimal(2023.43),2,BigDecimal.ROUND_HALF_UP);
		System.out.println(totalAreaInHalfAcre);
		int halfAcreCount = totalAreaInHalfAcre.intValue();
		System.out.println(halfAcreCount);
		halfAcreCount = (totalAreaInHalfAcre.compareTo(new BigDecimal(halfAcreCount))>=0)?halfAcreCount+1:halfAcreCount;	    		
		System.out.println(halfAcreCount);
		totalAmount = totalAmount.add(BigDecimal.valueOf(10000).multiply(new BigDecimal(halfAcreCount)).setScale(2, BigDecimal.ROUND_HALF_UP));
		System.out.println(totalAmount);
    }
}
