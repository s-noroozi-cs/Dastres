package com.dastres.master.thread;

import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.domain.UserLocationInfo;
import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.master.rsws.types.input.UserLocationParamTO;

public class UserLocationThread extends Thread {

	private UserLocationParamTO userLocParamTO;
	private List<Integer> acceptedIDs;
	private int method = 0;
	public static final int METHOD_ADD = 1;
	public static final int METHOD_ACCEPT = 2;
	public static final int METHOD_REMOVE = 3;

	private UserLocationThread(int method) {
		setPriority(Thread.MIN_PRIORITY);
		this.method = method;
	}

	public UserLocationThread(UserLocationParamTO userLocParamTO) {
		this(METHOD_ADD);
		this.userLocParamTO = userLocParamTO;
	}

	public UserLocationThread(List<Integer> acceptedIDs, int method) {
		this(method);
		this.acceptedIDs = acceptedIDs;
	}

	private void addMethod() {
		UserLocationInfo userLocTO = new UserLocationInfo();
		userLocTO.setLatitude(userLocParamTO.getLatitude());
		userLocTO.setLongitude(userLocParamTO.getLongitude());
		userLocTO.setName(userLocParamTO.getName());
		userLocTO.setTags(userLocParamTO.getTags());
		userLocTO.setMoreInfo(userLocParamTO.getMoreInfo());

		try {
			ServiceFactory.getUserLocService().add(userLocTO);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	private void acceptMethod() {
		try {
			if (acceptedIDs != null && acceptedIDs.size() > 0) {
				List<LocationInfo> userLocationInfo = ServiceFactory
						.getUserLocService().accept(acceptedIDs);
				List<SlaveTO> slaveTOs = ServiceFactory
						.getSlaveManagementService().getActiveSlaveTO();
				SlaveTO slaveTO = slaveTOs.get(0);
				for (int i = 1; i < slaveTOs.size(); i++) {
					if (slaveTO.getRecordSize() > slaveTOs.get(i)
							.getRecordSize()) {
						slaveTO = slaveTOs.get(i);
					}
				}
				new SendDataThread(userLocationInfo, slaveTO).start();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	private void removeMethod() {
		try {
			if (acceptedIDs != null && acceptedIDs.size() > 0) {
				ServiceFactory.getUserLocService().remove(acceptedIDs);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		if (this.method == METHOD_ADD) {
			addMethod();
		} else if (this.method == METHOD_ACCEPT) {
			acceptMethod();
		} else if (this.method == METHOD_REMOVE) {
			removeMethod();
		} else {
			System.err.println("Method is not valid, method: " + method);
		}
	}
}
