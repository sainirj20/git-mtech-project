package com.traffic.congestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.traffic.dao.CongestionClustersDao;
import com.traffic.model.Place;
import com.traffic.places.PlacesGenerator;

public class CongestionGenerator {

	private CongestionClusters clusters = new CongestionClusters();
	private List<Set<Place>> smallCongestions = new ArrayList<>();
	private List<Set<Place>> largeCongestions = new ArrayList<>();

	private Map<String, Place> placesMap = null;

	public List<Set<Place>> getSmallCongestions() {
		return smallCongestions;
	}

	public List<Set<Place>> getLargeCongestions() {
		return largeCongestions;
	}

	public void generateCongestion() throws IOException {
		placesMap = new PlacesGenerator().generatePlaces();
		List<Place> congestedPlaces = new ArrayList<Place>();

		for (Entry<String, Place> entry : placesMap.entrySet()) {
			Place place = entry.getValue();
			if (place.isPlaceCongested() && place.hasLocationDetails()) {
				congestedPlaces.add(place);
			}
		}
		groupCongestedPlaces(congestedPlaces);
		List<Set<Place>> groups = clusters.getAllClusters();
		CongestionClustersDao clustersDao = new CongestionClustersDao();
		clustersDao.addAll(groups);
		for (Set<Place> set : groups) {
			if (set.size() == 1)
				smallCongestions.add(set);
			else
				largeCongestions.add(set);

		}
		System.out.println("Small congestions :: " + smallCongestions.size());
		System.out.println("large congestions :: " + largeCongestions.size());
	}

	private void groupCongestedPlaces(List<Place> congestedPlaces) {
		int size = congestedPlaces.size();
		System.out.println("congested Places found :: " + size);
		System.out.println("gropuing congested places...");

		int ctr = 0;
		for (Place srcPlace : congestedPlaces) {
			if (ctr++ % 500 == 0) {
				System.out.println("gropuing :: " + ctr + " of " + congestedPlaces.size());
			}
			Set<Place> set = clusters.getGroup(srcPlace);
			for (Place destPlace : congestedPlaces) {
				clusters.addToGroup(set, destPlace);
			}
		}
	}
}
