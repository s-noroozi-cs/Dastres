package com.dastres.http.jaxrs;


import com.dastres.util.RSWSClientUtil;

public class JRSManagerImpl implements JRSManager {


	@Override
	public JRSResponse send(JRSRequest request) {
		JRSResponse response = new JRSResponse();
		
		if (!request.isMultipart()) {
			long time = System.currentTimeMillis();
			response.setResponse(RSWSClientUtil.sendRequest(request));
			response.setTimeDelay((int) (System.currentTimeMillis() - time));
		}
		return response;
	}
}
