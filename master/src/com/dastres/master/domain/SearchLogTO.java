package com.dastres.master.domain;

import java.sql.Timestamp;

import com.dastres.domain.EntityTO;

public class SearchLogTO extends EntityTO{
	
	private static final long serialVersionUID = 1L;
	
	private String keyword;
	private String region;
	private String userAgent;
	private long waitTime;
	private Timestamp logDate;

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}

	public Timestamp getLogDate() {
		return logDate;
	}

}
