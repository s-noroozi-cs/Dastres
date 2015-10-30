package com.dastres.master.action.helper;

import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;

public class ComplexRequestParameter implements RequestParameter {

	private FileItem fileItem;

	public ComplexRequestParameter(FileItem fileItem) {
		this.fileItem = fileItem;
	}

	public FileItem getFileItem() {
		return this.fileItem;
	}

	public boolean isFormField() {
		return this.fileItem.isFormField();
	}

	public String getFieldName() {
		return fileItem.getFieldName();
	}

	public String getFieldValue() {
		try {
			return this.fileItem.getString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return this.fileItem.getString();
		}

	}

}
