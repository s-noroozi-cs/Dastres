package com.dastres.slave.dao;

import java.util.Set;

import com.dastres.slave.exception.DAOException;

public interface TrustedHostDAO {

	void save(Set<String> hosts) throws DAOException;

	Set<String> list() throws DAOException;

}
