package com.dastres.master.action.helper;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.dastres.config.Constant;

public class HttpFormProcessorUtil {

	private static Logger logger = Logger
			.getLogger(HttpFormProcessorUtil.class);

	public static String getParamAsString(Map<String, RequestParameter> params,
			String paramName) {
		String value = null;
		RequestParameter reqParam = params.get(paramName);
		if (reqParam != null) {
			value = reqParam.getFieldValue();
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, RequestParameter> processRequest(
			HttpServletRequest request) throws UnsupportedEncodingException {
		logger.info("enter");

		Map<String, RequestParameter> items = new HashMap<String, RequestParameter>();
		String contetType = request.getContentType();
		logger.info("request.getContentType(): " + contetType);

		contetType = contetType == null ? "" : contetType;

		if (contetType.indexOf("multipart/form-data") != -1
				|| contetType.indexOf("multipart/mixed") != -1) {

			try {
				ServletFileUpload sfu = new ServletFileUpload(
						new DiskFileItemFactory());
				sfu.setHeaderEncoding(Constant.UTF8_ENCODING);
				for (FileItem fileItem : sfu.parseRequest(request)) {
					items.put(fileItem.getFieldName(),
							new ComplexRequestParameter(fileItem));
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		} else {
			Enumeration<String> parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String paramName = parameterNames.nextElement();
				items.put(paramName, new SimpleRequestParameter(paramName,
						request.getParameter(paramName)));
			}
		}
		return items;
	}
}
