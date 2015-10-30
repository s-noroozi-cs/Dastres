package com.dastres.master.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dastres.master.action.helper.ActionMessageUtil;
import com.dastres.master.action.helper.ActionNavigationUtil;
import com.dastres.master.config.DefaultValues;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.util.NumberUtil;

public class SystemSettingAction extends HttpServlet {

	public static final String RESPONSE_RULE_ONLY_MASTER = "Only-Master";
	public static final String RESPONSE_RULE_MASTER_SLAVE = "Master-Slave";

	private Logger logger = Logger.getLogger(this.getClass());

	private static final String PARAM_METHOD = "method";
	private static final String PARAM_SLAVE_PING_TIME = "slavePingTime";

	private static final String PARAM_RESPONSE_RULE = "responseRule";

	private static final String METHOD_SAVE = "save";

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String method = request.getParameter(PARAM_METHOD);

		if (METHOD_SAVE.equals(method)) {
			save(request, response);
		} else {
			showPage(request, response);
		}

	}

	private void showPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setAttribute(PARAM_SLAVE_PING_TIME, ServiceFactory
					.getSystemSettingService().getSlavePingTime());

			request.setAttribute(PARAM_RESPONSE_RULE, ServiceFactory
					.getSystemSettingService().getResponseRule());

		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			ActionMessageUtil.setError(request, e);
		}

		request
				.getRequestDispatcher(
						ActionNavigationUtil.SYS_SETTING_MAIN_PAGE).forward(
						request, response);

	}

	private void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int slvPingTime = NumberUtil.cast2Int(request
				.getParameter(PARAM_SLAVE_PING_TIME),
				DefaultValues.SYS_CFG_SLV_PING_TIME);
		String responseRule = request.getParameter(PARAM_RESPONSE_RULE);

		try {
			ServiceFactory.getSystemSettingService().setSlavePingTime(
					slvPingTime);

			ServiceFactory.getSystemSettingService().setResponseRule(
					responseRule);

			ActionMessageUtil.setSucessMessage(request);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			ActionMessageUtil.setError(request, e);
		}

		showPage(request, response);

	}
}