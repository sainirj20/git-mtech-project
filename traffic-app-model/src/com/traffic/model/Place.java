package com.traffic.model;

public class Place {
	private String placeId;
	private Double lat;
	private Double lng;
	private String address;
	private Integer freeFlowSpeed; // KPH
	private Integer averageSpeed; // KPH
	private Integer currentSpeed; // KPH

	public Place(String placeId) {
		this.placeId = placeId;
	}

	// Getters and setters
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double longitude) {
		this.lng = longitude;
	}

	public Integer getFreeFlowSpeed() {
		return freeFlowSpeed;
	}

	public void setFreeFlowSpeed(Integer freeFlowSpeed) {
		this.freeFlowSpeed = freeFlowSpeed;
	}

	public Integer getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(Integer currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public Integer getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(Integer averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public String getPlaceId() {
		return placeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placeId == null) ? 0 : placeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (placeId == null) {
			if (other.placeId != null)
				return false;
		} else if (!placeId.equals(other.placeId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Place [PlaceId=" + placeId + ", Lat=" + lat + ", Lng=" + lng + ", freeFlowSpeed=" + freeFlowSpeed
				+ ", averageSpeed=" + averageSpeed + ", currentSpeed=" + currentSpeed + ", address=" + address + "]";
	}

	public String getLatLong() {
		return lat + "," + lng;
	}

	public void copySpeedDetails(Place place) {
		freeFlowSpeed = place.getFreeFlowSpeed();
		averageSpeed = place.getAverageSpeed();
		currentSpeed = place.getCurrentSpeed();
	}

	public boolean isPlaceCongested() {
		Integer walkingSpeed = 6; // KPH
		float congestionThresHold = 0.5f; // half of freeFlowSpeed
		if (null == currentSpeed || null == freeFlowSpeed) {
			return false;
		}
		return (currentSpeed < (freeFlowSpeed * congestionThresHold) || currentSpeed < walkingSpeed) ? true : false;
	}

	public boolean hasLocationDetails() {
		return null != lat && null != lng && null != address;
	}

}