package com.dmitrij.doberstein.spritfuchs.dataclasses;

import java.io.Serializable;
import java.util.Date;

public class Price implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private FuelSort fuel;
    private double price;
    private Date timestamp;
	
    public Price(FuelSort fuel, double price, Date timestamp) {
		super();
		this.fuel = fuel;
		this.price = price;
		this.timestamp = timestamp;
	}

	public FuelSort getFuel() {
		return fuel;
	}

	public void setFuel(FuelSort fuel) {
		this.fuel = fuel;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
