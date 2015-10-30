package com.dastres.slave.domain;

public class LocationSearchTO {
	private String keyword;
	private RegionTO region;
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setRegion(RegionTO region) {
		this.region = region;
	}
	public RegionTO getRegion() {
		return region;
	}

}
