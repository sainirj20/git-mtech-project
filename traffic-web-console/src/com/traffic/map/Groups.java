package com.traffic.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.traffic.dao.CityCongestionsDao;
import com.traffic.model.Congestion;
import com.traffic.model.Place;

public class Groups {
	private CityCongestionsDao clustersDao = new CityCongestionsDao();

	private List<Set<Place>> smallCongestions = new ArrayList<>();
	private List<Set<Place>> largeCongestions = new ArrayList<>();

	public void init() {
		List<Congestion> groups = clustersDao.getAll();
		for (Set<Place> set : groups) {
			if (set.size() == 1)
				smallCongestions.add(set);
			else
				largeCongestions.add(set);
		}
	}

	public List<Place> getSmallCongestions() {
		List<Place> list = new LinkedList<>();
		for (Set<Place> set : smallCongestions) {
			for (Place place : set) {
				list.add(place);
			}
		}
		System.out.println("small Congestions list :: " + list.size());
		return list;
	}

	public List<Place> getLargeCongestions() {
		Set<Place> list = new HashSet<>();
		int index = 0;
		for (Set<Place> set : largeCongestions) {
			index = 0;
			for (Place place : set) {
				if (index++ % 3 == 0)
					list.add(place);

			}
		}
		System.out.println("large Congestions list :: " + list.size());
		return new LinkedList<>(list);
	}
}
