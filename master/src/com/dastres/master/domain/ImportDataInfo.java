package com.dastres.master.domain;

import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.master.exception.DataException;

public interface ImportDataInfo {

	public List<LocationInfo> getLocationInfos() throws DataException;

}
