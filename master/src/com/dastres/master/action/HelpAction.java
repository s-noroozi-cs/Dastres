package com.dastres.master.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dastres.master.action.helper.HttpFormProcessorUtil;
import com.dastres.master.action.helper.RequestParameter;
import com.dastres.util.SecurityUtil;

public class HelpAction extends HttpServlet {

	private static final long serialVersionUID = 1L;


	private final String PARAM_METHOD = "method";
	private static final String PARAM_USER_NAME = "user";
	private static final String PARAM_PASSWORD = "pass";

	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, RequestParameter> params = HttpFormProcessorUtil
				.processRequest(request);
		
		if ("hashPass".equals(params.get(PARAM_METHOD).getFieldValue())) {
			generateCredential(request, response, params);
		}else if ("logReq".equals(params.get(PARAM_METHOD).getFieldValue())) {
			String newLine = "\n";
			StringBuilder sb = new StringBuilder();
			sb.append("request.getRemoteAddr(): " + request.getRemoteAddr());
			sb.append(newLine);
			sb.append("request.getRemoteHost(): " + request.getRemoteHost());
			sb.append(newLine);
			sb.append("----------- header -----------");
			sb.append(newLine);
			Enumeration<String> headerNames =  request.getHeaderNames();
			while(headerNames.hasMoreElements()){
				String headerName = headerNames.nextElement();
				Enumeration<String> headerValues = request.getHeaders(headerName);
				while(headerValues.hasMoreElements()){
					sb.append(headerName + ":" + headerValues.nextElement());
					sb.append(newLine);
				}
			}
			sb.append("----------- header -----------");
			sb.append(newLine);
			OutputStream out =  response.getOutputStream();
			out.write(sb.toString().getBytes());
			out.flush();
			out.close();
		}
		
	}

	private void generateCredential(HttpServletRequest request,
			HttpServletResponse response, Map<String, RequestParameter> params) {
		String userName = params.get(PARAM_USER_NAME).getFieldValue();
		String pass = params.get(PARAM_PASSWORD).getFieldValue();
		try {
			response.getOutputStream().write(
					SecurityUtil.makeCredential(userName, pass).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
