package com.dastres.master.domain;

import com.dastres.domain.EntityTO;
import com.dastres.util.NumberUtil;
import com.dastres.util.StringUtil;

public class ConfigTO extends EntityTO implements Cloneable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String value;

	public ConfigTO() {
	}

	public ConfigTO(String name) {
		this.name = name;
	}

	public ConfigTO(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		if (NumberUtil.isPositive(getId())) {
			hashCode = 37 * getId();
		}
		if (StringUtil.isNotEmpty(getName())) {
			hashCode += getName().hashCode();
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object object) {
		boolean equal = true;
		if (object != null && object instanceof ConfigTO) {
			ConfigTO obj = (ConfigTO) object;
			if (NumberUtil.isPositive(getId())
					&& NumberUtil.isPositive(obj.getId())) {
				equal = equal && (getId() == obj.getId());
			}
			if (StringUtil.isNotEmpty(getName())
					&& StringUtil.isNotEmpty(obj.getName())) {
				equal = equal && (getName().equals(obj.getName()));
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
		if (StringUtil.isNotEmpty(getValue())) {
			sb.append(", value:" + getValue());
		}
		return sb.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
