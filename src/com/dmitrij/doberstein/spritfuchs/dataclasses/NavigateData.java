package com.dmitrij.doberstein.spritfuchs.dataclasses;

public class NavigateData {
	private double myLatitude;
	private double myLongtitude;
	private double destLatitude;
	private double destLongtitude;
	
	public NavigateData(double myLatitude, double myLongtitude,
			double destLatitude, double destLongtitude) {
		super();
		this.myLatitude = myLatitude;
		this.myLongtitude = myLongtitude;
		this.destLatitude = destLatitude;
		this.destLongtitude = destLongtitude;
	}

	public double getMyLatitude() {
		return myLatitude;
	}

	public void setMyLatitude(double myLatitude) {
		this.myLatitude = myLatitude;
	}

	public double getMyLongtitude() {
		return myLongtitude;
	}

	public void setMyLongtitude(double myLongtitude) {
		this.myLongtitude = myLongtitude;
	}

	public double getDestLatitude() {
		return destLatitude;
	}

	public void setDestLatitude(double destLatitude) {
		this.destLatitude = destLatitude;
	}

	public double getDestLongtitude() {
		return destLongtitude;
	}

	public void setDestLongtitude(double destLongtitude) {
		this.destLongtitude = destLongtitude;
	}
}
