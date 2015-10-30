package com.dastres.slave.rsws.types.input;


public class FindLocationParamTO extends ParamTO {
	private String keyword;
	private RegionParamTO region;

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setRegion(RegionParamTO region) {
		this.region = region;
	}

	public RegionParamTO getRegion() {
		return region;
	}

	@Override
	public String toString() {
		return "keyword:" + keyword + ", region: " + region.toString();
	}

}
