package com.traffic.model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.UUID;

public class Congestion {
	/** AlertStatus for Congestion */
	public enum AlertStatus {
	ACTIVE, RESOLVED, INVALID, EXPIRED
	}

	private String congestionId; // uniqueID

	private Place firstPlace;
	private Place lastPlace;
	private AlertStatus status;

	private Boolean isUsualCongestion;
	private Long startTime;
	private Long lastUpdatedTime;
	private String lastUpdatedBy;

	private Integer congestionDuration;
	private Integer delayCaused; // Minutes
	private Integer congestionDistance; // Meters
	private Integer averageSpeed;

	private LinkedList<String> congestedPlaces;
	private LinkedList<String> resolvedPlaces;

	public Congestion(String congestionId) {
		this.congestionId = congestionId;
	}

	public Congestion(Place place) {
		congestionId = UUID.randomUUID().toString();
		congestedPlaces = new LinkedList<>();
		congestedPlaces.add(place.getPlaceId());
		lastUpdatedBy = "Cron Job";
		long now = Calendar.getInstance().getTimeInMillis();
		startTime = now;
		lastUpdatedTime = now;
		status = AlertStatus.ACTIVE;
	}

	// -----------------------------------------------------------------------

	public String getCongestionId() {return congestionId;}
	public Place getFirstPlace() {return firstPlace;}
	public Place getLastPlace() {return lastPlace;}
	public AlertStatus getStatus() {return status;}
	public Boolean getIsUsualCongestion() {return isUsualCongestion;}
	public Long getStartTime() {return startTime;}
	public Long getLastUpdatedTime() {return lastUpdatedTime;}
	public String getLastUpdatedBy() {return lastUpdatedBy;}
	public Integer getCongestionDuration() {return congestionDuration;}
	public Integer getDelayCaused() {return delayCaused;}
	public Integer getCongestionDistance() {return congestionDistance;}
	public Integer getAverageSpeed() {return averageSpeed;}
	public LinkedList<String> getCongestedPlaces() { return congestedPlaces; }
	public LinkedList<String> getResolvedPlaces() {	return resolvedPlaces; }

	public void setCongestionId(String congestionId) {this.congestionId = congestionId;}
	public void setFirstPlace(Place firstPlace) {this.firstPlace = firstPlace;}
	public void setLastPlace(Place lastPlace) {this.lastPlace = lastPlace;}
	public void setStatus(AlertStatus status) {this.status = status;}
	public void setIsUsualCongestion(Boolean isUsualCongestion) {this.isUsualCongestion = isUsualCongestion;}
	public void setStartTime(Long startTime) {this.startTime = startTime;}
	public void setLastUpdatedTime(Long lastUpdatedTime) {this.lastUpdatedTime = lastUpdatedTime;}
	public void setLastUpdatedBy(String lastUpdatedBy) {this.lastUpdatedBy = lastUpdatedBy;}
	public void setCongestionDuration(Integer congestionDuration) {this.congestionDuration = congestionDuration;}
	public void setDelayCaused(Integer delayCaused) {this.delayCaused = delayCaused;}
	public void setCongestionDistance(Integer congestionDistance) {this.congestionDistance = congestionDistance;}
	public void setAverageSpeed(Integer averageSpeed) {	this.averageSpeed = averageSpeed;}
	public void setCongestedPlaces(LinkedList<String> congestedPlaces) {this.congestedPlaces = congestedPlaces;	}
	public void setResolvedPlaces(LinkedList<String> resolvedPlaces) {this.resolvedPlaces = resolvedPlaces; }

	// -----------------------------------------------------------------------

	public boolean contains(Place place) {
		return congestedPlaces.contains(place.getPlaceId());
	}

	public void addFirst(Place place) {
		lastUpdatedBy = "Cron Job";
		lastUpdatedTime = Calendar.getInstance().getTimeInMillis();
		congestedPlaces.addFirst(place.getPlaceId()); // Add place to beginning of the map

		if (resolvedPlaces.contains(place.getPlaceId())) {
			resolvedPlaces.remove(place.getPlaceId());
		}
	}

	public void addLast(Place place) {
		lastUpdatedBy = "Cron Job";
		lastUpdatedTime = Calendar.getInstance().getTimeInMillis();
		congestedPlaces.addLast(place.getPlaceId()); // Add place to end of the map

		if (resolvedPlaces.contains(place.getPlaceId())) {
			resolvedPlaces.remove(place.getPlaceId());
		}
	}

	public void addToResolved(Place place) {
		lastUpdatedBy = "Cron Job";
		lastUpdatedTime = Calendar.getInstance().getTimeInMillis();
		if (resolvedPlaces.contains(place.getPlaceId())) {
			resolvedPlaces.add(place.getPlaceId());
		}
	}

	@Override
	public String toString() {
		return "Congestion [congestionId=" + congestionId + "]";
	}
}
