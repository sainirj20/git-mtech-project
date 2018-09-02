package com.traffic.congestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.traffic.dao.CityCongestionsDao;
import com.traffic.model.Congestion;
import com.traffic.model.Place;
import com.traffic.places.PlacesGenerator;
import com.traffic.utils.DistanceUtil;
import com.traffic.utils.StopWatch;

public class CityCongestions {
	private final Map<String, Congestion> allCityCongestions = new HashMap<>();

	public void generateCongestion() throws IOException {
		System.out.println(":: CityCongestions ::");
		Map<String, Place> placesMap = new PlacesGenerator().generatePlaces();

		// get congested places
		List<Place> congestedPlaces = new ArrayList<Place>();
		placesMap.forEach((placeId, place) -> {
			if (place.isPlaceCongested() && place.hasLocationDetails()) {
				congestedPlaces.add(place);
			}
		});

		// group them into small and large congestion(s)
		groupCongestedPlaces(congestedPlaces);

		// set congestion type
		int small = 0, large = 0;
		for (Congestion congestion : allCityCongestions.values()) {
			Congestion.CongestionType type = congestion.setType();
			if (type == Congestion.CongestionType.SMALL) {
				small++;
			} else {
				large++;
			}
		}
		System.out.println("Small congestions :: " + small);
		System.out.println("large congestions :: " + large);

		// save city Congestions to db
		CityCongestionsDao congestionsDao = new CityCongestionsDao();
		congestionsDao.drop();
		congestionsDao.addAll(new LinkedList<Congestion>(allCityCongestions.values()));
	}

	// ---------------------- grouping congested Places --------------------------

	private void groupCongestedPlaces(List<Place> congestedPlaces) {
		System.out.println("congested Places found :: " + congestedPlaces.size());
		System.out.println("gropuing congested places...");

		int ctr = 0;
		StopWatch watch = new StopWatch();
		for (Place srcPlace : congestedPlaces) {
			if (ctr++ % 500 == 0) {
				System.out.println("gropuing :: " + ctr + " of " + congestedPlaces.size() + " :: " + watch.lap());
			}
			Congestion congestion = getCongestion(srcPlace);
			congestedPlaces.forEach(destPlace -> addToCongestion(congestion, destPlace));
		}
	}

	private Congestion getCongestion(Place place) {
		if (allCityCongestions.containsKey(place.getPlaceId())) {
			return allCityCongestions.get(place.getPlaceId());
		} else {
			Congestion congestion = new Congestion(place);
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
	// --------------------------------------------------------------------------------
}
