package com.dastres.slave.factory;

import com.dastres.slave.dao.LocationDAO;
import com.dastres.slave.dao.LocationDAOImpl;
import com.dastres.slave.dao.TrustedHostDAO;
import com.dastres.slave.dao.TrustedHostDAOImpl;
import com.dastres.slave.exception.DAOException;

public class DAOFactory {
	private final static LocationDAO locationDAO = new LocationDAOImpl();
	private final static TrustedHostDAO trustedHostDAO = new TrustedHostDAOImpl();

	public static LocationDAO getLocationDAO() throws DAOException {
		return locationDAO;
	}

	public static TrustedHostDAO getTrustedHostDAO() throws DAOException {
		return trustedHostDAO;
	}

}
