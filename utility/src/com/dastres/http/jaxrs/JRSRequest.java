package com.dastres.http.jaxrs;

import java.util.HashMap;
import java.util.Map;

public class JRSRequest {
	private String url;
	private Map<String, String> headers = new HashMap<String, String>();
	private String method;
	private Object data;
	private boolean multipart;
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setMultipart(boolean multipart) {
		this.multipart = multipart;
	}

	public boolean isMultipart() {
		return multipart;
	}

}
