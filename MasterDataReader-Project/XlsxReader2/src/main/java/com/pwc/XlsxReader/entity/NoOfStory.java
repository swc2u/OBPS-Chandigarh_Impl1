package com.pwc.XlsxReader.entity;

public class NoOfStory {

	public static final String NO_OF_STORY_PREFIX="nos";
	private String key;
	
	private String PermissibleBuildingStories;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPermissibleBuildingStories() {
		return PermissibleBuildingStories;
	}

	public void setPermissibleBuildingStories(String permissibleBuildingStories) {
		PermissibleBuildingStories = permissibleBuildingStories;
	}

	
}