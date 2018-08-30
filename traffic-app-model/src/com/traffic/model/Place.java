package com.traffic.model;

public class Place {
	private String placeId;
	private Double lat;
	private Double lng;
	private String address;
	private Integer freeFlowSpeed; // KPH
	private Integer averageSpeed; // KPH
	private Integer currentSpeed; // KPH

	private boolean isUpdated = false; // not to be saved in db;

	public Place(String placeId) {
		this.placeId = placeId;
	}

	// getters and setters
	public String getPlaceId() {
		return placeId;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLng() {
		return lng;
	}

	public String getAddress() {
		return address;
	}

	public Integer getFreeFlowSpeed() {
		return freeFlowSpeed;
	}

	public Integer getAverageSpeed() {
		return averageSpeed;
	}

	public Integer getCurrentSpeed() {
		return currentSpeed;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setLat(Double lat) {
		isUpdated = this.lat != lat;
		this.lat = lat;
	}

	public void setLng(Double lng) {
		isUpdated = this.lng != lng;
		this.lng = lng;
	}

	public void setAddress(String address) {
		if (null == address) {
			return;
		}
		isUpdated = !address.equals(this.address);
		this.address = address;
	}

	public void setFreeFlowSpeed(Integer freeFlowSpeed) {
		isUpdated = this.freeFlowSpeed != freeFlowSpeed;
		this.freeFlowSpeed = freeFlowSpeed;
	}

	public void setAverageSpeed(Integer averageSpeed) {
		isUpdated = this.averageSpeed != averageSpeed;
		this.averageSpeed = averageSpeed;
	}

	public void setCurrentSpeed(Integer currentSpeed) {
		isUpdated = this.currentSpeed != currentSpeed;
		this.currentSpeed = currentSpeed;
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