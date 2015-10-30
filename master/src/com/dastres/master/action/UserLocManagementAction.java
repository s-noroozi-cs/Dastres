package com.dastres.master.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dastres.master.action.helper.ActionNavigationUtil;
import com.dastres.master.data.list.UserLocationListHandler;
import com.dastres.master.thread.UserLocationThread;
import com.dastres.util.NumberUtil;
import com.dastres.util.StringUtil;

public class UserLocManagementAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String PARAM_METHOD = "method";
	private static final String PARAM_IDS = "ids";

	// private static final String METHOD_LIST = "list";
	private static final String METHOD_ACCEPT = "accept";
	private static final String METHOD_REMOVE = "remove";

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter(PARAM_METHOD);

		String ids = request.getParameter(PARAM_IDS);
		List<Integer> idsList = new ArrayList<Integer>();
		if (StringUtil.isNotEmpty(ids)) {
			for (String id : ids.split(",")) {
				if (NumberUtil.isPositive(id)) {
					idsList.add(Integer.parseInt(id));
				}
			}
		}

		if (METHOD_ACCEPT.equals(method)) {
			if (idsList.size() > 0) {
				new UserLocationThread(idsList,
						UserLocationThread.METHOD_ACCEPT).start();
			}
		} else if (METHOD_REMOVE.equals(method)) {
			if (idsList.size() > 0) {
				new UserLocationThread(idsList,
						UserLocationThread.METHOD_REMOVE).start();
			}
		}

		request.setAttribute("userLocListHandler",
				new UserLocationListHandler());

		request.getRequestDispatcher(
				ActionNavigationUtil.USER_LOC_MANAGEMENT_MAIN_PAGE).forward(
				request, response);

	}
}
