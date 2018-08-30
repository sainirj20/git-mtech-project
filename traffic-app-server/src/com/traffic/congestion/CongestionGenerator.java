package com.traffic.congestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.traffic.model.Place;
import com.traffic.places.PlacesGenerator;
import com.traffic.utils.DistanceUtil;

public class CongestionGenerator {
	private final PlacesGenerator placesGenerator = new PlacesGenerator();

	private final List<Set<Place>> groups = new ArrayList<>();

	public void generateCongestion() throws IOException {
		Map<String, Place> placesMap = placesGenerator.generatePlaces();

		List<Place> congestedPlaces = new ArrayList<Place>();

		for (Entry<String, Place> entry : placesMap.entrySet()) {
			Place place = entry.getValue();
			if (place.isPlaceCongested()) {
				congestedPlaces.add(place);
			}
		}

		groupCongestedPlaces(congestedPlaces);
		List<Set<Place>> smallCongestions = new ArrayList<>();
		List<Set<Place>> largeCongestions = new ArrayList<>();
		for (Set<Place> set : groups) {
			if (set.size() == 1)
				smallCongestions.add(set);
			else
				largeCongestions.add(set);

		}
		System.out.println("Congestion generated size :: " + congestedPlaces.size());
		System.out.println("Small congestions :: " + smallCongestions.size());
		System.out.println("large congestions :: " + largeCongestions.size());
	}

	private void groupCongestedPlaces(List<Place> congestedPlaces) {
		for (Place srcPlace : congestedPlaces) {
			Set<Place> set = contains(srcPlace);
			for (Place destPlace : congestedPlaces) {
				if (isPlaceNearBy(set, destPlace)) {
					set.add(destPlace);
				}
			}
			groups.add(set);
		}
	}

	private boolean isPlaceNearBy(Set<Place> set, Place destPlace) {
		for (Place place : set) {
			if (DistanceUtil.getDistance(place, destPlace) < 0.1) {
				return true;
			}
		}
		return false;
	}

	private Set<Place> contains(Place place) {
		for (Set<Place> set : groups) {
			if (set.contains(place)) {
				return set;
			}
		}
		Set<Place> set = new HashSet<>();
		set.add(place);
		return set;
	}

}
