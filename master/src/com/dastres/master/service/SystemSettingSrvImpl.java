package com.dastres.master.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import com.dastres.config.Constant;
import com.dastres.master.config.DefaultValues;
import com.dastres.master.domain.ConfigTO;
import com.dastres.master.exception.DAOException;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.DAOFactory;
import com.dastres.master.schedule.CommonJobTask;
import com.dastres.master.schedule.PingTimeJob;
import com.dastres.master.schedule.ScheduleManager;
import com.dastres.util.CacheManagement;
import com.dastres.util.NumberUtil;

public class SystemSettingSrvImpl implements SystemSettingService {

	private Logger logger = Logger.getLogger(this.getClass());

	private void put2Cache(String name, ConfigTO configTO) {

		CacheManagement.getSystemSettingCache().put(name, configTO);

	}

	private ConfigTO getFromCache(String name) {
		return (ConfigTO) CacheManagement.getSystemSettingCache().get(name);
	}

	private ConfigTO getConfig(String name) throws ServiceException {
		return getConfig(name, false);
	}

	private ConfigTO getConfig(String name, boolean ignoreCache)
			throws ServiceException {

		ConfigTO configTO = ignoreCache ? null : getFromCache(name);

		if (configTO == null) {

			try {
				configTO = DAOFactory.getSystemSettingDAO().find(
						new ConfigTO(name));
			} catch (DAOException e) {
				logger.error(e.getMessage(), e);
				throw new ServiceException(e.getMessage(), e);
			}

			if (configTO != null) {
				put2Cache(configTO.getName(), configTO);
			}
		}

		return configTO;
	}

	private void setConfig(ConfigTO configTO) throws ServiceException {

		try {
			logger.debug(configTO);
			DAOFactory.getSystemSettingDAO().save(configTO);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		put2Cache(configTO.getName(), configTO);

	}

	@Override
	public int getSlavePingTime() throws ServiceException {
		int slavePingTime = DefaultValues.SYS_CFG_SLV_PING_TIME;

		ConfigTO cfgTO = getConfig(Constant.CONFIG_SYS_SLV_PING_TIME);
		if (cfgTO != null) {
			slavePingTime = NumberUtil.cast2Int(cfgTO.getValue(),
					DefaultValues.SYS_CFG_SLV_PING_TIME);
		}

		return slavePingTime;
	}

	@Override
	public void setSlavePingTime(int slavePingTime) throws ServiceException {
		if (NumberUtil.isPositive(slavePingTime)) {
			ConfigTO configTO = new ConfigTO(Constant.CONFIG_SYS_SLV_PING_TIME,
					String.valueOf(slavePingTime));
			setConfig(configTO);

			List<CommonJobTask> jobs = new ArrayList<CommonJobTask>();
			jobs.add(new PingTimeJob());
			try {
				ScheduleManager.reScheduleJobs(jobs);
			} catch (SchedulerException e) {
				throw new ServiceException(e.getMessage(), e);
			}
		} else {
			throw new ServiceException("slave ping time is not positive number");
		}
	}

	@Override
	public String getUserCredential(String user) throws ServiceException {
		return getConfig(user, true).getValue();
	}

	@Override
	public void setResponseRule(String responseRule) throws ServiceException {
		
//		new NotifyTrustedHostThread(responseRule).start();
		
		ConfigTO cfg = new ConfigTO();
		cfg.setName(Constant.CONFIG_SYS_RESPONSE_RULE);
		cfg.setValue(String.valueOf(responseRule));
		setConfig(cfg);
	}

	@Override
	public String getResponseRule() throws ServiceException {
		String result = DefaultValues.SYS_CFG_RESPONSE_RULE;
		ConfigTO cfg = getConfig(Constant.CONFIG_SYS_RESPONSE_RULE);
		if (cfg != null) {
			result = cfg.getValue();
		}
		return result;
	}
}
