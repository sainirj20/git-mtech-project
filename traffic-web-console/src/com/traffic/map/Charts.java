package com.traffic.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.traffic.utils.GoogleAPIsUtil;
import com.traffic.utils.URLBuilder;

public class Charts {
	// private CityCongestionsBackEnd backEnd =
	// CityCongestionsBackEnd.getInstance();
	// private Map<Congestion.CongestionType, Integer> congestions = new
	// HashMap<>();

	@SuppressWarnings("unchecked")
	public Integer getSpeedLimit(String placeId) {
		Integer speedLimit = 0;
		try {
			Map<String, Object> response = GoogleAPIsUtil.getResponse(URLBuilder.getSpeedLimitsUrl(placeId));
			List<Map<String, Object>> speedLimits = (List<Map<String, Object>>) response.get("speedLimits");
			speedLimit = (Integer) speedLimits.get(0).get("speedLimit");
		} catch (IOException e) {
		}
		return speedLimit;
	}

	public List<String> getTodaysHistory(String placeId) {
		List<String> congestedPlacesAndDuration = new ArrayList<>();
		congestedPlacesAndDuration.add("['Time', 'Duration']");
		String[] time = { "8:00", "9:00", "10:00", "11:00", "12:00", "1:00", "2:00", "3:00" };
		for (int i = 0; i < 8; i++) {
			int duration = (int) ((Math.random() * 100000) % 100);
			String temp = "['" + time[i] + "'" + ", " + (duration / 10) * 10 + "]";
			congestedPlacesAndDuration.add(temp);
		}
		return congestedPlacesAndDuration;
	}

	public List<String> getWeeksHistory(String placeId) {
		List<String> congestedPlacesAndDuration = new ArrayList<>();
		congestedPlacesAndDuration.add("['Time', 'Duration']");
		String[] week = { "Mon", "Tue", "Wed", "Thr", "Fri" };
		for (int i = 0; i < 5; i++) {
			int duration = (int) ((Math.random() * 100000) % 100);
			String temp = "['" + week[i] + "'" + ", " + (duration / 10) * 10 + "]";
			congestedPlacesAndDuration.add(temp);
		}
		return congestedPlacesAndDuration;
	}

	public List<String> congestionsHistory() {
		List<String> congestedPlacesAndDuration = new ArrayList<>();
		congestedPlacesAndDuration.add("['Time', 'Small','Large','Unusual']");
		String[] time = { "8:00", "9:00", "10:00", "11:00", "12:00", "1:00", "2:00", "3:00" };
		for (int i = 0; i < 5; i++) {
			int small = (int) ((Math.random() * 100000) % 100);
			int large = (int) ((Math.random() * 100000) % 1000);
			int unusual = (int) ((Math.random() * 100000) % 10);
			String temp = "['" + time[i] + "'" + ", " + small + "," + large + "," + unusual + "]";
			congestedPlacesAndDuration.add(temp);
		}
		return congestedPlacesAndDuration;
	}

}
