package com.traffic.model;

public class Pair {
	private Double lat, lng;

	public Pair(Place place) {
		this.lat = place.getLat();
		this.lng = place.getLng();
	}

	public Pair(Double lat, Double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLng() {
		return lng;
	}

	@Override
	public String toString() {
		return lat + "," + lng;
	}
}