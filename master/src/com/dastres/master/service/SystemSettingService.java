package com.dastres.master.service;

import com.dastres.master.exception.ServiceException;

public interface SystemSettingService {

	int getSlavePingTime() throws ServiceException;

	void setSlavePingTime(int slavePingTime) throws ServiceException;

	void setResponseRule(String responseRule) throws ServiceException;

	String getResponseRule() throws ServiceException;

	String getUserCredential(String user) throws ServiceException;

}
