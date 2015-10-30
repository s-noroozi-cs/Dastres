package com.dastres.domain;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.dastres.util.DataCorrectioUtil;
import com.dastres.util.StringUtil;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LocationInfo extends EntityTO {

	private static final long serialVersionUID = 1L;

	private String longitude;
	private String latitude;
	private String name;
	private List<String> tags = new ArrayList<String>();
	private LocationMoreInfo moreInfo;
	

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

	public void setName(String name) {
		this.name = DataCorrectioUtil.unifiedChar(name);
	}

	public String getName() {
		return name;
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

	public void addTag(String tag) {
		if (StringUtil.isNotEmpty(tag)) {
			tags.add(DataCorrectioUtil.unifiedChar(tag));
		}
	}

	public JSONObject asJson() {
		try {
			JSONObject js = new JSONObject();
			JSONArray tagArr = new JSONArray();

			js.put("longitude", getLongitude());
			js.put("latitude", getLatitude());
			js.put("name", getName());
			if (getMoreInfo() != null)
				js.put("moreInfo", getMoreInfo().asJson());

			if (getTags() != null && getTags().size() > 0) {
				for (String tag : getTags()) {
					tagArr.put(tag);
				}
				js.put("tags", tagArr);
			}

			return js;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void setMoreInfo(LocationMoreInfo moreInfo) {
		this.moreInfo = moreInfo;
	}

	public LocationMoreInfo getMoreInfo() {
		return moreInfo;
	}

}
