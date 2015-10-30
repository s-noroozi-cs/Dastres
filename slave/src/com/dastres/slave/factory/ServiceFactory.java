package com.dastres.slave.factory;

import com.dastres.slave.exception.ServiceException;
import com.dastres.slave.service.ResourceService;
import com.dastres.slave.service.ResourceServiceImpl;
import com.dastres.slave.service.SearchService;
import com.dastres.slave.service.SearchServiceImpl;
import com.dastres.slave.service.StorageService;
import com.dastres.slave.service.StorageServiceImpl;
import com.dastres.slave.service.TrustedHostService;
import com.dastres.slave.service.TrustedHostSrvImpl;

public class ServiceFactory {

	private static final SearchService searchService = new SearchServiceImpl();
	private static final StorageService storageService = new StorageServiceImpl();
	private static final ResourceService resourceService = new ResourceServiceImpl();
	private static final TrustedHostService trustedHostSrv = new TrustedHostSrvImpl();

	public static TrustedHostService getTrustedHostService()
			throws ServiceException {
		return trustedHostSrv;
	}

	public static SearchService getSearchService() throws ServiceException {
		return searchService;
	}

	public static StorageService getStorageService() throws ServiceException {
		return storageService;
	}

	public static ResourceService getResourceService() throws ServiceException {
		return resourceService;
	}

}
