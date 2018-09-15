package com.traffic.places;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.traffic.dao.PlacesDao;
import com.traffic.log.MyLogger;
import com.traffic.model.Place;
import com.traffic.utils.GoogleAPIsUtil;
import com.traffic.utils.StopWatch;
import com.traffic.utils.URLBuilder;

public class PlacesService {
	private final Logger logger = MyLogger.getLogger(PlacesService.class.getName());

	private final PlacesDao placesDao = new PlacesDao();
	private final PlaceDetailsTask detailsTask;
	private Map<String, Place> placesMap;

	public PlacesService() {
		placesMap = placesDao.getAll();
		detailsTask = new PlaceDetailsTask(placesDao);
	}

	@SuppressWarnings("unchecked")
	private void fetchFreeflowSpeed() throws IOException {
		Map<String, Object> response = GoogleAPIsUtil
				.getResponse(URLBuilder.getRoadApiURL(732, 474, URLBuilder.RoadApi.freeFlow));

		List<String> placeIds = (List<String>) response.get("placeIds");
		List<Integer> freeFlowSpeeds = (List<Integer>) response.get("freeflowSpeeds");
		for (int index = 0; index < placeIds.size(); index++) {
			String placeId = placeIds.get(index);
			if (placesMap.containsKey(placeId)) {
				Place place = placesMap.get(placeId);
				place.setFreeFlowSpeed(freeFlowSpeeds.get(index));
			} else {
				Place place = new Place(placeId);
				place.setFreeFlowSpeed(freeFlowSpeeds.get(index));
				placesMap.put(place.getPlaceId(), place);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void fetchCurrentSpeeds() throws IOException {
		Map<String, Object> response = GoogleAPIsUtil
				.getResponse(URLBuilder.getRoadApiURL(732, 474, URLBuilder.RoadApi.current));

		List<String> placeIds = (List<String>) response.get("placeIds");
		List<Integer> currentSpeeds = (List<Integer>) response.get("currentSpeeds");
		for (int index = 0; index < placeIds.size(); index++) {
			String placeId = placeIds.get(index);
			if (placesMap.containsKey(placeId)) {
				Place place = placesMap.get(placeId);
				if (place == null) {
					System.out.println(placeId);
				}
				place.setCurrentSpeed(currentSpeeds.get(index));
			} else {
				Place place = new Place(placeId);
				place.setCurrentSpeed(currentSpeeds.get(index));
				placesMap.put(place.getPlaceId(), place);
			}
		}
	}

	public List<Place> getCongestedPlaces() throws IOException {
		StopWatch stopWatch = new StopWatch();
		fetchFreeflowSpeed();
		logger.log(Level.INFO, "FreeflowSpeed fetched :: " + stopWatch.lap());
		fetchCurrentSpeeds();
		logger.log(Level.INFO, "CurrentSpeeds fetched :: " + stopWatch.lap());

		// getting only congested places
		List<Place> congestedPlaces = new LinkedList<>();
		placesMap.forEach((placeId, place) -> {
			if (place.isPlaceCongested()) {
				congestedPlaces.add(place);
			}
		});
		detailsTask.fetchPlacesDetails(congestedPlaces);

		logger.log(Level.INFO, "Congested Places found :: " + congestedPlaces.size() + " :: " + stopWatch.lap());
		return congestedPlaces;
	}

}
