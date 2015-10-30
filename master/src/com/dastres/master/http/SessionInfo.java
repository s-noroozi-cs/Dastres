package com.dastres.master.http;

public class SessionInfo {

	private String token;
	private String ip;

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token == null ? "" : token;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip == null ? "" : ip;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof SessionInfo) {
			SessionInfo sessionInfo = (SessionInfo) obj;
			return this.getIp().equals(sessionInfo.getIp())
					&& this.getToken().equals(sessionInfo.getToken());
		}
		return false;
	}
}
