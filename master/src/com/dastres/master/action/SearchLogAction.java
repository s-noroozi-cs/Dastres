package com.dastres.master.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dastres.master.action.helper.ActionNavigationUtil;
import com.dastres.master.data.list.SearchLogListHandler;

public class SearchLogAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("logSearchHandler", new SearchLogListHandler());
		request.getRequestDispatcher(ActionNavigationUtil.SEARCH_LOG_MAIN_PAGE)
				.forward(request, response);
	}

}
