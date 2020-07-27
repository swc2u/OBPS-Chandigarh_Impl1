package org.egov.collection.cdg.finance.model;

import java.io.Serializable;

public class Fund implements Serializable {

	private Long id;
	private String name;
	private String code;
	private Character identifier;
	private String level;
	private String parentId;
	private Boolean isParent;
	private Boolean active;

	public Character getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Character identifier) {
		this.identifier = identifier;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
