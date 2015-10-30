package com.dastres.master.service;

import com.dastres.master.exception.ServiceException;

public interface ResourceService {
	
	java.sql.Connection getConnection()throws ServiceException;

}
