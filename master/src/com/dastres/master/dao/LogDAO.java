package com.dastres.master.dao;

import java.util.List;

import com.dastres.master.domain.SearchLogTO;
import com.dastres.master.exception.DAOException;

public interface LogDAO {
	void saveSearchLog(SearchLogTO searchLogTO) throws DAOException;

	List<SearchLogTO> list(int from, int to, String orderField, boolean orderAsc)
			throws DAOException;

	int countSearchLog() throws DAOException;
}
