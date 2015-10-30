package com.dastres.master.web.table;

import org.displaytag.decorator.TableDecorator;

import com.dastres.master.domain.SearchLogTO;

public class SearchLogWrapper extends TableDecorator {

	private SearchLogTO getSearchLogTO() {
		return (SearchLogTO) this.getCurrentRowObject();
	}

	public String getId() {
		return getSearchLogTO().getId() + "";
	}

	public String getDate() {
		return getSearchLogTO().getLogDate() + "";
	}

	public String getKeyword() {
		return getSearchLogTO().getKeyword();
	}

	public String getRegion() {
		return getSearchLogTO().getRegion();
	}

	public String getUserAgent() {
		return getSearchLogTO().getUserAgent();
	}

	public String getWaitTime() {
		return getSearchLogTO().getWaitTime() + "";
	}

}
