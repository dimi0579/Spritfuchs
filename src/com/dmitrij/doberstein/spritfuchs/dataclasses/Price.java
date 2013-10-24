package com.dmitrij.doberstein.spritfuchs.dataclasses;

import java.io.Serializable;

public class Price implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private FuelSort fuel;
    private double price;
    private long timestamp;
	
    public Price(FuelSort fuel, double price, long timestamp) {
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

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
