package org.egov.common.entity.edcr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class OCDataComparison implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String Additional_fee_Residential_plotted = "Additional fee Residential plotted";
	public static final String Labour_Cess = "Labour cess";
	public static final String RULE5 = "Rule5";
	public static final String Additional_Coverage_In_RearCourtyard = "Additional Coverage In RearCourtyard";
	public static final String Minor_Internal_Changes_During_Construction = "Minor internal changes during construction";
	public static final String Excess_Coverage_Beyond_Zoning_6_INCH = "Excess coverage beyond zoning 6'";
	public static final String Glazing_Of_Verandah = "Glazing of verandah";
	public static final String Additional_height_on_second_floor_of_SCFs_converted_into_SCOs = "Additional height on second floor of SCFs converted into SCOs";
	public static final String Partitions_on_ground_floor_on_multi_bays_shops = "Partitions on ground floor on multi-bays shops";

	//@JsonProperty(value = "oCDataComparison")
	private Map<String, Data> ocdataComparison = new HashMap<String, Data>();
	
	public OCDataComparison() {
		
	}

	public Data getData(String key) {
		return ocdataComparison.get(key);
	}

	public void addData(String key, Data data) {
		ocdataComparison.put(key, data);
	}
	
	public Map<String, Data> getOcdataComparison() {
		return ocdataComparison;
	}

	public void setOcdataComparison(Map<String, Data> ocdataComparison) {
		this.ocdataComparison = ocdataComparison;
	}


	public static class Data {
		private BigDecimal oc;
		private BigDecimal permit;
		private BigDecimal deviation;

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

}
