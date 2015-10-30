package com.dastres.master.web.table;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.decorator.TableDecorator;

import com.dastres.domain.UserLocationInfo;
import com.dastres.domain.LocationMoreInfo;
import com.dastres.util.StringUtil;

public class UserLocationWrapper extends TableDecorator {

	public UserLocationInfo getCurrentTO() {
		return (UserLocationInfo) this.getCurrentRowObject();
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) this.getPageContext().getRequest();
	}

	public String getContext() {
		return getRequest().getContextPath();
	}

	public String getAccept() {
		String wait = "<img src='" + getContext()
				+ "/pages/img/red.png' alt='Wait' >";
		String accept = "<img src='" + getContext()
				+ "/pages/img/green.png' alt='Wait' >";
		if ("Y".equalsIgnoreCase(getCurrentTO().getAccept())) {
			return accept;
		} else {
			return wait;
		}
	}

	public String getCheckBox() {
		StringBuilder sb = new StringBuilder();
		sb.append("<input type='checkbox' id='chk_" + this.getViewIndex()
				+ "' />");
		sb.append("<input type='hidden' id='help_" + this.getViewIndex()
				+ "' value='" + getCurrentTO().getId() + "' />");
		return sb.toString();
	}

	public String getId() {
		return getCurrentTO().getId() + "";
	}

	public String getDate() {
		return getCurrentTO().getDate() + "";
	}

	public String getName() {
		return getCurrentTO().getName();
	}

	public String getLongitude() {
		return getCurrentTO().getLongitude();
	}

	public String getLatitude() {
		return getCurrentTO().getLatitude();
	}

	public String getTags() {
		List<String> tags = getCurrentTO().getTags();
		StringBuilder sb = new StringBuilder();
		if (tags != null && tags.size() > 0) {
			sb.append("<UL>");
			for (String tag : tags) {
				sb.append("<LI>" + tag + "</LI>");
			}
			sb.append("</UL>");
		}
		return sb.toString();
	}

	public String getMoreInfo() {
		StringBuilder sb = new StringBuilder();
		LocationMoreInfo moreInfo = getCurrentTO().getMoreInfo();
		if (moreInfo != null) {
			sb.append("<UL>");
			if (StringUtil.isNotEmpty(moreInfo.getNote())) {
				sb.append("<LI>" + "Note: " + moreInfo.getNote() + "</LI>");
			}
			if (StringUtil.isNotEmpty(moreInfo.getTel())) {
				sb.append("<LI>" + "Tel: " + moreInfo.getTel() + "</LI>");
			}
			if (StringUtil.isNotEmpty(moreInfo.getWeb())) {
				sb.append("<LI>" + "Web: " + moreInfo.getWeb() + "</LI>");
			}
			sb.append("</UL>");
		}
		return sb.toString();
	}
}
