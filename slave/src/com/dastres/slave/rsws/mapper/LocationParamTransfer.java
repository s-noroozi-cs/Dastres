package com.dastres.slave.rsws.mapper;

import java.util.ArrayList;
import java.util.List;

import com.dastres.domain.LocationInfo;
import com.dastres.slave.domain.LocationSearchTO;
import com.dastres.slave.domain.RegionTO;
import com.dastres.slave.rsws.types.input.FindLocationParamTO;
import com.dastres.slave.rsws.types.input.LocationStorageParamTO;
import com.dastres.slave.rsws.types.input.RegionParamTO;

public class LocationParamTransfer {

	public static LocationSearchTO getLocationSearchTO(
			FindLocationParamTO locParamTO) {
		LocationSearchTO locSearchTO = new LocationSearchTO();
		locSearchTO.setKeyword(locParamTO.getKeyword());
		locSearchTO.setRegion(getRegionTO(locParamTO.getRegion()));
		return locSearchTO;
	}

	public static RegionTO getRegionTO(RegionParamTO regParamTO) {
		RegionTO regTO = new RegionTO();
		regTO.setLeftUpX(regParamTO.getLeftUpX());
		regTO.setLeftUpY(regParamTO.getLeftUpY());
		regTO.setRightDownX(regParamTO.getRightDownX());
		regTO.setRightDownY(regParamTO.getRightDownY());
		return regTO;
	}

	public static List<LocationInfo> getLocationInfos(
			List<LocationStorageParamTO> locStorageTOs) {
		List<LocationInfo> list = new ArrayList<LocationInfo>(locStorageTOs
				.size());
		for (LocationStorageParamTO to : locStorageTOs) {
			list.add(to.asLocationInfo());
		}
		return list;
	}

}
