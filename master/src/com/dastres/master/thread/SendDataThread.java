package com.dastres.master.thread;

import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.factory.JRSFactory;

public class SendDataThread extends Thread {


	private List<LocationInfo> data;
	private SlaveTO slaveTO;

	public SendDataThread(List<LocationInfo> data, SlaveTO slaveTO) {
		setPriority(Thread.MIN_PRIORITY);
		this.data = data;
		this.slaveTO = slaveTO;

	}

	@Override
	public void run() {
		JRSFactory.getJWSManager().send(
				JRSFactory.getSendStorageDataRequest(data, slaveTO));
	}

}
