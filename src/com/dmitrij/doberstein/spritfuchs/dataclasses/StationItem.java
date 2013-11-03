package com.dmitrij.doberstein.spritfuchs.dataclasses;

import java.io.Serializable;
import java.util.List;

public class StationItem extends Station implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private double destination;
    private List<Price> prices;

    public StationItem(List<BussinessHours> openingTimes, String id, String name,
			StationBrand mark, String street, String houseNumber, String city,
			int cityCode, double latitude, double longtitude, double destination,
			List<Price> prices) {
		super(openingTimes, id, name, mark, street, houseNumber, city,
				cityCode, latitude, longtitude);
		this.destination = destination;
		this.prices = prices;
	}

	public double getDestination() {
            return destination;
    }

    public void setDestination(double destination) {
            this.destination = destination;
    }

    public List<Price> getPrices() {
            return prices;
    }

    public void setPrice(List<Price> prices) {
            this.prices = prices;
    }
}