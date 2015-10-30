package com.dastres.master.schedule;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.dastres.master.config.DefaultValues;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.JRSFactory;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.http.jaxrs.JRSResponse;
import com.dastres.master.http.jaxrs.JRSResponseHelper;
import com.dastres.util.CollectionUtil;

@DisallowConcurrentExecution
public class PingTimeJob implements CommonJobTask {

	private final String JOB_NAME = "PING_TIME_JOB";
	private final String TRIGGER_NAME = "PING_TIME_TRIGGER";
	private final String GROUP_NAME = "PING_TIME_GROUP";

	private static Logger logger = Logger.getLogger(ScheduleManager.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
			List<SlaveTO> slaveTOs = ServiceFactory.getSlaveManagementService()
					.getActiveSlaveTO();
			if (CollectionUtil.isNotEmpty(slaveTOs)) {
				for (int i = 0; i < slaveTOs.size(); i++) {
					JRSResponse response = JRSFactory.getJWSManager().send(
							JRSFactory.getPingTimeRequest(slaveTOs.get(i)));
					if (response != null) {
						JRSResponseHelper.processPingTime(slaveTOs.get(i),
								response);
					}
				}
				ServiceFactory.getSlaveManagementService().setSlaveTO(
						slaveTOs.toArray(new SlaveTO[slaveTOs.size()]));
			} else {
				logger.warn("SlaveTO list is empty!!!");
			}
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
		}
		
	}

	public JobDetail getJobDetail() {
		return JobBuilder.newJob(PingTimeJob.class).withIdentity(getJobKey())
				.build();

	}

	private Trigger getTrigger() {
		int pingTime = DefaultValues.SYS_CFG_SLV_PING_TIME;

		try {
			pingTime = ServiceFactory.getSystemSettingService()
					.getSlavePingTime();
			logger.debug(pingTime);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}

		return TriggerBuilder.newTrigger().withIdentity(getTriggerKey())
				.withSchedule(
						SimpleScheduleBuilder.repeatSecondlyForever(pingTime))
				.build();
	}

	@Override
	public void reSchedule(Scheduler scheduler) {
		try {
			Date nextTime = scheduler.rescheduleJob(getTriggerKey(),
					getTrigger());
			logger.info("next date to execute: " + nextTime);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void schedule(Scheduler scheduler) {
		try {
			Date nextTime = scheduler.scheduleJob(getJobDetail(), getTrigger());
			logger.info("next date to execute: " + nextTime);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public JobKey getJobKey() {
		return JobKey.jobKey(JOB_NAME, GROUP_NAME);
	}

	@Override
	public TriggerKey getTriggerKey() {
		return TriggerKey.triggerKey(TRIGGER_NAME, GROUP_NAME);
	}

}
