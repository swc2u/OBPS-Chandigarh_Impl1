package com.pwc.XlsxReader.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class DxfConstent {

	public static final String AREA_CATEGORY = "AREA_CATEGORY";
	public static final String ZONE = "ZONE";
	public static final String PLOT_AREA_TYPE = "PLOT_AREA_TYPE";
	public static final String SECTOR = "SECTOR";
	public static final String RESIDENTIAL = "Residential";
	public static final String PLOTTED = "Plotted";
	public static final String GROUPED = "Grouped";

	public static final String MARLA = "MARLA"; // Till 379.35sqm
	public static final String ONE_KANAL = "ONE_KANAL"; // 379.35sqm to less than 505.85 sqm
	public static final String TWO_KANAL = "TWO_KANAL"; // 505.85sqm to less than 1011.7sqm
	public static final String ABOVE_TWO_KANAL = "ABOVE_TWO_KANAL"; // Above 1011.7sqm

	public static String validateValue(String str) {
		String result = "";
		
		try {
			str=str.trim();
			
			if (str == null || str.length() == 0) {
				return result;
			} else if (str.contains("*")) {
				String[] ary = str.split(Pattern.quote("*"));
				if (ary.length > 0) {
					String r1 = validateFeetInch(ary[0]);
					String r2 = "";
					if (ary.length >= 2) {
						r2 = validateFeetInch(ary[1]);
					}
					result = r1 + "*" + r2;
				}

			} else if (str.contains("F")) {
				result = validateFeetInch(str);
			} else {
				result = str.toUpperCase();
			}
		}catch (Exception e) {
			//System.out.println("str : "+str+"  || "+e.getMessage());
		}
		return result;
	}
	
	public static String getString(String str) {
		return str.replaceAll("[^a-zA-Z0-9,.,*]", "_").toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(validateValue("19F6*6F"));
	}
	public static String validateFeetInch(String str) {
		String result = "";
		if (str.contains("F")) {
			String[] array = str.split("F");
			double fm = 0;
			double fi = 0;
			if (array.length > 0) {
				fm = feetToMeter(Double.parseDouble(array[0]));
				if (array.length == 2) {
					fi = inchToMeter(Double.parseDouble(array[1]));
				}
			}
			BigDecimal r = BigDecimal.valueOf((fm + fi));
			r=r.setScale(2,BigDecimal.ROUND_HALF_EVEN);
			result=r.toString();
		} else {
			result = str;
		}

		return result;
	}

	public static double feetToMeter(double feet) {
		BigDecimal result = BigDecimal.ZERO;
		result = BigDecimal.valueOf(feet * 0.3048);
		return result.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

	public static double inchToMeter(double inch) {
		BigDecimal result = BigDecimal.ZERO;
		result = BigDecimal.valueOf(inch * 0.0254);
		return result.setScale(2,BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}

	public static void processSetBackLeft(String cellValue, SetBack setBack) {

		String[] strings = cellValue.split(Pattern.quote("*"));

		try {
			String strLeftWidth = strings[0];
			String strLeftDepth = strings[1];

			BigDecimal leftArea = new BigDecimal(Double.parseDouble(strLeftWidth) * Double.parseDouble(strLeftDepth));

			setBack.setMinimumPermissibleSetback_left_Width(strLeftWidth);
			setBack.setMinimumPermissibleSetback_left_Depth(strLeftDepth);
			setBack.setMinimumPermissibleSetback_left_Area(leftArea.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());

		} catch (Exception e) {
			System.out.println("processSetBackLeft :: number :- " + cellValue);
			e.printStackTrace();
		}

	}

	public static void processSetBackRight(String cellValue, SetBack setBack) {

		String[] strings = cellValue.split(Pattern.quote("*"));

		try {
			String strRightWidth = strings[0];
			String strRightDepth = strings[1];

			BigDecimal rightArea = new BigDecimal(
					Double.parseDouble(strRightWidth) * Double.parseDouble(strRightDepth));

			setBack.setMinimumPermissibleSetback_Right_Width(strRightWidth);
			setBack.setMinimumPermissibleSetback_Right_Depth(strRightDepth);
			setBack.setMinimumPermissibleSetback_Right_Area(rightArea.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());

		} catch (Exception e) {
			System.out.println("processSetBackLeft :: number :- " + cellValue);
			e.printStackTrace();
		}

	}

//	public static String getString1(String str) {
//		return str.replaceAll("[^a-zA-Z0-9,.,*]", "").toUpperCase();
//	}
//
//	public static String getString(String str) {
//		return str.replaceAll("[^a-zA-Z0-9,.,*]", "_").toUpperCase();
//	}
//
//	public static String getNumber(String str) {
//		return str.replaceAll("[^0-9,.,*]", "").toUpperCase();
//	}

}
