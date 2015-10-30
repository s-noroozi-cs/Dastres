package com.dastres.master.http.helper;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.master.http.SessionInfo;
import com.dastres.util.SecurityUtil;
import com.dastres.util.StringUtil;

public class HttpAuthenticationHelper {

	private static final String TOKEN = "token";
	private static final String SESSION_INFO = "SessionInfo";

	public static boolean validateToken(HttpServletRequest httpRequest) {

		boolean tokenValidation = false;

		SessionInfo currentInfo = new SessionInfo();
		String remoteAddr = httpRequest.getRemoteAddr();
		String token = "";

		if (httpRequest.getCookies() != null) {
			for (Cookie ck : httpRequest.getCookies()) {
				if (TOKEN.equals(ck.getName())) {
					token = ck.getValue();
					break;
				}
			}
		}

		currentInfo.setIp(remoteAddr);
		currentInfo.setToken(token);

		if (currentInfo.equals(httpRequest.getSession().getAttribute(
				SESSION_INFO))) {
			tokenValidation = true;
		}

		return tokenValidation;
	}

	public static boolean validateCredential(HttpServletRequest request,
			HttpServletResponse response, String userName, String userCredential)
			throws ServiceException, IOException {
		boolean credentialValidation = false;
		String sysCredential = ServiceFactory.getSystemSettingService()
				.getUserCredential(userName);

		if (StringUtil.isNotEmpty(sysCredential)
				&& sysCredential.equals(userCredential)) {
			String token = SecurityUtil.hash(sysCredential
					+ System.currentTimeMillis());

			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setIp(request.getRemoteAddr());
			sessionInfo.setToken(token);
			request.getSession().setAttribute(SESSION_INFO, sessionInfo);
			response.addCookie(new Cookie(TOKEN, token));
			credentialValidation = true;
		}
		return credentialValidation;
	}
}
