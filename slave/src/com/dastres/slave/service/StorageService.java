package com.dastres.slave.service;

import java.util.Collection;
import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.slave.exception.ServiceException;
import com.dastres.rsws.types.input.TransferParamTO;

public interface StorageService {

	int getRecordCount() throws ServiceException;

	void add(List<LocationInfo> locInfos) throws ServiceException;
	
	void remove(TransferParamTO transferTO)throws ServiceException;
	
	Collection<LocationInfo> getTransferList(TransferParamTO transferTO) throws ServiceException;

}
