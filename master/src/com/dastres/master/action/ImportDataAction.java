package com.dastres.master.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dastres.master.action.helper.ActionMessageUtil;
import com.dastres.master.action.helper.ActionNavigationUtil;
import com.dastres.master.action.helper.HttpFormProcessorUtil;
import com.dastres.master.action.helper.RequestParameter;
import com.dastres.master.domain.ImportDataInfo;
import com.dastres.master.factory.Maker;
import com.dastres.master.thread.ImportDataThread;

public class ImportDataAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String PARAM_METHOD = "method";
	private static final String PARAM_SEPARATOR = "separator";
	private static final String PARAM_DATA_FILE = "dataFile";

	private static final String METHOD_UPLOAD_ZIP_FILE = "uploadZipFile";

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Map<String, RequestParameter> params = HttpFormProcessorUtil
				.processRequest(request);

		String method = HttpFormProcessorUtil.getParamAsString(params,
				PARAM_METHOD);

		if (METHOD_UPLOAD_ZIP_FILE.equals(method)) {
			uploadZipFile(params);
			ActionMessageUtil.setSucessMessage(request);
		}

		showPage(request, response);

		
	}

	private void uploadZipFile(Map<String, RequestParameter> params)
			throws IOException {

		String separator = (params.get(PARAM_SEPARATOR).getFieldValue());
		InputStream zipInputStream = params.get(PARAM_DATA_FILE).getFileItem()
				.getInputStream();

		ImportDataInfo importDataInfo = Maker.makeCSVZipDataInfo(
				zipInputStream, separator);

		new ImportDataThread(importDataInfo).start();
	}

	private void showPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		request
				.getRequestDispatcher(
						ActionNavigationUtil.IMPORT_DATA_MAIN_PAGE).forward(
						request, response);

		
	}

}
