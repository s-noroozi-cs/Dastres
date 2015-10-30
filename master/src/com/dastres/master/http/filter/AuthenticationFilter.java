package com.dastres.master.http.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.dastres.master.http.helper.HttpAuthenticationHelper;
import com.dastres.util.StringUtil;

public class AuthenticationFilter implements Filter {

	private Logger logger = Logger.getLogger(AuthenticationFilter.class);

	public static final String LOGIN_PAGE = "/pages/jsp/Login.jsp";
	private final String INIT_PARAM_IGNORE = "ignoreURI";

	private List<String> ignoreURI = new ArrayList<String>();

	public void destroy() {
	}

	private boolean ignore(String requestURI, String requestURL) {
		logger.debug("requestURI: " + requestURI);
		logger.debug("requestURL: " + requestURL);

		boolean ignore = requestURI.indexOf(LOGIN_PAGE) != -1;
		for (String uri : ignoreURI) {
			ignore = ignore || requestURI.indexOf(uri) != -1;
		}
		return ignore;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String requestURI = httpRequest.getRequestURI();
		String requestURL = httpRequest.getRequestURL().toString();

		if (ignore(requestURI, requestURL)) {
			chain.doFilter(request, response);
		} else {

			if (HttpAuthenticationHelper.validateToken(httpRequest)) {
				chain.doFilter(request, response);
			} else {
				request.getRequestDispatcher(LOGIN_PAGE).forward(request,
						response);
			}
		}
	}

	public void init(FilterConfig cfg) throws ServletException {
		String ignoreURIs = cfg.getInitParameter(INIT_PARAM_IGNORE);
		if (StringUtil.isNotEmpty(ignoreURIs)) {
			StringTokenizer token = new StringTokenizer(ignoreURIs, ",");
			while (token.hasMoreElements()) {
				ignoreURI.add(token.nextToken());
			}
		}
	}
}
