package com.dastres.slave.dao;

import java.util.Collection;
import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.slave.domain.LocationSearchTO;
import com.dastres.slave.exception.DAOException;
import com.dastres.rsws.types.input.TransferParamTO;

public interface LocationDAO {
	List<LocationInfo> find(LocationSearchTO locSearchTO) throws DAOException;

	void add(List<LocationInfo> locInfos) throws DAOException;

	int countRecord() throws DAOException;

	void remove(TransferParamTO transferTO) throws DAOException;

	Collection<LocationInfo> list(TransferParamTO transferTO) throws DAOException;
}
