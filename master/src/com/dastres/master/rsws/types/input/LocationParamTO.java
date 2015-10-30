package com.dastres.master.rsws.types.input;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.dastres.util.DataCorrectioUtil;

public class LocationParamTO extends ParamTO {

	private Logger logger = Logger.getLogger(LocationParamTO.class);

	private String keyword;
	private RegionParamTO region;

	public void setKeyword(String keyword) {
		this.keyword = DataCorrectioUtil.unifiedChar(keyword);
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

	public JSONObject asJson() {
		try {
			JSONObject js = new JSONObject();
			js.put("keyword", keyword);
			js.put("region", region.asJson());

			return js;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}

}
