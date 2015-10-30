package com.dastres.master.schedule;

import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;

public interface CommonJobTask extends Job {

	public void schedule(Scheduler scheduler);

	public void reSchedule(Scheduler scheduler);

	public JobKey getJobKey();

	public TriggerKey getTriggerKey();
}
