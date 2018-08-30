package com.traffic.map;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.traffic.dao.PlacesDao;
import com.traffic.model.Place;

public class Marker {
	private final PlacesDao placesDao = new PlacesDao();

	public List<Place> get() {
		System.out.println("started");
		List<Place> result = new LinkedList<>();

		Map<String, Place> places = placesDao.getAll();

		for (Entry<String, Place> entry : places.entrySet()) {
			Place p = entry.getValue();
			if (p.isPlaceCongested()) {
				result.add(p);
			}

		}

		System.out.println(result.size());
		return result;
	}
}
