package com.dastres.master.thread;

import java.util.concurrent.Callable;

import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.http.jaxrs.JRSResponse;
import com.dastres.util.RSWSClientUtil;

public class FindLocationCallable implements Callable<JRSResponse> {

	private JRSRequest request;
	

	public FindLocationCallable(JRSRequest request) {
		this.request = request;
	}

	@Override
	public JRSResponse call(){
		JRSResponse response = new JRSResponse();
		long time = System.currentTimeMillis();
		response.setResponse(RSWSClientUtil.sendRequest(request));
		response.setTimeDelay(System.currentTimeMillis() - time);
		return response;
	}

}
