package com.dastres.master.dao;

import java.util.List;

import com.dastres.master.domain.SlaveTO;
import com.dastres.master.exception.DAOException;

public interface SlaveManagementDAO {
	
	boolean save(SlaveTO... slaveTO)throws DAOException;

	List<SlaveTO> list()throws DAOException;
	List<SlaveTO> activeList()throws DAOException;
	
	boolean remove(int slaveId) throws DAOException;


}
