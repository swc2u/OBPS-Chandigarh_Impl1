package com.pwc.XlsxReader.entity;

public class Master {
	
	private String occupancyType;
	private String subOccupancyType;
	private String code;
	private String sector;
	private String plotNo;
	private String areaType;
	private String plotAreaInSqm;
	private String plotAreaInSqy;
	private String plotDepth;
	private String plotWidth;
	private String backCourtyardConstructionWidth;
	private String backCourtyardConstructionHeight;
	private String permissibleBuildingStories;
	private String permissibleBuildingHeight;
	private String maxmimumPermissibleFAR;
	private String minimumPermissibleSetback_Front;
	private String minimumPermissibleSetback_Rear;
	private String minimumPermissibleSetback_left;
	private String minimumPermissibleSetback_Right;
	private String key;
	
	
	
	
	public String getPermissibleBuildingHeight() {
		return permissibleBuildingHeight;
	}
	public void setPermissibleBuildingHeight(String permissibleBuildingHeight) {
		this.permissibleBuildingHeight = permissibleBuildingHeight;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPlotWidth() {
		return plotWidth;
	}
	public void setPlotWidth(String plotWidth) {
		this.plotWidth = plotWidth;
	}
	public String getPlotDepth() {
		return plotDepth;
	}
	public void setPlotDepth(String plotDepth) {
		this.plotDepth = plotDepth;
	}
	public String getPlotAreaInSqm() {
		return plotAreaInSqm;
	}
	public void setPlotAreaInSqm(String plotAreaInSqm) {
		this.plotAreaInSqm = plotAreaInSqm;
	}
	public String getPlotAreaInSqy() {
		return plotAreaInSqy;
	}
	public void setPlotAreaInSqy(String plotAreaInSqy) {
		this.plotAreaInSqy = plotAreaInSqy;
	}
	public String getOccupancyType() {
		return occupancyType;
	}
	public void setOccupancyType(String occupancyType) {
		this.occupancyType = occupancyType;
	}
	public String getSubOccupancyType() {
		return subOccupancyType;
	}
	public void setSubOccupancyType(String subOccupancyType) {
		this.subOccupancyType = subOccupancyType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getPlotNo() {
		return plotNo;
	}
	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	
	public String getBackCourtyardConstructionWidth() {
		return backCourtyardConstructionWidth;
	}
	public void setBackCourtyardConstructionWidth(String backCourtyardConstructionWidth) {
		this.backCourtyardConstructionWidth = backCourtyardConstructionWidth;
	}
	public String getBackCourtyardConstructionHeight() {
		return backCourtyardConstructionHeight;
	}
	public void setBackCourtyardConstructionHeight(String backCourtyardConstructionHeight) {
		this.backCourtyardConstructionHeight = backCourtyardConstructionHeight;
	}
	public String getPermissibleBuildingStories() {
		return permissibleBuildingStories;
	}
	public void setPermissibleBuildingStories(String permissibleBuildingStories) {
		this.permissibleBuildingStories = permissibleBuildingStories;
	}
	public String getMaxmimumPermissibleFAR() {
		return maxmimumPermissibleFAR;
	}
	public void setMaxmimumPermissibleFAR(String maxmimumPermissibleFAR) {
		this.maxmimumPermissibleFAR = maxmimumPermissibleFAR;
	}
	public String getMinimumPermissibleSetback_Front() {
		return minimumPermissibleSetback_Front;
	}
	public void setMinimumPermissibleSetback_Front(String minimumPermissibleSetback_Front) {
		this.minimumPermissibleSetback_Front = minimumPermissibleSetback_Front;
	}
	public String getMinimumPermissibleSetback_Rear() {
		return minimumPermissibleSetback_Rear;
	}
	public void setMinimumPermissibleSetback_Rear(String minimumPermissibleSetback_Rear) {
		this.minimumPermissibleSetback_Rear = minimumPermissibleSetback_Rear;
	}
	public String getMinimumPermissibleSetback_left() {
		return minimumPermissibleSetback_left;
	}
	public void setMinimumPermissibleSetback_left(String minimumPermissibleSetback_left) {
		this.minimumPermissibleSetback_left = minimumPermissibleSetback_left;
	}
	public String getMinimumPermissibleSetback_Right() {
		return minimumPermissibleSetback_Right;
	}
	public void setMinimumPermissibleSetback_Right(String minimumPermissibleSetback_Right) {
		this.minimumPermissibleSetback_Right = minimumPermissibleSetback_Right;
	}
	@Override
	public String toString() {
		return "Master [occupancyType=" + occupancyType + ", subOccupancyType=" + subOccupancyType + ", code=" + code
				+ ", sector=" + sector + ", plotNo=" + plotNo + ", areaType=" + areaType + ", plotAreaInSqm="
				+ plotAreaInSqm + ", plotAreaInSqy=" + plotAreaInSqy + ", plotDepth=" + plotDepth + ", plotWidth="
				+ plotWidth + ", backCourtyardConstructionWidth=" + backCourtyardConstructionWidth
				+ ", backCourtyardConstructionHeight=" + backCourtyardConstructionHeight
				+ ", permissibleBuildingStories=" + permissibleBuildingStories + ", permissibleBuildingHeight="
				+ permissibleBuildingHeight + ", maxmimumPermissibleFAR=" + maxmimumPermissibleFAR
				+ ", minimumPermissibleSetback_Front=" + minimumPermissibleSetback_Front
				+ ", minimumPermissibleSetback_Rear=" + minimumPermissibleSetback_Rear
				+ ", minimumPermissibleSetback_left=" + minimumPermissibleSetback_left
				+ ", minimumPermissibleSetback_Right=" + minimumPermissibleSetback_Right + ", key=" + key + "]";
	}

}
