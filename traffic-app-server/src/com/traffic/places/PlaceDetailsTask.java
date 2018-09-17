package com.traffic.places;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.traffic.dao.PlacesDao;
import com.traffic.model.Place;
import com.traffic.utils.GoogleAPIsUtil;
import com.traffic.utils.URLBuilder;

public class PlaceDetailsTask {
	private class PlaceDetailsCallable implements Callable<Place> {
		private Place place = null;

		public PlaceDetailsCallable(Place place) {
			this.place = place;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Place call() throws Exception {
			if (null == place) {
				return null;
			}
			try {
				Map<String, Object> jsonFromUrl = GoogleAPIsUtil
						.getResponse(URLBuilder.getPlaceApiURL(place.getPlaceId()));
				Map<String, Object> result = (Map<String, Object>) jsonFromUrl.get("result");
				Map<String, Object> geometry = (Map<String, Object>) result.get("geometry");
				Map<String, Number> location = (Map<String, Number>) geometry.get("location");

				place.setLat(Double.parseDouble(location.get("lat").toString()));
				place.setLng(Double.parseDouble(location.get("lng").toString()));
				place.setAddress((String) result.get("formatted_address"));
			} catch (Exception e) {
				System.out.println(Thread.currentThread().getName() + " :: " + e.getMessage());
				return null;
			}
			return this.place;
		}
	}

	private final PlacesDao placesDao;

	public PlaceDetailsTask(PlacesDao placesDao) {
		this.placesDao = placesDao;
	}

	private final int threadPoolSize = 50;
	private ExecutorService executorService = null;
	private final List<Callable<Place>> callables = new ArrayList<Callable<Place>>();

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

	public void fetchPlacesDetails(List<Place> congestedPlaces) {
		congestedPlaces = congestedPlaces.stream().filter(place -> !place.hasLocationDetails())
				.collect(Collectors.toList());

		if (congestedPlaces.size() == 0) {
			return;
		}

		System.out.println("Fetching places details with congestion");
		executorService = Executors.newFixedThreadPool(threadPoolSize);
		int threadCtr = 0, index = 0;
		for (Place place : congestedPlaces) {
			if (threadCtr < threadPoolSize) {
				callables.add(new PlaceDetailsCallable(place));
				threadCtr++;
			}
			if (threadCtr == threadPoolSize) {
				System.out.println("processed :: " + index + " of " + congestedPlaces.size());
				executeThreadPool();
				threadCtr = 0;
			}
			index++;
		}
		executeThreadPool();
		executorService.shutdown();
	}
}
