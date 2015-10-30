package com.dastres.master.domain;

import com.dastres.domain.EntityTO;
import com.dastres.util.NumberUtil;
import com.dastres.util.StringUtil;

public class SlaveTO extends EntityTO {

	private static final long serialVersionUID = 1L;

	private String name;
	private String address;
	private boolean disabled = true;

	private int lastPing;
	private int minPing = Integer.MAX_VALUE;
	private int maxPing;
	private int recordSize;
	private int pingCount;
	private long pingSum;

	@Override
	public int hashCode() {
		int hashCode = 0;

		if (NumberUtil.isPositive(getId())) {
			hashCode = 37 * getId();
		}

		if (StringUtil.isNotEmpty(getName())) {
			hashCode += getName().hashCode();
		}

		if (StringUtil.isNotEmpty(getAddress())) {
			hashCode += getAddress().hashCode();
		}

		hashCode += isDisabled() ? 1 : 0;

		return hashCode;
	}

	@Override
	public boolean equals(Object object) {
		boolean equal = true;
		if (object != null && object instanceof SlaveTO) {
			SlaveTO obj = (SlaveTO) object;
//			if (NumberUtil.isPositive(getId())
//					&& NumberUtil.isPositive(obj.getId())) {
//				equal = equal && (getId() == obj.getId());
//			}
			if (StringUtil.isNotEmpty(getName())
					&& StringUtil.isNotEmpty(obj.getName())) {
				equal = equal && (getName().equals(obj.getName()));
			}
			if (StringUtil.isNotEmpty(getAddress())
					&& StringUtil.isNotEmpty(obj.getAddress())) {
				equal = equal && (getAddress().equals(obj.getAddress()));
			}
		} else {
			equal = false;
		}
		return equal;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (NumberUtil.isPositive(getId())) {
			sb.append(", id: " + getId());
		}
		if (StringUtil.isNotEmpty(getName())) {
			sb.append(", name:" + getName());
		}
		if (StringUtil.isNotEmpty(getAddress())) {
			sb.append(", address:" + getAddress());
		}
		sb.append(", disabled:" + isDisabled());
		sb.append(", record size: " + recordSize);

		return sb.toString();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isEnabled() {
		return !disabled;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setLastPing(int lastPingTime) {
		this.lastPing = lastPingTime;
	}

	public int getLastPing() {
		return lastPing;
	}

	public void setMinPing(int minPingTime) {
		this.minPing = minPingTime;
	}

	public int getMinPing() {
		return minPing;
	}

	public void setMaxPing(int maxPingTime) {
		this.maxPing = maxPingTime;
	}

	public int getMaxPing() {
		return maxPing;
	}

	public long getAvgPing() {
		if (pingCount > 0) {
			return pingSum / pingCount;
		}
		return 0;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setPingCount(int pingCount) {
		this.pingCount = pingCount;
	}

	public int getPingCount() {
		return pingCount;
	}

	public void updatePingTime(int newPingTime) {
		pingCount++;
		lastPing = newPingTime;
		if (minPing > newPingTime) {
			minPing = newPingTime;
		}
		if (maxPing < newPingTime) {
			maxPing = newPingTime;
		}
		pingSum += newPingTime;
	}

	public void setPingSum(long pingSum) {
		this.pingSum = pingSum;
	}

	public long getPingSum() {
		return pingSum;
	}
}
