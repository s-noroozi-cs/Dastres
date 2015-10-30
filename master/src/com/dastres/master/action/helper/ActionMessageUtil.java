package com.dastres.master.action.helper;

import javax.servlet.http.HttpServletRequest;

import com.dastres.master.config.MessageResouces;

public class ActionMessageUtil {

	private static final String ERROR_ATR_NAME = "actErrMsg";
	private static final String SUCCESS_ATR_NAME = "actSucessMsg";

	public static void setError(HttpServletRequest request, Exception ex) {
		request.setAttribute(ERROR_ATR_NAME, MessageResouces.OPERATION_FAILED
				+ ex.getMessage());
	}

	public static void setSucessMessage(HttpServletRequest request) {
		request.setAttribute(SUCCESS_ATR_NAME,
				MessageResouces.OPERATION_SUCCESS);
	}

	public static boolean hasError(HttpServletRequest request) {
		return request.getAttribute(ERROR_ATR_NAME) != null ? true : false;
	}

	public static String getErrorMessage(HttpServletRequest request) {
		return (String) request.getAttribute(ERROR_ATR_NAME);
	}

	public static boolean hasSucess(HttpServletRequest request) {
		return request.getAttribute(SUCCESS_ATR_NAME) != null ? true : false;
	}

	public static String getSucessMessage(HttpServletRequest request) {
		return (String) request.getAttribute(SUCCESS_ATR_NAME);
	}

}
