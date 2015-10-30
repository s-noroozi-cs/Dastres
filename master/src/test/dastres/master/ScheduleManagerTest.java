package test.dastres.master;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.dastres.master.factory.ServiceFactory;
import com.dastres.master.schedule.CommonJobTask;
import com.dastres.master.schedule.PingTimeJob;
import com.dastres.master.schedule.ScheduleManager;

public class ScheduleManagerTest extends TestCase {

	public final void testScheduleJobs() {

		try {

			List<CommonJobTask> jobs = new ArrayList<CommonJobTask>();
			jobs.add(new PingTimeJob());
			ScheduleManager.scheduleJobs(jobs);

			ServiceFactory.getSystemSettingService().setSlavePingTime(1);

			Thread.sleep(2000);

			ServiceFactory.getSystemSettingService().setSlavePingTime(1000);
			
			assertTrue(true);
			
			

		} catch (Throwable e) {
			e.printStackTrace();
			fail();
		}
	}

}
