package com.traffic.congestion;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.traffic.dao.CityCongestionsDao;
import com.traffic.dao.CongestionHistoryDao;
import com.traffic.history.CongestionHistory;
import com.traffic.model.Congestion;
import com.traffic.model.Place;
import com.traffic.places.PlacesService;
import com.traffic.utils.PropertiesUtil;

public class CityCongestionsService {
	private final PlacesService placesService = new PlacesService();
	private final Map<String, Congestion> allCityCongestions = new HashMap<>();

	private CongestionHistory congestionHistory;

	public void processCongestions() throws IOException {
		System.out.println(":: CityCongestions ::");

		// get congested places.
		List<Place> congestedPlaces = placesService.getCongestedPlaces();

		// save congested places to Congestion History
		CongestionHistoryDao congestionHistoryDao = new CongestionHistoryDao();
		congestionHistoryDao.insertOrUpdate(congestedPlaces);

		// check for server mode
		Boolean serverMode = Boolean.parseBoolean(PropertiesUtil.getPropertyValue("server.save.only.history"));
		if (serverMode) {
			System.out.println("server.save.only.history is ON skipping the rest");
			return;
		}

		// group them into small and large congestion(s)
		congestionHistory = new CongestionHistory(congestedPlaces);
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

	private void setCongestionType() {
		int small = 0, large = 0, unUsual = 0;
		for (Congestion congestion : allCityCongestions.values()) {
			Congestion.CongestionType type = congestion.setType();
			if (type == Congestion.CongestionType.SMALL) {
				small++;
			} else {
				large++;
			}
			if (congestionHistory.isUnunsualCongestion(congestion)) {
				unUsual++;
				large--;
			}
		}
		System.out.println("Small congestions :: " + small);
		System.out.println("Large congestions :: " + large);
		System.out.println("UnUsual congestions :: " + unUsual);
	}

}
