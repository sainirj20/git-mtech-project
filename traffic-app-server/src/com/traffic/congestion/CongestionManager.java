package com.traffic.congestion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.traffic.model.Congestion;
import com.traffic.model.Place;
import com.traffic.utils.DistanceUtil;

public class CongestionManager {
	public enum CongestionType {
		SMALL, LARGE
	};

	// placeId to Congestion map
	private final Map<String, Congestion> congestions = new HashMap<>();

	public Congestion getCongestion(Place place) {
		if (congestions.containsKey(place.getPlaceId())) {
			return congestions.get(place.getPlaceId());
		} else {
			Congestion congestion = new Congestion(place);
			congestions.put(place.getPlaceId(), congestion);
			return congestion;
		}
	}

	public void addToCongestion(Congestion congestion, Place destPlace) {
		if (congestion.contains(destPlace)) {
			return;
		}
		for (Place place : congestion) {
			double distance = DistanceUtil.getDistance(place, destPlace);
			if (distance < 0.1) {
				congestion.add(destPlace);
				congestions.put(destPlace.getPlaceId(), congestion);
				return;
			} else if (distance > 3) {
				return;
			}
		}
	}

	public Map<CongestionType, List<Congestion>> getAllClusters() {
		Map<CongestionType, List<Congestion>> types = new HashMap<>();
		types.put(CongestionType.SMALL, new LinkedList<Congestion>());
		types.put(CongestionType.LARGE, new LinkedList<Congestion>());
		for (Congestion congestion : congestions.values()) {
			if (congestion.size() == 1)
				types.get(CongestionType.SMALL).add(congestion);
			else
				types.get(CongestionType.LARGE).add(congestion);
		}
		return types;
	}

}
