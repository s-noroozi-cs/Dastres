package com.dastres.slave.rsws.types.input;

public class StorageWithReceiptParamTO extends StorageParamTO {
	private String ticket;
	private String receiptURL;

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTicket() {
		return ticket;
	}

	public void setReceiptURL(String receiptURL) {
		this.receiptURL = receiptURL;
	}

	public String getReceiptURL() {
		return receiptURL;
	}

}
