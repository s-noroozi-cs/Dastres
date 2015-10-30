package com.dastres.domain;

public abstract class EntityTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
