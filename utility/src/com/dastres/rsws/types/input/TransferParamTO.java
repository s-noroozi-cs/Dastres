package com.dastres.rsws.types.input;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class TransferParamTO {

	private String toURL;
	private int recordCount;
	private String ticket;
	private String receiptURL;

	public void setToURL(String toURL) {
		this.toURL = toURL;
	}

	public String getToURL() {
		return toURL;
	}

	public void setReceiptURL(String confirmURL) {
		this.receiptURL = confirmURL;
	}

	public String getReceiptURL() {
		return receiptURL;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTicket() {
		return ticket;
	}

	public JSONObject asJson() throws JSONException {
		JSONObject js = new JSONObject();
		js.put("toURL", getToURL());
		js.put("recordCount", getRecordCount());
		js.put("receiptURL", getReceiptURL());
		js.put("ticket", getTicket());
		return js;
	}

}
