package com.pwc.XlsxReader.entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class BackYardConstruction {

	public static final String BACKCOURTYARDCONSTRUCTION_PREFIX = "byc";

	public static final String BACKCOURTYARDCONSTRUCTIONWIDTH_SUFFIX = "backCourtyardConstructionWidth";
	public static final String BACKCOURTYARDCONSTRUCTIONHIGHT_SUFFIX = "backCourtyardConstructionHeight";

	private String keyHight;
	private String keyWidth;

	private String backCourtyardConstructionWidth;
	private String backCourtyardConstructionHeight;

	public String getKeyHight() {
		return keyHight;
	}

	public void setKeyHight(String keyHight) {
		this.keyHight = keyHight;
	}

	public String getKeyWidth() {
		return keyWidth;
	}

	public void setKeyWidth(String keyWidth) {
		this.keyWidth = keyWidth;
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
}
