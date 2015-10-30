package com.dastres.master.service;

import java.util.List;

import com.dastres.master.domain.SearchLogTO;
import com.dastres.master.exception.ServiceException;

public interface LogService {

	void logSearchServiceCall(final SearchLogTO searchLogTO);

	List<SearchLogTO> list(int from, int to, String orderField, boolean orderAsc)
			throws ServiceException;

	int countSearchLog() throws ServiceException;

}
