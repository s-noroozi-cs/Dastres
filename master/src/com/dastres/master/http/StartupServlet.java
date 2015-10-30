package com.dastres.master.http;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import com.dastres.master.schedule.CommonJobTask;
import com.dastres.master.schedule.ScheduleManager;

public class StartupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	private Map<String, String> iterateInitParams() {
		Map<String, String> paramMap = new HashMap<String, String>();

		logger.info("ierate init params name");
		Enumeration<String> params = (Enumeration<String>) getInitParameterNames();

		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			paramMap.put(paramName, getInitParameter(paramName));
		}

		return paramMap;
	}

	public void init() throws ServletException {

		Map<String, String> initParams = iterateInitParams();
		schedulingTask(initParams);


	}

	private void schedulingTask(Map<String, String> initParams) {
		List<CommonJobTask> jobList2Schedule = new ArrayList<CommonJobTask>();
		for (String paramName : initParams.keySet()) {
			if (paramName.startsWith("Schedule_")) {
				try {
					jobList2Schedule.add((CommonJobTask) Class.forName(
							initParams.get(paramName)).newInstance());
				} catch (Throwable e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		try {
			ScheduleManager.scheduleJobs(jobList2Schedule);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}


}
