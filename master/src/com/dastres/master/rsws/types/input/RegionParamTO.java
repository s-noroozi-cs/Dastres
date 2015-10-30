package com.dastres.master.rsws.types.input;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.dastres.util.DataCorrectioUtil;

public class RegionParamTO extends ParamTO {

	private Logger logger = Logger.getLogger(RegionParamTO.class);

	private String leftUpX;
	private String leftUpY;
	private String rightDownX;
	private String rightDownY;

	public void setLeftUpX(String leftUpX) {
		this.leftUpX = DataCorrectioUtil.unifiedChar(leftUpX);
	}

	public String getLeftUpX() {
		return leftUpX;
	}

	public void setLeftUpY(String leftUpY) {
		this.leftUpY = DataCorrectioUtil.unifiedChar(leftUpY);
	}

	public String getLeftUpY() {
		return leftUpY;
	}

	public void setRightDownX(String rightDownX) {
		this.rightDownX = DataCorrectioUtil.unifiedChar(rightDownX);
	}

	public String getRightDownX() {
		return rightDownX;
	}

	public void setRightDownY(String rightDownY) {
		this.rightDownY = DataCorrectioUtil.unifiedChar(rightDownY);
	}

	public String getRightDownY() {
		return rightDownY;
	}

	@Override
	public String toString() {
		return "leftUpX: " + leftUpX + ", leftUpY: " + leftUpY
				+ ", rightDownX: " + rightDownX + ", rightDownY: " + rightDownY;
	}

	public JSONObject asJson() {
		try {
			JSONObject js = new JSONObject();
			js.put("leftUpX", leftUpX);
			js.put("leftUpY", leftUpY);
			js.put("rightDownX", rightDownX);
			js.put("rightDownY", rightDownY);
			return js;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}

}
