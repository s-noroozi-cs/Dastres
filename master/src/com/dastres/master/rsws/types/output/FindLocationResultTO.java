package com.dastres.master.rsws.types.output;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dastres.domain.LocationInfo;
import com.dastres.rsws.types.output.ResultTO;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FindLocationResultTO extends ResultTO {

	private List<LocationInfo> locationInfos;
	private long searchTime;

	public void setLocationInfos(List<LocationInfo> locationInfos) {
		this.locationInfos = locationInfos;
	}

	public List<LocationInfo> getLocationInfos() {
		return locationInfos;
	}

	public void addLocationInfos(List<LocationInfo> newLocInfos) {
		if (locationInfos == null) {
			locationInfos = new ArrayList<LocationInfo>();
		}
		if (newLocInfos != null && newLocInfos.size() > 0) {
			locationInfos.addAll(newLocInfos);
		}
	}

	public void setSearchTime(long searchTime) {
		this.searchTime = searchTime;
	}

	public long getSearchTime() {
		return searchTime;
	}
}
