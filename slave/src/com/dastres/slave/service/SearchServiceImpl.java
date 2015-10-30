package com.dastres.slave.service;

import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.slave.domain.LocationSearchTO;
import com.dastres.slave.exception.ServiceException;
import com.dastres.slave.factory.DAOFactory;

public class SearchServiceImpl implements SearchService {

	@Override
	public List<LocationInfo> findLocation(LocationSearchTO locSearchTO)
			throws ServiceException {
		try {
			return DAOFactory.getLocationDAO().find(locSearchTO);
		} catch (Throwable ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}
}
