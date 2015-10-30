package com.dastres.master.factory;

import com.dastres.master.dao.LogDAO;
import com.dastres.master.dao.LogDAOImpl;
import com.dastres.master.dao.SlaveManagementDAO;
import com.dastres.master.dao.SlaveManagementDAOImpl;
import com.dastres.master.dao.SystemSettingDAO;
import com.dastres.master.dao.SystemSettingDAOImpl;
import com.dastres.master.dao.UserLocDAO;
import com.dastres.master.dao.UserLocDAOImpl;

public class DAOFactory {

	private static final SystemSettingDAO sysSettingDAOInstace = new SystemSettingDAOImpl();
	private static final SlaveManagementDAO slaveManagementDAOInstace = new SlaveManagementDAOImpl();
	private static final LogDAO logDAOImpl = new LogDAOImpl();
	private static final UserLocDAO userLocDAOImpl = new UserLocDAOImpl();

	public static SystemSettingDAO getSystemSettingDAO() {
		return sysSettingDAOInstace;
	}

	public static SlaveManagementDAO getslaveManagementDAOInstace() {
		return slaveManagementDAOInstace;
	}

	public static LogDAO getLogDAO() {
		return logDAOImpl;
	}

	public static UserLocDAO getUserLocDAO() {
		return userLocDAOImpl;
	}
}
