package com.dastres.master.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dastres.master.action.helper.HttpFormProcessorUtil;
import com.dastres.master.action.helper.RequestParameter;
import com.dastres.master.http.filter.AuthenticationFilter;
import com.dastres.master.http.helper.HttpAuthenticationHelper;
import com.dastres.util.SecurityUtil;

public class LoginAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String PARAM_USER_NAME = "user";
	private static final String PARAM_PASSWORD = "pass";

	private Logger logger = Logger.getLogger(this.getClass());

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			Map<String, RequestParameter> params = HttpFormProcessorUtil
					.processRequest(request);
			String userName = "";
			if (params.get(PARAM_USER_NAME) != null) {
				userName = params.get(PARAM_USER_NAME).getFieldValue();
			}
			String pass = "";
			if (params.get(PARAM_PASSWORD) != null) {
				pass = params.get(PARAM_PASSWORD).getFieldValue();
			}

			String userCredential = SecurityUtil.makeCredential(userName, pass);

			if (HttpAuthenticationHelper.validateCredential(request, response,
					userName, userCredential)) {
				response.sendRedirect(request.getContextPath());
			} else {
				request.setAttribute("usrPassInvalid", true);
				request.getRequestDispatcher(AuthenticationFilter.LOGIN_PAGE)
						.forward(request, response);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.getRequestDispatcher(AuthenticationFilter.LOGIN_PAGE)
					.forward(request, response);
		}
	}
}
