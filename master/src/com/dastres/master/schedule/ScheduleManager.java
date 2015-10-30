package com.dastres.master.schedule;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class ScheduleManager {

	private static Logger logger = Logger.getLogger(ScheduleManager.class);
	private static Scheduler scheduler = null;

	static {
		try {

			logger.info("StdSchedulerFactory.getDefaultScheduler");

			scheduler = StdSchedulerFactory.getDefaultScheduler();

			logger.info(scheduler.getMetaData().getSummary());

		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public static void scheduleJobs(List<CommonJobTask> jobClassz)
			throws SchedulerException {
		if (jobClassz != null && !jobClassz.isEmpty()) {

			for (CommonJobTask comTask : jobClassz) {
				logger.info("schedule " + comTask.getClass().getName());
				comTask.schedule(scheduler);
			}

			logger.info("try to start scheduler");
			scheduler.start();
		} else {
			logger.warn("job class list is empty!!!");
		}
	}

	public static void reScheduleJobs(List<CommonJobTask> jobClassz)
			throws SchedulerException {
		if (jobClassz != null && !jobClassz.isEmpty()) {

			for (CommonJobTask comTask : jobClassz) {
				logger.info("reSchedule " + comTask.getClass().getName());
				comTask.reSchedule(scheduler);
			}
		} else {
			logger.warn("job class list is empty!!!");
		}
	}

}
