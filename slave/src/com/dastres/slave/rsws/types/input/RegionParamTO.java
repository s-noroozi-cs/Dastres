package com.dastres.slave.rsws.types.input;


public class RegionParamTO extends ParamTO {
	private double leftUpX;
	private double leftUpY;
	private double rightDownX;
	private double rightDownY;

	public void setLeftUpX(double leftUpX) {
		this.leftUpX = leftUpX;
	}

	public double getLeftUpX() {
		return leftUpX;
	}

	public void setLeftUpY(double leftUpY) {
		this.leftUpY = leftUpY;
	}

	public double getLeftUpY() {
		return leftUpY;
	}

	public void setRightDownX(double rightDownX) {
		this.rightDownX = rightDownX;
	}

	public double getRightDownX() {
		return rightDownX;
	}

	public void setRightDownY(double rightDownY) {
		this.rightDownY = rightDownY;
	}

	public double getRightDownY() {
		return rightDownY;
	}

	@Override
	public String toString() {
		return "leftUpX: " + leftUpX + ", leftUpY: " + leftUpY
				+ ", rightDownX: " + rightDownX + ", rightDownY: " + rightDownY;
	}

}
