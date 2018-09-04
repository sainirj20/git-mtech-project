package com.traffic.congestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.traffic.dao.CityCongestionsDao;
import com.traffic.dao.CongestionHistoryDao;
import com.traffic.model.Congestion;
import com.traffic.model.Place;
import com.traffic.places.PlacesGenerator;

public class CityCongestions {
	private final Map<String, Congestion> allCityCongestions = new HashMap<>();

	public void generateCongestion() throws IOException {
		System.out.println(":: CityCongestions ::");
		
		// get congested places
		List<Place> congestedPlaces = getCongestedPlaces();
		
		//save congested places to Congestion History
		CongestionHistoryDao congestionHistoryDao= new CongestionHistoryDao();
		congestionHistoryDao.insertOrUpdate(congestedPlaces);

		// group them into small and large congestion(s)
		CityCongestionsHelper helper = new CityCongestionsHelper(allCityCongestions);
		helper.groupCongestedPlaces(congestedPlaces);

		// set congestion type
		setCongestionType();

		// save city Congestions to db
		System.out.println("saving congestions to db...");
		CityCongestionsDao congestionsDao = new CityCongestionsDao();
		congestionsDao.drop();
		congestionsDao.addAll(new LinkedList<Congestion>(allCityCongestions.values()));
	}
	
	private List<Place> getCongestedPlaces() throws IOException {
		Map<String, Place> placesMap = new PlacesGenerator().generatePlaces();

		// get congested places
		List<Place> congestedPlaces = new ArrayList<Place>();
		placesMap.forEach((placeId, place) -> {
			if (place.isPlaceCongested() && place.hasLocationDetails()) {
				congestedPlaces.add(place);
			}
		});
		return congestedPlaces;
	}

	private void setCongestionType() {
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
	}
}
