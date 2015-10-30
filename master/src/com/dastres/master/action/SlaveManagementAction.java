package com.dastres.master.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dastres.master.action.helper.ActionMessageUtil;
import com.dastres.master.action.helper.ActionNavigationUtil;
import com.dastres.master.action.helper.HttpFormProcessorUtil;
import com.dastres.master.action.helper.RequestParameter;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.util.NumberUtil;
import com.dastres.util.StringUtil;

public class SlaveManagementAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	private static final String PARAM_METHOD = "method";

	private static final String PARAM_SLAVE_ID = "slaveId";
	private static final String PARAM_SLAVE_NAME = "slaveName";
	private static final String PARAM_SLAVE_ADDRESS = "slaveAddress";
	private static final String PARAM_SLAVE_DISABLED = "slaveDisabled";

	private static final String REQ_ATR_SLV_MACHINE_LIST = "SlvMachiveList";

	private static final String METHOD_SAVE = "save";
	private static final String METHOD_DELETE = "remove";
	private static final String METHOD_RELOAD = "reload";

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		String method = request.getParameter(PARAM_METHOD);

		if (METHOD_DELETE.equals(method)) {
			remove(request, response);
		} else if (METHOD_SAVE.equals(method)) {
			save(request, response);
		} else if (METHOD_RELOAD.equals(method)) {
			reload(request, response);
		} else {
			showPage(request, response);
		}

		
	}

	private void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		

		try {
			int slaveId = Integer
					.parseInt(request.getParameter(PARAM_SLAVE_ID));
			ServiceFactory.getSlaveManagementService().remove(slaveId);

			ActionMessageUtil.setSucessMessage(request);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			ActionMessageUtil.setError(request, e);
		}

		showPage(request, response);

		
	}

	private void reload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		

		try {
			int slaveId = Integer
					.parseInt(request.getParameter(PARAM_SLAVE_ID));
			ServiceFactory.getSlaveManagementService().reload(slaveId);

			ActionMessageUtil.setSucessMessage(request);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			ActionMessageUtil.setError(request, e);
		}

		showPage(request, response);

		
	}

	private void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		

		SlaveTO slaveTO = new SlaveTO();

		Map<String, RequestParameter> params = HttpFormProcessorUtil
				.processRequest(request);

		String slaveId = HttpFormProcessorUtil.getParamAsString(params,
				PARAM_SLAVE_ID);
		String slaveName = HttpFormProcessorUtil.getParamAsString(params,
				PARAM_SLAVE_NAME);
		String slaveAddress = HttpFormProcessorUtil.getParamAsString(params,
				PARAM_SLAVE_ADDRESS);
		String slaveDisable = HttpFormProcessorUtil.getParamAsString(params,
				PARAM_SLAVE_DISABLED);

		if (NumberUtil.isPositive(slaveId)) {
			slaveTO.setId(Integer.parseInt(slaveId));
		}

		if (StringUtil.isNotEmpty(slaveName)) {
			slaveTO.setName(slaveName);
		}

		if (StringUtil.isNotEmpty(slaveAddress)) {
			slaveTO.setAddress(slaveAddress);
		}

		if (StringUtil.isNotEmpty(slaveDisable)) {
			slaveTO.setDisabled(Boolean.parseBoolean(slaveDisable));
		}

		try {
			ServiceFactory.getSlaveManagementService().setSlaveTO(slaveTO);
			ActionMessageUtil.setSucessMessage(request);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			ActionMessageUtil.setError(request, e);
		}

		showPage(request, response);

		
	}

	private void showPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		try {
			request.setAttribute(REQ_ATR_SLV_MACHINE_LIST, ServiceFactory
					.getSlaveManagementService().getAllSlaveTO());
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			ActionMessageUtil.setError(request, e);
		}

		request.getRequestDispatcher(
				ActionNavigationUtil.SLV_MANAGEMENT_MAIN_PAGE).forward(request,
				response);
		
	}
}
