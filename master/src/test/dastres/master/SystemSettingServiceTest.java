package test.dastres.master;

import junit.framework.TestCase;

import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.master.service.SystemSettingService;
import com.dastres.util.NumberUtil;

public class SystemSettingServiceTest extends TestCase {

	private SystemSettingService sysSettingSrv;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.sysSettingSrv = ServiceFactory.getSystemSettingService();
	}

	public final void testGetSlavePingTime() {
		try {
			assertTrue(NumberUtil.isPositive(this.sysSettingSrv
					.getSlavePingTime()));
		} catch (ServiceException e) {
			fail(e.getMessage());
		}
	}

	public final void testSetSlavePingTime() {

		try {
			sysSettingSrv.setSlavePingTime(-1);
			fail();
		} catch (ServiceException e) {
			assertTrue(true);
		}

		try {
			int oldPingTime = sysSettingSrv.getSlavePingTime();
			int pingTime = oldPingTime + 1000;
			sysSettingSrv.setSlavePingTime(pingTime);
			assertTrue(pingTime == sysSettingSrv.getSlavePingTime());
			sysSettingSrv.setSlavePingTime(1000);
		} catch (ServiceException e) {
			fail(e.getMessage());
		}

	}

}
