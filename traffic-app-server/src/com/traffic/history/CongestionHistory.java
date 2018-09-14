package com.traffic.history;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.traffic.dao.CongestionHistoryDao;
import com.traffic.model.Congestion;
import com.traffic.model.Place;

public class CongestionHistory {
	private final Map<String, Integer> congestedPlacesAndDuration = new HashMap<>();
	private final Map<String, Boolean> isNewCongestedPlacesMap = new HashMap<>();

	private final List<List<String>> todaysHistory;
	private final List<List<String>> weeksHistory;

	public CongestionHistory(List<Place> congestedPlaces) {
		for (Place place : congestedPlaces) {
			congestedPlacesAndDuration.put(place.getPlaceId(), 0);
			isNewCongestedPlacesMap.put(place.getPlaceId(), false);
		}

		CongestionHistoryDao historyDao = new CongestionHistoryDao();
		todaysHistory = historyDao.getTodaysHistory();
		weeksHistory = historyDao.getWeeksHistory();
		this.init();
	}

	private void init() {
		for (String placeId : congestedPlacesAndDuration.keySet()) {
			int duration = 0;
			for (List<String> hour : todaysHistory) {
				if (0 == hour.size()) {
					continue;
				}
				duration += hour.contains(placeId) ? 10 : -10;
				duration = (duration < 0) ? 0 : duration;
			}
			congestedPlacesAndDuration.put(placeId, duration);
			boolean isNew = true;
			for (List<String> day : weeksHistory) {
				if (0 == day.size() || day.contains(placeId)) {
					isNew = false;
					break;
				}
			}
			if (isNew && duration <= 30) {
				isNewCongestedPlacesMap.put(placeId, true);
			}
		}
	}

	public boolean isUnunsualCongestion(Congestion congestion) {
		congestion.forEach(congestedPlace -> {
			congestion.setDuration(congestedPlacesAndDuration.get(congestedPlace.getPlaceId()));
			congestion.setTypeUnusual(isNewCongestedPlacesMap.get(congestedPlace.getPlaceId()));
		});
		return congestion.getType() == Congestion.CongestionType.UNUSUAL.getValue();
	}

}
