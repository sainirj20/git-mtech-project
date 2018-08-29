package com.traffic.model;

import java.util.LinkedHashMap;

import org.joda.time.DateTime;

public class CongestionHistory {

	private final class HistoryKey {
		final Integer monthOfYear = DateTime.now().getMonthOfYear();;
		final String dayOfWeek = DateTime.now().dayOfWeek().getAsText();
		final Integer hour = DateTime.now().getHourOfDay();
	}

	private final class PlaceToSpeedMap extends LinkedHashMap<String, Integer> {
		private static final long serialVersionUID = 1L;
	}

	private final class HoursHistory extends LinkedHashMap<Number, PlaceToSpeedMap> {
		private static final long serialVersionUID = 1L;
	}

	private final class DaysHistory extends LinkedHashMap<String, HoursHistory> {
		private static final long serialVersionUID = 1L;
	}

	private final class MonthsHistory extends LinkedHashMap<Number, DaysHistory> {
		private static final long serialVersionUID = 1L;
	}

	private MonthsHistory monthsHistory;

	public MonthsHistory getHistory() {
		return monthsHistory;
	}

	public void setHistory(MonthsHistory monthsHistory) {
		this.monthsHistory = monthsHistory;
	}

	public boolean containsPlaceId(String placeId) {
		HistoryKey key = new HistoryKey();
		if (monthsHistory.containsKey(key.monthOfYear)) {
			DaysHistory daysHistory = monthsHistory.get(key.monthOfYear);
			if (daysHistory.containsKey(key.dayOfWeek)) {
				HoursHistory hoursHistory = daysHistory.get(key.dayOfWeek);
				if (hoursHistory.containsKey(key.hour)) {
					return hoursHistory.get(key.hour).containsKey(placeId);
				}
			}
		}
		return false;
	}

}
