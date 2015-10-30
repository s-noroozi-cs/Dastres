package com.dastres.master.service;

import java.util.List;

import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.ServiceException;

public interface SlaveManagementService {

	boolean setSlaveTO(SlaveTO... slaveTO) throws ServiceException;
	
	boolean remove(int slaveId) throws ServiceException;
	
	boolean reload(int... slaveId) throws ServiceException;
	
	void reload() throws ServiceException;

	List<SlaveTO> getAllSlaveTO() throws ServiceException;
	
	List<SlaveTO> getActiveSlaveTO() throws ServiceException;

}
