package com.dastres.util;

import java.io.OutputStream; 
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.dastres.config.Constant;
import com.dastres.http.jaxrs.JRSRequest;

public class RSWSClientUtil {

	public static String sendGetRequest(String url) {
		return sendRequest(url, null, Constant.HTTP_METHOD_GET, null);
	}

	public static String sendRequest(JRSRequest request) {
		String response = null;
		if (request.getData() instanceof String) {
			response = sendRequest(request.getUrl(), request.getHeaders(),
					request.getMethod(), (String) request.getData());
		} else {
			System.err.println("request data type does not support, url:"
					+ request.getUrl());
		}
		return response;
	}

	public static String sendRequest(String url, Map<String, String> headers,
			String method, String data) {

		try {

			HttpURLConnection httpCon = (HttpURLConnection) new URL(url)
					.openConnection();

			httpCon.setRequestMethod(method);

			if (headers != null && !headers.isEmpty()) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpCon
							.addRequestProperty(entry.getKey(), entry
									.getValue());
				}
			}

			if (StringUtil.isNotEmpty(data)) {
				httpCon.setDoOutput(true);
				OutputStream outStream = httpCon.getOutputStream();
				outStream.write(data.getBytes(Constant.UTF8_ENCODING));
				outStream.flush();
				outStream.close();
			}

			return StreamUtil.getString(httpCon.getInputStream());

		} catch (Throwable e) {
			return e.getMessage();
		}
	}
}