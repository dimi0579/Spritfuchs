package com.dmitrij.doberstein.spritfuchs.dataclasses;

import java.io.Serializable;
import java.util.List;

public class Station implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Openingtime> openingTimes;
    
    private String id;

    private String name;

    private StationBrand mark;

    private String street;

    private String houseNumber;

    private String city;

    private int cityCode;

    private double latitude;

    private double longtitude;

    public Station(List<Openingtime> openingTimes, String id, String name,
    		StationBrand mark, String street, String houseNumber, String city,
			int cityCode, double latitude, double longtitude) {
		super();
		this.openingTimes = openingTimes;
		this.id = id;
		this.name = name;
		this.mark = mark;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.cityCode = cityCode;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}

	public String getCity() {
            return city;
    }

    public int getCityCode() {
            return cityCode;
    }

    public String getHouseNumber() {
            return houseNumber;
    }

    public String getId() {
            return id;
    }

    public double getLatitude() {
            return latitude;
    }

    public double getLongtitude() {
            return longtitude;
    }

    public StationBrand getMark() {
            return mark;
    }

    public String getName() {
            return name;
    }

    public List<Openingtime> getOpeningTimes() {
            return openingTimes;
    }

    public String getStreet() {
            return street;
    }

    public void setCity(String city) {
            this.city = city;
    }

    public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
    }

    public void setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
    }
    public void setId(String id) {
            this.id = id;
    }
    public void setLatitude(double latitude) {
            this.latitude = latitude;
    }
    public void setLongtitude(double longtitude) {
            this.longtitude = longtitude;
    }
    public void setMark(StationBrand mark) {
            this.mark = mark;
    }
    public void setName(String name) {
            this.name = name;
    }
    public void setOpeningTimes(List<Openingtime> openingTimes) {
            this.openingTimes = openingTimes;
    }
    public void setStreet(String street) {
            this.street = street;
    }

}