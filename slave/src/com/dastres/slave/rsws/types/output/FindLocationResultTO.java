package com.dastres.slave.rsws.types.output;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dastres.domain.LocationInfo;
import com.dastres.rsws.types.output.ResultTO;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FindLocationResultTO extends ResultTO {

	private List<LocationInfo> locationInfos;

	public void setLocationInfos(List<LocationInfo> locationInfos) {
		this.locationInfos = locationInfos;
	}

	public List<LocationInfo> getLocationInfos() {
		return locationInfos;
	}

}
