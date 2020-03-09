package org.egov.edcr.service.cdg;

public enum CDGAConstant {
	
	BALCONY("Balcony"),BASEMENT("Basement"),FAR("far"),SETBACKS("setBack"),PERMISSIBLE_BUILDING_HEIGHT("pbh"),NO_OF_STORY("nos"),BACK_YARD_CONSTRUCTION("byc");
	
	
	
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
