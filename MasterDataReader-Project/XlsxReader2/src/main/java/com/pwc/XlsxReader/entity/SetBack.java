package com.pwc.XlsxReader.entity;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class SetBack {

	public static final String FRONT_SUFFIX = "Front";
	public static final String REAR_SUFFIX = "Rear";
	public static final String LEFT_SUFFIX = "Left";
	public static final String RIGHT_SUFFIX = "Right";
	public static final String Area_SUFFIX = "Area";
	public static final String WIDTH_SUFFIX = "Width";
	public static final String DEPTH_SUFFIX = "Depth";
	public static final String SETBACK_PREFIX = "setBack";

	private String keyFront;
	private String keyRear;
	private String keyLeft;
	private String keyRight;
	private String keyLeftArea;
	private String keyLeftWidth;
	private String keyLeftDepth;
	private String keyRightArea;
	private String keyRightWidth;
	private String keyRightDepth;

	private String minimumPermissibleSetback_Front;
	private String minimumPermissibleSetback_Rear;
	private String minimumPermissibleSetback_left;
	private String minimumPermissibleSetback_Right;
	private String minimumPermissibleSetback_left_Width;
	private String minimumPermissibleSetback_Right_Width;
	private String minimumPermissibleSetback_left_Depth;
	private String minimumPermissibleSetback_Right_Depth;
	private String minimumPermissibleSetback_left_Area;
	private String minimumPermissibleSetback_Right_Area;

	public String getKeyFront() {
		return keyFront;
	}

	public void setKeyFront(String keyFront) {
		this.keyFront = keyFront;
	}

	public String getKeyRear() {
		return keyRear;
	}

	public void setKeyRear(String keyRear) {
		this.keyRear = keyRear;
	}

	public String getKeyLeft() {
		return keyLeft;
	}

	public void setKeyLeft(String keyLeft) {
		this.keyLeft = keyLeft;
	}

	public String getKeyRight() {
		return keyRight;
	}

	public void setKeyRight(String keyRight) {
		this.keyRight = keyRight;
	}

	public String getKeyLeftArea() {
		return keyLeftArea;
	}

	public void setKeyLeftArea(String keyLeftArea) {
		this.keyLeftArea = keyLeftArea;
	}

	public String getKeyLeftWidth() {
		return keyLeftWidth;
	}

	public void setKeyLeftWidth(String keyLeftWidth) {
		this.keyLeftWidth = keyLeftWidth;
	}

	public String getKeyLeftDepth() {
		return keyLeftDepth;
	}

	public void setKeyLeftDepth(String keyLeftDepth) {
		this.keyLeftDepth = keyLeftDepth;
	}

	public String getKeyRightArea() {
		return keyRightArea;
	}

	public void setKeyRightArea(String keyRightArea) {
		this.keyRightArea = keyRightArea;
	}

	public String getKeyRightWidth() {
		return keyRightWidth;
	}

	public void setKeyRightWidth(String keyRightWidth) {
		this.keyRightWidth = keyRightWidth;
	}

	public String getKeyRightDepth() {
		return keyRightDepth;
	}

	public void setKeyRightDepth(String keyRightDepth) {
		this.keyRightDepth = keyRightDepth;
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

	public String getMinimumPermissibleSetback_left_Width() {
		return minimumPermissibleSetback_left_Width;
	}

	public void setMinimumPermissibleSetback_left_Width(String minimumPermissibleSetback_left_Width) {
		this.minimumPermissibleSetback_left_Width = minimumPermissibleSetback_left_Width;
	}

	public String getMinimumPermissibleSetback_Right_Width() {
		return minimumPermissibleSetback_Right_Width;
	}

	public void setMinimumPermissibleSetback_Right_Width(String minimumPermissibleSetback_Right_Width) {
		this.minimumPermissibleSetback_Right_Width = minimumPermissibleSetback_Right_Width;
	}

	public String getMinimumPermissibleSetback_left_Depth() {
		return minimumPermissibleSetback_left_Depth;
	}

	public void setMinimumPermissibleSetback_left_Depth(String minimumPermissibleSetback_left_Depth) {
		this.minimumPermissibleSetback_left_Depth = minimumPermissibleSetback_left_Depth;
	}

	public String getMinimumPermissibleSetback_Right_Depth() {
		return minimumPermissibleSetback_Right_Depth;
	}

	public void setMinimumPermissibleSetback_Right_Depth(String minimumPermissibleSetback_Right_Depth) {
		this.minimumPermissibleSetback_Right_Depth = minimumPermissibleSetback_Right_Depth;
	}

	public String getMinimumPermissibleSetback_left_Area() {
		return minimumPermissibleSetback_left_Area;
	}

	public void setMinimumPermissibleSetback_left_Area(String minimumPermissibleSetback_left_Area) {
		this.minimumPermissibleSetback_left_Area = minimumPermissibleSetback_left_Area;
	}

	public String getMinimumPermissibleSetback_Right_Area() {
		return minimumPermissibleSetback_Right_Area;
	}

	public void setMinimumPermissibleSetback_Right_Area(String minimumPermissibleSetback_Right_Area) {
		this.minimumPermissibleSetback_Right_Area = minimumPermissibleSetback_Right_Area;
	}

	@Override
	public String toString() {
		return "SetBack [keyFront=" + keyFront + ", keyRear=" + keyRear + ", keyLeft=" + keyLeft + ", keyRight="
				+ keyRight + ", keyLeftArea=" + keyLeftArea + ", keyLeftWidth=" + keyLeftWidth + ", keyLeftDepth="
				+ keyLeftDepth + ", keyRightArea=" + keyRightArea + ", keyRightWidth=" + keyRightWidth
				+ ", keyRightDepth=" + keyRightDepth + ", minimumPermissibleSetback_Front="
				+ minimumPermissibleSetback_Front + ", minimumPermissibleSetback_Rear=" + minimumPermissibleSetback_Rear
				+ ", minimumPermissibleSetback_left=" + minimumPermissibleSetback_left
				+ ", minimumPermissibleSetback_Right=" + minimumPermissibleSetback_Right
				+ ", minimumPermissibleSetback_left_Width=" + minimumPermissibleSetback_left_Width
				+ ", minimumPermissibleSetback_Right_Width=" + minimumPermissibleSetback_Right_Width
				+ ", minimumPermissibleSetback_left_Depth=" + minimumPermissibleSetback_left_Depth
				+ ", minimumPermissibleSetback_Right_Depth=" + minimumPermissibleSetback_Right_Depth
				+ ", minimumPermissibleSetback_left_Area=" + minimumPermissibleSetback_left_Area
				+ ", minimumPermissibleSetback_Right_Area=" + minimumPermissibleSetback_Right_Area + "]";
	}
	
	
}
