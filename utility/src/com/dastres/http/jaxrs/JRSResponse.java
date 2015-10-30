package com.dastres.http.jaxrs;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JRSResponse {

	private String response;
	private long timeDelay;
	private JSONObject jsResp;

	public void setResponse(String response) {
		this.response = response;
		try {
			jsResp = new JSONObject(response);
		} catch (JSONException e) {
			jsResp = new JSONObject();
		}
	}

	public String getResponse() {
		return response;
	}

	public int getValueAsInt(String key) {
		if (jsResp.has(key)) {
			try {
				return jsResp.getInt(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public boolean getValueAsBoolean(String key) {
		if (jsResp.has(key)) {
			try {
				return jsResp.getBoolean(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setTimeDelay(long timeDelay) {
		this.timeDelay = timeDelay;
	}

	public long getTimeDelay() {
		return timeDelay;
	}

}
