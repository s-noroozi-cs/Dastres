package com.dastres.master.data.list;

import java.util.List;

import org.displaytag.exception.ListHandlerException;
import org.displaytag.model.ListHandler;

import com.dastres.domain.UserLocationInfo;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.ServiceFactory;
import com.dastres.util.StringUtil;

public class UserLocationListHandler implements ListHandler {

	private String orderBy;
	int size = 0;

	@Override
	public List<UserLocationInfo> getList(boolean arg0)
			throws ListHandlerException {
		return null;
	}

	@Override
	public List<UserLocationInfo> getList(int from, int to, boolean arg2)
			throws ListHandlerException {
		String orderField = "";
		boolean asc = false;
		if (StringUtil.isNotEmpty(orderBy)) {
			orderField = orderBy.split(" ")[0];
			asc = "asc".equalsIgnoreCase(orderBy.split(" ")[1]);
		}
		try {
			return ServiceFactory.getUserLocService().list(from, to,
					orderField, asc);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getOrderBy() {
		return orderBy;
	}

	@Override
	public Object getValue(int arg0, boolean arg1) throws ListHandlerException {
		return null;
	}

	@Override
	public boolean isEmpty() throws ListHandlerException {
		return size == 0;
	}

	@Override
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public int size() throws ListHandlerException {
		try {
			size = ServiceFactory.getUserLocService().countUserLoc();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return size;

	}

}
