package com.traffic.congestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.traffic.congestion.CongestionManager.CongestionType;
import com.traffic.dao.CityCongestionsDao;
import com.traffic.model.Congestion;
import com.traffic.model.Place;
import com.traffic.places.PlacesGenerator;
import com.traffic.utils.StopWatch;

public class CongestionGenerator {
	private CongestionManager manager = new CongestionManager();
	private List<Congestion> smallCongestions = new ArrayList<>();
	private List<Congestion> largeCongestions = new ArrayList<>();

	public List<Congestion> getSmallCongestions() {
		return smallCongestions;
	}

	public List<Congestion> getLargeCongestions() {
		return largeCongestions;
	}

	public void generateCongestion() throws IOException {
		System.out.println(":: CongestionGenerator2 ::");
		Map<String, Place> placesMap = new PlacesGenerator().generatePlaces();
		List<Place> congestedPlaces = new ArrayList<Place>();

		for (Entry<String, Place> entry : placesMap.entrySet()) {
			Place place = entry.getValue();
			if (place.isPlaceCongested() && place.hasLocationDetails()) {
				congestedPlaces.add(place);
			}
		}
		groupCongestedPlaces(congestedPlaces);
		Map<CongestionType, List<Congestion>> types = manager.getAllClusters();
		
		CityCongestionsDao congestionsDao = new CityCongestionsDao();
		congestionsDao.drop();
		congestionsDao.addAll(types.get(CongestionManager.CongestionType.SMALL));
		congestionsDao.addAll(types.get(CongestionManager.CongestionType.LARGE));

		System.out.println("Small congestions :: " + types.get(CongestionManager.CongestionType.SMALL).size());
		System.out.println("large congestions :: " + types.get(CongestionManager.CongestionType.LARGE).size());
	}

	private void groupCongestedPlaces(List<Place> congestedPlaces) {
		int size = congestedPlaces.size();
		System.out.println("congested Places found :: " + size);
		System.out.println("gropuing congested places...");

		int ctr = 0;
		StopWatch watch = new StopWatch();
		for (Place srcPlace : congestedPlaces) {
			if (ctr++ % 500 == 0) {
				System.out.println("gropuing :: " + ctr + " of " + congestedPlaces.size() + " :: " + watch.lap());
			}
			Congestion congestion = manager.getCongestion(srcPlace);
			for (Place destPlace : congestedPlaces) {
				manager.addToCongestion(congestion, destPlace);
			}
		}
	}
}
