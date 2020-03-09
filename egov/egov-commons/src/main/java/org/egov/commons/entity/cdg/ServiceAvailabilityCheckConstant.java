package org.egov.commons.entity.cdg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceAvailabilityCheckConstant implements Serializable {

	/**
	 * 
	 */

	// CGCL START
	public static final String URBAN = "URBAN";
	public static final String RURAL = "RURAL";
	public static final String CENTER = "CENTER";
	public static final String EAST = "EAST";
	public static final String SOUTH = "SOUTH";
	public static final String MARLA = "MARLA";
	public static final String ONE_KANAL = "ONE_KANAL";
	public static final String TWO_KANAL = "TWO_KANAL";
	// CGCL END

	private static final long serialVersionUID = 1L;

	public static List<String> ROOT_BOUNDARY_TYPES = new ArrayList<String>();
	public static List<String> ZONES = new ArrayList<String>();
	public static List<String> SECTORS = new ArrayList<String>();
	public static List<String> PLOT_AREA_TYPES = new ArrayList<String>();
	
	

	static {
		ROOT_BOUNDARY_TYPES.add(URBAN);
		ROOT_BOUNDARY_TYPES.add(RURAL);
		
		ZONES.add(CENTER);
		ZONES.add(EAST);
		ZONES.add(CENTER);
		
		PLOT_AREA_TYPES.add(MARLA);
		PLOT_AREA_TYPES.add(ONE_KANAL);
		PLOT_AREA_TYPES.add(TWO_KANAL);
	}

}
