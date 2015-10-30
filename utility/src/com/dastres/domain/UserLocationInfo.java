package com.dastres.domain;

import java.util.Date;

public class UserLocationInfo extends LocationInfo{
	private static final long serialVersionUID = 1L;
	
	private Date date;
	private String accept;
	
	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAccept() {
		return accept;
	}

}
