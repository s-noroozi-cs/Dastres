package test.dastres.master;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.factory.JRSFactory;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.rsws.types.input.TransferParamTO;
import com.dastres.util.SecurityUtil;

public class DataTransferServiceTest extends TestCase {

	public final void testDataTrasfer() {
		try {
			ServiceFactory.getSlaveManagementService().reload();

			List<SlaveTO> activeSlaves = ServiceFactory
					.getSlaveManagementService().getActiveSlaveTO();

			if (activeSlaves.size() < 2) {
				fail("This test case need at least 2 active slaves");
			}

			int firstSlaveRecSize = activeSlaves.get(0).getRecordSize();
			int firstId = activeSlaves.get(0).getId();
			int secondSlaveRecSize = activeSlaves.get(1).getRecordSize();
			int secondId = activeSlaves.get(1).getId();

			int recSize = 10;
			if (firstSlaveRecSize > 0) {
				makeTransfer(activeSlaves.get(0).getAddress(), activeSlaves
						.get(1).getAddress(), recSize);
				ServiceFactory.getSlaveManagementService().reload();
				activeSlaves = ServiceFactory.getSlaveManagementService()
						.getActiveSlaveTO();
				for (SlaveTO slv : activeSlaves) {
					if (slv.getId() == firstId) {
						if (slv.getRecordSize() > (firstSlaveRecSize - recSize)) {
							fail("transfer operation in-completed");
						}
					}
				}

			} else if (secondSlaveRecSize > 0) {
				makeTransfer(activeSlaves.get(0).getAddress(), activeSlaves
						.get(1).getAddress(), recSize);
				ServiceFactory.getSlaveManagementService().reload();
				activeSlaves = ServiceFactory.getSlaveManagementService()
						.getActiveSlaveTO();
				for (SlaveTO slv : activeSlaves) {
					if (slv.getId() == secondId) {
						if (slv.getRecordSize() > (secondSlaveRecSize - recSize)) {
							fail("transfer operation in-completed");
						}
					}
				}
			} else {
				fail("both slaves are empty");
			}

		} catch (Throwable ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	private void makeTransfer(String from, String to, int size)
			throws InterruptedException {

		String ticket = SecurityUtil.hash(new Date().toString() + "|" + from
				+ "|" + to + "|" + size + "|" + System.currentTimeMillis());

		TransferParamTO transferTO = new TransferParamTO();
		transferTO.setRecordCount(size);
		transferTO.setTicket(ticket);
		transferTO.setToURL(to);
		transferTO.setReceiptURL(from);
		JRSRequest jrsRequest = JRSFactory.getTransferRequest(from, transferTO);
		JRSFactory.getJWSManager().send(jrsRequest);
		Thread.sleep(3000);
	}

}
