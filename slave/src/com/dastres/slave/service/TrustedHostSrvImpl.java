package com.dastres.slave.service;

import java.util.HashSet;
import java.util.Set;

import com.dastres.slave.exception.DAOException;
import com.dastres.slave.exception.ServiceException;
import com.dastres.slave.factory.DAOFactory;
import com.dastres.util.CacheManagement;

public class TrustedHostSrvImpl implements TrustedHostService {
	private final String CACHE_KEY_TRUST_LIST = "CACHE_KEY_TRUST_LIST";

	@SuppressWarnings("unchecked")
	@Override
	public boolean isTrusted(String host) throws ServiceException {
		boolean result = false;

		Set<String> hostSet = (Set<String>) CacheManagement
				.getTrustedHostCache().get(CACHE_KEY_TRUST_LIST);
		
		if (hostSet == null) {
			try {
				
				hostSet = DAOFactory.getTrustedHostDAO().list();

			} catch (DAOException e) {
				e.printStackTrace();
			}
		}

		if (hostSet != null) {
			result = hostSet.contains(host);

			CacheManagement.getTrustedHostCache().put(CACHE_KEY_TRUST_LIST,
					hostSet);

		}

		return result;
	}

	@Override
	public void set(String... hosts) throws ServiceException {
		try {

			Set<String> hostSet = new HashSet<String>();
			for (String host : hosts) {
				hostSet.add(host.trim());
			}

			DAOFactory.getTrustedHostDAO().save(hostSet);

			CacheManagement.getTrustedHostCache().clear();

			CacheManagement.getTrustedHostCache().put(CACHE_KEY_TRUST_LIST,
					hostSet);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}
