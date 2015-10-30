package com.dastres.master.dao;

import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.domain.UserLocationInfo;
import com.dastres.master.exception.DAOException;

public interface UserLocDAO {
	List<UserLocationInfo> list(int from, int to, String orderField,
			boolean orderAsc) throws DAOException;

	List<LocationInfo> accept(List<Integer> ids) throws DAOException;

	void remove(List<Integer> ids) throws DAOException;

	int countUserLoc() throws DAOException;

	void add(UserLocationInfo... LocInfos) throws DAOException;
}
