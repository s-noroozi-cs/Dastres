package com.dastres.slave.rsws.types.output;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dastres.rsws.types.output.ResultTO;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StorageStatisticTO extends ResultTO{
	private Integer recordCount;
	private String storageSignature; 

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setStorageSignature(String storageSignature) {
		this.storageSignature = storageSignature;
	}

	public String getStorageSignature() {
		return storageSignature;
	}
	

}
