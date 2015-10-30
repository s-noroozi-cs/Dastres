package com.dastres.slave.rsws.types.input;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dastres.domain.LocationInfo;
import com.dastres.domain.LocationMoreInfo;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LocationStorageParamTO{
	
	private String longitude;
	private String latitude;
	private String name;
	private List<String> tags;
	private LocationMoreInfo moreInfo;
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getTags() {
		return tags;
	}
	
	public LocationInfo asLocationInfo(){
		LocationInfo locInfo = new LocationInfo();
		locInfo.setLatitude(latitude);
		locInfo.setLongitude(longitude);
		locInfo.setName(name);
		locInfo.setTags(tags);
		locInfo.setMoreInfo(moreInfo);
		return locInfo;
	}
	public void setMoreInfo(LocationMoreInfo moreInfo) {
		this.moreInfo = moreInfo;
	}
	public LocationMoreInfo getMoreInfo() {
		return moreInfo;
	}

}
