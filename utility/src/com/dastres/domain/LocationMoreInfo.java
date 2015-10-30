package com.dastres.domain;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.dastres.util.DataCorrectioUtil;
import com.dastres.util.StringUtil;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LocationMoreInfo {
	private String tel;
	private String web;
	private String note;

	private static final String JS_KEY_TEL = "tel";
	private static final String JS_KEY_WEB = "web";
	private static final String JS_KEY_NOTE = "note";

	public void setTel(String tel) {
		this.tel = DataCorrectioUtil.unifiedChar(tel);
	}

	public String getTel() {
		return tel;
	}

	public void setWeb(String web) {
		this.web = DataCorrectioUtil.unifiedChar(web);
	}

	public String getWeb() {
		return web;
	}

	public void setNote(String note) {
		this.note = DataCorrectioUtil.unifiedChar(note);
	}

	public String getNote() {
		return note;
	}

	public JSONObject asJson() throws JSONException {
		JSONObject js = new JSONObject();
		js.put(JS_KEY_TEL, getTel());
		js.put(JS_KEY_WEB, getWeb());
		js.put(JS_KEY_NOTE, getNote());
		return js;
	}
	
	public static LocationMoreInfo mapMoreInfoField(String locMoreInfo) {
		try {
			if (StringUtil.isNotEmpty(locMoreInfo)) {
				return LocationMoreInfo
						.makeFromJson(new JSONObject(locMoreInfo));
			} else {
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return new LocationMoreInfo();
		}
	}

	public static LocationMoreInfo makeFromJson(JSONObject js) throws JSONException {
		LocationMoreInfo obj = new LocationMoreInfo();
		if (js.has(JS_KEY_NOTE)) {
			obj.setNote(js.getString(JS_KEY_NOTE));
		}
		if (js.has(JS_KEY_TEL)) {
			obj.setTel(js.getString(JS_KEY_TEL));
		}
		if (js.has(JS_KEY_WEB)) {
			obj.setWeb(js.getString(JS_KEY_WEB));
		}
		return obj;
	}
}
