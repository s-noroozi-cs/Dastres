package com.dastres.slave.service;

import com.dastres.slave.exception.ServiceException;

public interface TrustedHostService {

	void set(String... hosts) throws ServiceException;

	boolean isTrusted(String host) throws ServiceException;

}
