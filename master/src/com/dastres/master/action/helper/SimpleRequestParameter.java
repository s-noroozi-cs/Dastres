package com.dastres.master.action.helper;

import org.apache.commons.fileupload.FileItem;

public class SimpleRequestParameter implements RequestParameter {
	private String name;
	private String value;

	public SimpleRequestParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public boolean isFormField() {
		return true;
	}

	public FileItem getFileItem() {
		return null;
	}

	public String getFieldName() {
		return name;
	}

	public String getFieldValue() {
		return value;
	}
}