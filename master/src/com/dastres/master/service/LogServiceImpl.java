package com.dastres.master.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.dastres.master.domain.SearchLogTO;
import com.dastres.master.exception.DAOException;
import com.dastres.master.exception.ServiceException;
import com.dastres.master.factory.DAOFactory;

public class LogServiceImpl implements LogService {

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void logSearchServiceCall(final SearchLogTO searchLogTO) {
		logger.debug("enter");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				logger.debug("enter");
				try {
					DAOFactory.getLogDAO().saveSearchLog(searchLogTO);
				} catch (DAOException e) {
					e.printStackTrace();
				}
			}
		});
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	@Override
	public List<SearchLogTO> list(int from, int to, String orderField,
			boolean orderAsc) throws ServiceException {
		try {
			return DAOFactory.getLogDAO().list(from, to, orderField, orderAsc);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public int countSearchLog() throws ServiceException {
		try {
			return DAOFactory.getLogDAO().countSearchLog();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
