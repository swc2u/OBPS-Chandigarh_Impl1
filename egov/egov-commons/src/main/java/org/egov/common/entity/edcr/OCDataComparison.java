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
	public static final String Barsati_Floor = "Barsati floor height";
	public static final String Stair_Headway_Height = "Stair headway height";
	public static final String Terrace_Utilities = "Terrace utilities(water tank, solar panel, etc)";
	public static final String Minor_Changes_In_Doors_And_Windows = "Minor changes in - doors and windows (Number of floors)";
	public static final String Number_Of_Lofts = "Number of Lofts";
	public static final String Number_Of_Non_Standard_Gate = "Number of Non Standard Gate";
	public static final String Number_Of_Niches_On_Common_Wall = "Number of Niches on the common wall";
	public static final String Area_Of_False_Ceiling = "False ceiling";
	
	
	
	

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

}
