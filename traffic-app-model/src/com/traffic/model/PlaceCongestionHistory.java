package com.traffic.model;

import java.util.LinkedList;
import java.util.List;

public class PlaceCongestionHistory {
	private String placeId;
	private int currentHour;
	private int currentDay;

	// holds the congestion history of current date
	private List<Integer> currentDaysHistory;

	// holds the congestion history of past week's current hour
	private List<Integer> lastSevenDaysHistory;

	PlaceCongestionHistory(String placeId) {
		this.placeId = placeId;
		currentDaysHistory = new LinkedList<>();
		lastSevenDaysHistory = new LinkedList<>();
	}
	
	public void addToCurrentDaysHistory(Integer hour) {
		currentDaysHistory.add(hour);
	}
	
	public void addToLastSevenDaysHistory(Integer dayOfWeek) {
		lastSevenDaysHistory.add(dayOfWeek);
	}

	public String getPlaceId() {
		return placeId;
	}

	public int getCurrentHour() {
		return currentHour;
	}

	public int getCurrentDay() {
		return currentDay;
	}

	public List<Integer> getCurrentDaysHistory() {
		return currentDaysHistory;
	}

	public List<Integer> getLastSevenDaysHistory() {
		return lastSevenDaysHistory;
	}

	public void setCurrentHour(int currentHour) {
		this.currentHour = currentHour;
	}

	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}
}
