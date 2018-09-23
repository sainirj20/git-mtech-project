package com.traffic.congestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.traffic.model.Congestion;
import com.traffic.model.Place;
import com.traffic.utils.DistanceUtil;
import com.traffic.utils.StopWatch;

class CityCongestionsHelper {
	private Map<String, Congestion> allCityCongestions;
	private final String congestionKey;
	
	public CityCongestionsHelper(String key) {
		congestionKey = key;
	}

	Map<String, Congestion> groupCongestedPlaces(List<Place> congestedPlaces) {
		System.out.println("gropuing congested places...");
		allCityCongestions = new HashMap<>();
		int ctr = 0;
		StopWatch watch = new StopWatch();
		for (Place srcPlace : congestedPlaces) {
			if (ctr++ % 500 == 0) {
				System.out.println("gropuing :: " + ctr + " of " + congestedPlaces.size() + " :: " + watch.lap());
			}
			Congestion congestion = getCongestion(srcPlace);
			congestedPlaces.forEach(destPlace -> addToCongestion(congestion, destPlace));
		}
		return allCityCongestions;
	}

	private Congestion getCongestion(Place place) {
		if (allCityCongestions.containsKey(place.getPlaceId())) {
			return allCityCongestions.get(place.getPlaceId());
		} else {
			Congestion congestion = new Congestion(place);
			congestion.setKey(congestionKey);
			allCityCongestions.put(place.getPlaceId(), congestion);
			return congestion;
		}
	}

	private void addToCongestion(Congestion congestion, Place destPlace) {
		if (congestion.contains(destPlace)) {
			return;
		}
		for (Place place : congestion) {
			double distance = DistanceUtil.getDistance(place, destPlace);
			if (distance < 0.1) {
				congestion.add(destPlace);
				allCityCongestions.put(destPlace.getPlaceId(), congestion);
				return;
			} else if (distance > 3) {
				return;
			}
		}
	}
}
