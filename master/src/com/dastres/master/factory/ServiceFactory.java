package com.dastres.master.factory;

import com.dastres.master.service.LogService;
import com.dastres.master.service.LogServiceImpl;
import com.dastres.master.service.ResourceService;
import com.dastres.master.service.ResourceServiceImpl;
import com.dastres.master.service.SlaveManagementService;
import com.dastres.master.service.SlaveManagementSrvImpl;
import com.dastres.master.service.SystemSettingService;
import com.dastres.master.service.SystemSettingSrvImpl;
import com.dastres.master.service.UserLocService;
import com.dastres.master.service.UserLocServiceImpl;

public class ServiceFactory {

	private static final SystemSettingService sysSettingInstance = new SystemSettingSrvImpl();

	private static final SlaveManagementService slaveManageInstance = new SlaveManagementSrvImpl();

	private static final ResourceService resourceService = new ResourceServiceImpl();

	private static final LogService logService = new LogServiceImpl();

	private static final UserLocService userLocService = new UserLocServiceImpl();

	public static SystemSettingService getSystemSettingService() {
		return sysSettingInstance;
	}

	public static SlaveManagementService getSlaveManagementService() {
		return slaveManageInstance;
	}

	public static ResourceService getResourceService() {
		return resourceService;
	}

	public static LogService getLogService() {
		return logService;
	}

	public static UserLocService getUserLocService() {
		return userLocService;
	}
}
