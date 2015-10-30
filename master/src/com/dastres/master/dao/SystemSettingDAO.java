package com.dastres.master.dao;

import java.util.Set;

import com.dastres.master.domain.ConfigTO;
import com.dastres.master.exception.DAOException;

public interface SystemSettingDAO {

	public ConfigTO save(ConfigTO configTO)throws DAOException;

	public ConfigTO find(ConfigTO configTO)throws DAOException;

	public Set<ConfigTO> list()throws DAOException;
}
