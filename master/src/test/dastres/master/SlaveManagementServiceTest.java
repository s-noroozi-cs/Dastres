package test.dastres.master;

import java.util.List;

import junit.framework.TestCase;

import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.master.service.SlaveManagementService;

public class SlaveManagementServiceTest extends TestCase {

	public final void testSlaveManagerService() {
		try {
			SlaveManagementService slvManagSer = ServiceFactory
					.getSlaveManagementService();

			SlaveTO slaveTO = new SlaveTO();
			slaveTO.setAddress("http://add-" + System.currentTimeMillis());
			slaveTO.setDisabled(false);
			slaveTO.setName("name-" + System.currentTimeMillis());

			assertTrue(slvManagSer.setSlaveTO(slaveTO));

			boolean exist = false;
			List<SlaveTO> list =slvManagSer.getAllSlaveTO(); 
			for (SlaveTO to : list) {
				if (to.equals(slaveTO)) {
					exist = true;
					slaveTO = to;
					break;
				}
			}

			assertTrue(exist);
			assertFalse(slaveTO.isDisabled());

			slaveTO.setName("name2");
			slaveTO.setDisabled(true);
			assertTrue(slvManagSer.setSlaveTO(slaveTO));

			exist = false;
			list =slvManagSer.getAllSlaveTO();
			for (SlaveTO to : list) {
				if (to.equals(slaveTO)) {
					exist = true;
					slaveTO = to;
					break;
				}
			}

			assertTrue(exist);
			assertFalse(slaveTO.isEnabled());
			assertTrue(slvManagSer.remove(slaveTO.getId()));

			exist = false;
			for (SlaveTO to : slvManagSer.getAllSlaveTO()) {
				if (to.equals(slaveTO)) {
					exist = true;
					break;
				}
			}

			assertFalse(exist);

		} catch (ServiceException e) {
			fail(e.getMessage());
		}
	}
}
