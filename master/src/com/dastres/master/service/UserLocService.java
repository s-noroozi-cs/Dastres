package com.dastres.master.service;

import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.domain.UserLocationInfo;
import com.dastres.master.exception.ServiceException;

public interface UserLocService {
	List<UserLocationInfo> list(int from, int to, String orderField,
			boolean orderAsc) throws ServiceException;

	List<LocationInfo> accept(List<Integer> ids) throws ServiceException;
	
	void remove(List<Integer> ids) throws ServiceException;

	int countUserLoc() throws ServiceException;

	void add(UserLocationInfo... locInfos) throws ServiceException;
}
