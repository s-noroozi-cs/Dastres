package com.dastres.slave.thread;

import java.util.Collection;

import com.dastres.domain.LocationInfo;
import com.dastres.factory.JRSBaseFactory;
import com.dastres.http.jaxrs.JRSRequest;
import com.dastres.slave.exception.ServiceException;
import com.dastres.slave.factory.ServiceFactory;
import com.dastres.rsws.types.input.TransferParamTO;

public class TransferThread extends Thread {

	private TransferParamTO transferTO;

	public TransferThread(TransferParamTO transferTO) {
		setPriority(Thread.MIN_PRIORITY);
		this.setTransferTO(transferTO);
	}

	public void setTransferTO(TransferParamTO transferTO) {
		this.transferTO = transferTO;
	}

	public TransferParamTO getTransferTO() {
		return transferTO;
	}

	@Override
	public void run() {
		try {
			Collection<LocationInfo> locInfos = ServiceFactory
					.getStorageService().getTransferList(transferTO);

			JRSRequest request = JRSBaseFactory
					.getSendStorageDataWithReceiptRequest(locInfos, transferTO
							.getToURL(), transferTO.getTicket(), transferTO
							.getReceiptURL());

			JRSBaseFactory.getJWSManager().send(request);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
