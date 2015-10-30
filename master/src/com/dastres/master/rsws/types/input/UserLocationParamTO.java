package com.dastres.master.rsws.types.input;

import java.util.List;

import com.dastres.domain.LocationMoreInfo;
import com.dastres.util.DataCorrectioUtil;
import com.dastres.util.StringUtil;

public class UserLocationParamTO extends ParamTO {

	private String name;
	private String longitude;
	private String latitude;
	private LocationMoreInfo moreInfo;
	private List<String> tags;

	public void setName(String name) {
		this.name = DataCorrectioUtil.unifiedChar(name);
	}

	public String getName() {
		return name;
	}

	public void setLongitude(String longitude) {
		this.longitude = DataCorrectioUtil.unifiedChar(longitude);
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = DataCorrectioUtil.unifiedChar(latitude);
	}

	public String getLatitude() {
		return latitude;
	}

	public void setMoreInfo(LocationMoreInfo moreInfo) {
		this.moreInfo = moreInfo;
	}

	public LocationMoreInfo getMoreInfo() {
		return moreInfo;
	}

	public void setTags(List<String> tags) {
		if (tags != null && tags.size() > 0) {
			for (int i = 0; i < tags.size(); i++) {
				tags.set(i, DataCorrectioUtil.unifiedChar(tags.get(i)));
			}
		}
		this.tags = tags;
	}

	public List<String> getTags() {
		return tags;
	}

	public String checkValidation() {
		StringBuilder sb = new StringBuilder();
		if(StringUtil.isEmpty(getName())){
			sb.append("name field is empty. ");
		}
		if(StringUtil.isEmpty(getLongitude())){
			sb.append("longitude field is empty. ");
		}
		if(StringUtil.isEmpty(getLatitude())){
			sb.append("latitude field is empty. ");
		}
		return sb.toString();
	}

}
