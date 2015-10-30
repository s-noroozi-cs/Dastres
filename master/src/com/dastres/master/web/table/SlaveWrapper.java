package com.dastres.master.web.table;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.decorator.TableDecorator;

import com.dastres.master.domain.SlaveTO;

public class SlaveWrapper extends TableDecorator {

	private SlaveTO getSlaveTO() {
		return (SlaveTO) this.getCurrentRowObject();
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) this.getPageContext().getRequest();
	}

	public int getIdColumn() {
		return getSlaveTO().getId();
	}

	public String getNameColumn() {
		return getSlaveTO().getName();
	}

	public String getAddressColumn() {
		return getSlaveTO().getAddress();
	}

	private String getImageTag(String alt, String imgName, String click,
			boolean needPointer) {
		return "<img " + (needPointer ? "style='cursor:pointer'" : "")
				+ " src='" + getRequest().getContextPath() + "/pages/img/"
				+ imgName + "' alt='" + alt + "' title='" + alt + "' onclick='"
				+ click + "' />";
	}

	private String getHelperTag(String id, String value) {
		return "<input type='hidden' value='" + value + "' id='" + id + "' />";
	}

	private String encloseTableColumn(Object str) {
		return "<td>" + str + "</td>";
	}

	private String encloseTableRow(Object... items) {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>");
		for (Object item : items) {
			sb.append(encloseTableColumn(item));
		}
		sb.append("</tr>");
		return sb.toString();
	}

	public String getStatusColumn() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		sb.append("<tr>");
		sb.append(encloseTableColumn("State: "));
		if (getSlaveTO().isDisabled()) {
			sb.append(encloseTableColumn(getImageTag("Disabled", "red.png", "",
					false)));
		} else {
			sb.append(encloseTableColumn(getImageTag("Enabled", "green.png",
					"", false)));
		}
		sb.append("</tr>");
		
		sb.append(encloseTableRow("Last Ping (ms):", getSlaveTO()
				.getLastPing()));

		sb.append(encloseTableRow("Min Ping (ms):", getSlaveTO()
				.getMinPing()));

		sb.append(encloseTableRow("Max Ping (ms):", getSlaveTO()
				.getMaxPing()));

		sb.append(encloseTableRow("Avg Ping (ms):", getSlaveTO()
				.getAvgPing()));
		sb.append(encloseTableRow("Total Ping:", getSlaveTO()
				.getPingCount()));
		sb.append(encloseTableRow("Record size:", getSlaveTO()
				.getRecordSize()));
		sb.append("</table>");
		return sb.toString();
	}

	public String getActionColumn() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(getImageTag("Reload", "refresh.gif", "onSlvReload(\""
				+ getSlaveTO().getId() + "\")", true));

		sb.append(getImageTag("Edit", "edit.gif", "onSlvEdit(\""
				+ getSlaveTO().getId() + "\")", true));

		sb.append(getImageTag("Delete", "delete.gif", "onSlvDelete(\""
				+ getSlaveTO().getId() + "\")", true));
		

		sb.append(getHelperTag(getSlaveTO().getId() + "_name", getSlaveTO()
				.getName()));

		sb.append(getHelperTag(getSlaveTO().getId() + "_address", getSlaveTO()
				.getAddress()));

		sb.append(getHelperTag(getSlaveTO().getId() + "_disabled", getSlaveTO()
				.isDisabled() ? "true" : "false"));

		return sb.toString();
	}
}
