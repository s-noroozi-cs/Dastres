package com.dastres.slave.service;

import com.dastres.slave.exception.ServiceException;

public interface ResourceService {
	
	java.sql.Connection getConnection()throws ServiceException;

}
