package com.dastres.master.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.master.action.helper.ActionMessageUtil;
import com.dastres.master.action.helper.ActionNavigationUtil;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.JRSFactory;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.rsws.types.input.TransferParamTO;
import com.dastres.util.SecurityUtil;

public class DataTransferAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String REQ_ATT_ACT_SLVS = "activeSlaves";

	private static final String PARAM_METHOD = "method";
	private static final String PARAM_FROM_ADR = "fromAddress";
	private static final String PARAM_TO_ADR = "toAddress";
	private static final String PARAM_REC_SIZE = "recordSize";

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			String method = request.getParameter(PARAM_METHOD);

			if ("transfer".equals(method)) {
				transfer(request, response);
			} else {

				ServiceFactory.getSlaveManagementService().reload();

				List<SlaveTO> activeSlaves = ServiceFactory
						.getSlaveManagementService().getActiveSlaveTO();
				
				request.setAttribute(REQ_ATT_ACT_SLVS, activeSlaves);
			}

		} catch (ServiceException ex) {
			ActionMessageUtil.setError(request, ex);
		}

		request.getRequestDispatcher(
				ActionNavigationUtil.DATA_TRANSFER_MAIN_PAGE).forward(request,
				response);
	}

	private void transfer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String fromAddress = request.getParameter(PARAM_FROM_ADR);
			String toAddress = request.getParameter(PARAM_TO_ADR);
			String recordSize = request.getParameter(PARAM_REC_SIZE);

			String ticket = SecurityUtil.hash(new Date().toString() + "|"
					+ fromAddress + "|" + toAddress + "|" + recordSize + "|"
					+ System.currentTimeMillis());

			TransferParamTO transferTO = new TransferParamTO();
			transferTO.setRecordCount(Integer.parseInt(recordSize));
			transferTO.setTicket(ticket);
			transferTO.setToURL(toAddress);
			transferTO.setReceiptURL(fromAddress);
			JRSRequest jrsRequest = JRSFactory.getTransferRequest(fromAddress,
					transferTO);
			JRSFactory.getJWSManager().send(jrsRequest);
			
			ActionMessageUtil.setSucessMessage(request);
		} catch (Exception ex) {
			ActionMessageUtil.setError(request, ex);
		}
	}
}
