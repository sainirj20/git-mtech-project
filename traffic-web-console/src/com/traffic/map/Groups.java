package com.traffic.map;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.traffic.dao.CityCongestionsDao;
import com.traffic.model.Congestion;
import com.traffic.model.Place;

public class Groups {
	private CityCongestionsDao clustersDao = new CityCongestionsDao();

	private Set<Place> smallCongestions = new HashSet<>();
	private Set<Place> largeCongestions = new HashSet<>();
	private Set<Place> unUnsualCongestions = new HashSet<>();

	public void init() {
		List<Congestion> groups = clustersDao.getAll();
		for (Congestion congestion : groups) {
			if (congestion.getType() == Congestion.CongestionType.SMALL.getValue())
				smallCongestions.addAll(congestion);
			else if (congestion.getType() == Congestion.CongestionType.LARGE.getValue()) {
				int ctr = 0;
				for (Place place : congestion) {
					if (ctr % 3 == 0)
						largeCongestions.add(place);
				}
			} else
				unUnsualCongestions.addAll(congestion);
		}
	}

	public Set<Place> getSmallCongestions() {
		System.out.println("small Congestions list :: " + smallCongestions.size());
		return smallCongestions;
	}

	public Set<Place> getLargeCongestions() {
		System.out.println("large Congestions list :: " + largeCongestions.size());
		return largeCongestions;
	}

	public Set<Place> getUnUnsualCongestions() {
		System.out.println("Un-unsual Congestions list :: " + unUnsualCongestions.size());
		return unUnsualCongestions;
	}
}
