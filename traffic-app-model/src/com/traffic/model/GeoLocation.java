package com.traffic.model;

public class GeoLocation {
	private Integer zoom = 10;
	private String name = "City Name";	
	
	private double latitude = 0;
	private double longitude = 0;
		
	private Integer tileX = 0;
	private Integer tileY = 0;
	
	public Integer getZoom() {
		return zoom;
	}
	public String getName() {
		return name;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public Integer getTileX() {
		return tileX;
	}
	public Integer getTileY() {
		return tileY;
	}
	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setTileX(Integer tileX) {
		this.tileX = tileX;
	}
	public void setTileY(Integer tileY) {
		this.tileY = tileY;
	}
	
	@Override
	public String toString() {
		return "GeoLocation [zoom=" + zoom + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", tileX=" + tileX + ", tileY=" + tileY + "]";
	}
	
}
