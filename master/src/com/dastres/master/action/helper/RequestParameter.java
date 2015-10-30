package com.dastres.master.action.helper;

import org.apache.commons.fileupload.FileItem;

public interface RequestParameter {
	String getFieldName();

	String getFieldValue();

	boolean isFormField();

	FileItem getFileItem();
}
