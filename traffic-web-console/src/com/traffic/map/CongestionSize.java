package com.traffic.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.traffic.model.Congestion;
import com.traffic.model.Place;

public class CongestionSize {
	private CityCongestionsBackEnd backEnd = CityCongestionsBackEnd.getInstance();
	private Map<Congestion.CongestionType, Integer> congestions = new HashMap<>();

	public Map<Congestion.CongestionType, Integer> getCongestionsCount() {
		List<Congestion> groups = backEnd.getGroups();
		Set<Place> small = new HashSet<>(), large = new HashSet<>(), unusual = new HashSet<>();
		for (Congestion congestion : groups) {
			if (congestion.getType() == Congestion.CongestionType.SMALL.getValue())
				small.addAll(congestion);
			else if (congestion.getType() == Congestion.CongestionType.LARGE.getValue())
				large.addAll(congestion);
			else if (congestion.getType() == Congestion.CongestionType.UNUSUAL.getValue())
				unusual.addAll(congestion);
		}
		congestions.put(Congestion.CongestionType.SMALL, small.size());
		congestions.put(Congestion.CongestionType.LARGE, large.size());
		congestions.put(Congestion.CongestionType.UNUSUAL, unusual.size());
		return congestions;
	}

}
