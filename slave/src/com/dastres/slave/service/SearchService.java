package com.dastres.slave.service;

import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.slave.domain.LocationSearchTO;
import com.dastres.slave.exception.ServiceException;

public interface SearchService {

	List<LocationInfo> findLocation(LocationSearchTO searchTO)
			throws ServiceException;
}
