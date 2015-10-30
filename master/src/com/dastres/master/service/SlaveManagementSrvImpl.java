package com.dastres.master.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.DAOException;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.DAOFactory;
import com.dastres.master.factory.JRSFactory;
import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.http.jaxrs.JRSResponse;
import com.dastres.master.http.jaxrs.JRSResponseHelper;
import com.dastres.util.CacheManagement;
import com.dastres.util.CollectionUtil;

public class SlaveManagementSrvImpl implements SlaveManagementService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean remove(int slaveId) throws ServiceException {
		logger.info("slave ID: " + slaveId);
		boolean result = false;
		try {
			result = DAOFactory.getslaveManagementDAOInstace().remove(slaveId);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}

		clearCache();

		return result;
	}

	private void clearCache() {
		logger.info("clear SlaveMachineCache cache");
		CacheManagement.getSlaveMachineCache().clear();
	}

	private void put2Cache(String key, List<SlaveTO> retList) {
		if (retList != null && !retList.isEmpty()) {
			logger.info("key: " + key + ", list size: " + retList.size());
			CacheManagement.getSlaveMachineCache().put(key, retList);
		}
	}

	@SuppressWarnings("unchecked")
	private List<SlaveTO> getFromCache(String key) {
		logger.info("key: " + key);
		Object value = CacheManagement.getSlaveMachineCache().get(key);
		if (value != null && value instanceof List) {
			return (List<SlaveTO>) value;
		}
		return null;
	}

	@Override
	public boolean setSlaveTO(SlaveTO... slaveTOs) throws ServiceException {
		boolean result = false;
		try {
			result = DAOFactory.getslaveManagementDAOInstace().save(slaveTOs);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}

		clearCache();

		return result;

	}

	@Override
	public List<SlaveTO> getAllSlaveTO() throws ServiceException {
		final String CACHE_KEY = "SLV_LIST_KEY";
		List<SlaveTO> retList = getFromCache(CACHE_KEY);
		try {
			if (CollectionUtil.isEmpty(retList)) {
				retList = DAOFactory.getslaveManagementDAOInstace().list();
				put2Cache(CACHE_KEY, retList);
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

		return retList;
	}

	@Override
	public List<SlaveTO> getActiveSlaveTO() throws ServiceException {
		final String CACHE_KEY = "SLV_ACT_LIST_KEY";
		List<SlaveTO> retList = getFromCache(CACHE_KEY);
		try {
			if (CollectionUtil.isEmpty(retList)) {
				retList = DAOFactory.getslaveManagementDAOInstace()
						.activeList();
				put2Cache(CACHE_KEY, retList);
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}

		return retList;
	}

	@Override
	public boolean reload(int... slaveIds) throws ServiceException {
		logger.info("slave ID: " + slaveIds);
		boolean result = false;
		try {

			for (SlaveTO slv : getActiveSlaveTO()) {
				for (int slaveId : slaveIds) {
					if (slv.getId() == slaveId) {
						JRSRequest request = JRSFactory
								.getRecordCountRequest(slv);
						JRSResponse response = JRSFactory.getJWSManager().send(
								request);
						JRSResponseHelper.processRecordCount(slv, response);
						setSlaveTO(slv);
					}
				}
			}
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return result;
	}

	@Override
	public void reload() throws ServiceException {
		try {
			for (SlaveTO slv : getActiveSlaveTO()) {
				JRSRequest request = JRSFactory.getRecordCountRequest(slv);
				JRSResponse response = JRSFactory.getJWSManager().send(request);
				JRSResponseHelper.processRecordCount(slv, response);
				setSlaveTO(slv);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
}