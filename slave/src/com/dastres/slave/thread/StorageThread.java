package com.dastres.slave.thread;

import org.apache.log4j.Logger;

import com.dastres.factory.JRSBaseFactory;
import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.slave.exception.ServiceException;
import com.dastres.slave.factory.ServiceFactory;
import com.dastres.slave.rsws.mapper.LocationParamTransfer;
import com.dastres.slave.rsws.types.input.StorageParamTO;
import com.dastres.slave.rsws.types.input.StorageWithReceiptParamTO;

public class StorageThread extends Thread {

	private Logger logger = Logger.getLogger(this.getClass());
	private StorageParamTO storageTO;

	public StorageThread(StorageParamTO storageTO) {
		super.setPriority(Thread.MIN_PRIORITY);
		this.storageTO = storageTO;
	}

	@Override
	public void run() {
		try {
			ServiceFactory.getStorageService().add(
					LocationParamTransfer.getLocationInfos(storageTO
							.getLocStorageTOs()));
			
			if (storageTO instanceof StorageWithReceiptParamTO) {
				StorageWithReceiptParamTO receiptTO = (StorageWithReceiptParamTO) storageTO;

				JRSRequest request = JRSBaseFactory.getTransferReceiptRequest(
						receiptTO.getReceiptURL(), receiptTO.getTicket());

				JRSBaseFactory.getJWSManager().send(request);
			}
			
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
