package com.traffic.places;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.traffic.dao.PlacesDao;
import com.traffic.model.Place;
import com.traffic.utils.GoogleAPIsUtil;
import com.traffic.utils.StopWatch;
import com.traffic.utils.URLBuilder;

public class PlacesGenerator {
	private final PlacesDao placesDao = new PlacesDao();
	private final Map<String, Place> placesMap;

	private final int threadPoolSize = 50;
	private ExecutorService executorService = null;
	private final List<Callable<Place>> callables = new ArrayList<Callable<Place>>();

	public PlacesGenerator() {
		placesMap = placesDao.getAll();
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

	private void fetchPlacesDetails() {
		int threadCtr = 0, index = 0;
		for (Entry<String, Place> entry : placesMap.entrySet()) {
			Place place = entry.getValue();
			if (!place.hasLocationDetails() && place.isPlaceCongested()) {
				if (threadCtr < threadPoolSize) {
					callables.add(new PlaceDetailsTask(place));
					threadCtr++;
				}
			}
			if (threadCtr == threadPoolSize) {
				System.out.println("executing batch :: " + index + " of " + placesMap.size());
				executeThreadPool();
				threadCtr = 0;
			}
			index++;
		}
		executeThreadPool();
	}

	private void executeThreadPool() {
		if (callables.size() == 0) {
			return;
		}
		try {
			List<Future<Place>> futures = executorService.invokeAll(callables);
			for (Future<Place> future : futures) {
				Place place = future.get();
				placesDao.addOrUpdate(place);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		callables.clear();
	}

	public Map<String, Place> generatePlaces() throws IOException {
		StopWatch stopWatch = new StopWatch();
		executorService = Executors.newFixedThreadPool(threadPoolSize);
		System.out.print("fetching freeflowSpeed... ");
		fetchFreeflowSpeed();
		System.out.println("FreeflowSpeed fetched :: " + stopWatch.lap());
		System.out.print("fetching currentSpeeds... ");
		fetchCurrentSpeeds();
		System.out.println("CurrentSpeeds fetched :: " + stopWatch.lap());
		System.out.print("generating places... ");
		fetchPlacesDetails();
		System.out.println("Places Generated :: " + placesMap.size() + " :: " + stopWatch.totalTime());
		executorService.shutdown();
		return placesMap;
	}
}
