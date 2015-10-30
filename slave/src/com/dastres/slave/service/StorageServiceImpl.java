package com.dastres.slave.service;

import java.util.Collection;
import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.slave.exception.DAOException;
import com.dastres.slave.exception.ServiceException;
import com.dastres.slave.factory.DAOFactory;
import com.dastres.rsws.types.input.TransferParamTO;

public class StorageServiceImpl implements StorageService {

	@Override
	public void add(List<LocationInfo> locInfos) throws ServiceException {
		try {
			DAOFactory.getLocationDAO().add(locInfos);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public int getRecordCount() throws ServiceException {
		try {
			return DAOFactory.getLocationDAO().countRecord();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void remove(TransferParamTO transferTO) throws ServiceException {
		try {
			DAOFactory.getLocationDAO().remove(transferTO);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Collection<LocationInfo> getTransferList(TransferParamTO transferTO)
			throws ServiceException {
		try {
			return DAOFactory.getLocationDAO().list(transferTO);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
