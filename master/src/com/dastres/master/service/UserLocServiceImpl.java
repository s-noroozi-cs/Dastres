package com.dastres.master.service;

import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.domain.UserLocationInfo;
import com.dastres.master.exception.DAOException;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.DAOFactory;

public class UserLocServiceImpl implements UserLocService {

	@Override
	public int countUserLoc() throws ServiceException {
		try {
			return DAOFactory.getUserLocDAO().countUserLoc();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<UserLocationInfo> list(int from, int to, String orderField,
			boolean orderAsc) throws ServiceException {
		try {
			return DAOFactory.getUserLocDAO().list(from, to, orderField,
					orderAsc);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void add(UserLocationInfo... locInfos) throws ServiceException {
		try {
			DAOFactory.getUserLocDAO().add(locInfos);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<LocationInfo> accept(List<Integer> ids)
			throws ServiceException {
		try {
			return DAOFactory.getUserLocDAO().accept(ids);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void remove(List<Integer> ids) throws ServiceException {
		try {
			DAOFactory.getUserLocDAO().remove(ids);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
