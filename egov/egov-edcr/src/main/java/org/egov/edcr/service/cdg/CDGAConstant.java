package org.egov.edcr.service.cdg;

public enum CDGAConstant {
	
	//BALCONY("Balcony"),BASEMENT("Basement"),FAR("far"),SETBACKS("setBack"),PERMISSIBLE_BUILDING_HEIGHT("pbh"),NO_OF_STORY("nos"),BACK_YARD_CONSTRUCTION("byc");
	
	TOILET("toilet"), KITCHEN("kitchen"), HABITABLE_ROOM("hr"), HIGHT("hight"), BALCONY("balcony"),
	GROUND_COVERAGE("gc"), PLINTH_LEVEL("pl"), BASEMENT("Basement"), FAR("far"), SETBACKS("setBack"),
	PERMISSIBLE_BUILDING_HEIGHT("pbh"), NO_OF_STORY("nos"), BACK_YARD_CONSTRUCTION("byc"), WC_AND_POWER_ROOM("wc"),
	BATH("bath"), LIGHT_AND_VENTILATION("lav"), VENTILATION_SHAFT("vs"),
	INTERIOR_COURTYARD_FOR_LIGHT_AND_VENTILATION("icflav"), VERANDAH_FOR_LIGHT_AND_VENTILATION("vflav"),
	STAIRCASE("staircase"), CONSTRUCTION_IN_BACK_COURTYARD("cibc"), LIFT("lift"), MUMTY("mumty"),
	SERVICE_ZONE_ON_TERRACE("szot"), GATE("gate"), BOUNDARY_WALL("bw"), RAMP("ramp"), PARKING("parking"),
	PROJECTION_BALCONY("pb"), RAIN_WATER_HERVESTING("rwh"), SOLAR_WATER_HEATING_SYSTEM("swhs"),
	SOLAR_PHOTO_VOLTAIC("spv"), FLUSHING_SYSTEM("fs"), PARAPET("parapet"), MINIMUN_PASSAGE("mp"), GARAGE("garage"),
	SERVANT_QUARTER("sq"), WATER_TANK("wt"), PROFESSIONALS_CONSULTANTS_SPACE("pcs"), STD_PCO("std"),
	PAYING_GUEST("pgf"), STLLT_PARKING("sp"), CHHAJJA_OR_JAMBS("coj"), ORGANIZED_GREEN_PARKS("ogp"), EWS("ews"),
	AMAIGAMATION("Amalgamation"), SUB_DIVISION("Sub-division"), MEZZANINE_FLOOR("Mezzanine-floor"),
	COMMERCAIL_OR_COMMUNLTY_FACLTITIES("Commercial-or-community-facitities"), DWELLING_UNITS("Dwelling-units"),
	DG_SET("DG-set"), LIGHT_AND_VENTILATION_IN_BASEMENT("Light-and-Ventilation-in-Basement"), CHECKPOST("Checkpost"),
	SEATING_CAPACITY("Seating-Capacity"), COMMERCIAL_AREAS("Commercial-areas"), RESIDENTIAL("Residential"),
	PANTRY("Pantry"), ANCLLARY_FACIITIES("Ancillary-facilities"),COMPOUND_WALL_SERVICE("cws");
	
	
    private final String occupancyTypeVal;

	CDGAConstant(String aTypeVal) {
        this.occupancyTypeVal = aTypeVal;
    }

    public String getCDGAConstant() {
        return occupancyTypeVal;
    }

    public String getCDGAConstantValue() {
        return occupancyTypeVal;
    }

}
