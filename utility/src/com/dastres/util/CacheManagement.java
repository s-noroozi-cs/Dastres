package com.dastres.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CacheManagement {

	private static final String CACHE_NAME_SYS_SETTING = "SYSTEM_SETTING_CACHE";
	private static final String CACHE_NAME_SLAVE_MACHINES = "SLAVE_MACHINE_CACHE";
	private static final String CACHE_NAME_TRUSTED_HOST = "TRUSTED_HOST_CACHE";

	private static Set<String> cacheNames = new HashSet<String>();
	private static Map<String, Object> systemSettingCache = initCache(CACHE_NAME_SYS_SETTING);
	private static Map<String, Object> slaveMachineCache = initCache(CACHE_NAME_SLAVE_MACHINES);
	private static Map<String, Object> trustedHostCache = initCache(CACHE_NAME_TRUSTED_HOST);

	private static Map<String, Object> initCache(String cacheName) {
		cacheNames.add(cacheName);
		return new HashMap<String, Object>();
	}

	public static Map<String, Object> getSystemSettingCache() {
		return systemSettingCache;
	}

	public static Map<String, Object> getSlaveMachineCache() {
		return slaveMachineCache;
	}
	
	public static Map<String, Object> getTrustedHostCache() {
		return trustedHostCache;
	}

	public Set<String> getAllCacheName() {
		return cacheNames;
	}

	public void clearCache(String cacheName) {
		CacheManagement.initCache(cacheName).clear();
	}

	public void clearCache() {
		for (String cacheName : cacheNames) {
			CacheManagement.initCache(cacheName).clear();
		}
	}

}
