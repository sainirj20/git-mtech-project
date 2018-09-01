package com.traffic.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.traffic.congestion.CongestionGenerator;
import com.traffic.dao.CongestionClustersDao;
import com.traffic.model.Place;

public class Groups {
	private CongestionClustersDao clustersDao = new CongestionClustersDao();

	private List<Set<Place>> smallCongestions = new ArrayList<>();
	private List<Set<Place>> largeCongestions = new ArrayList<>();

	public void init() {
		List<Set<Place>> groups = clustersDao.getAll();
		for (Set<Place> set : groups) {
			if (set.size() == 1)
				smallCongestions.add(set);
			else
				largeCongestions.add(set);
		}
		System.out.println("smallCongestions :: " +smallCongestions.size());
		System.out.println("largeCongestions :: " +largeCongestions.size());
	}
	
	public void initFormGenerator() {
		CongestionGenerator congestionGenerator = new CongestionGenerator();
		try {
			congestionGenerator.generateCongestion();
			smallCongestions = congestionGenerator.getSmallCongestions();
			largeCongestions = congestionGenerator.getLargeCongestions();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Place> getSmallCongestions() {
		List<Place> list = new LinkedList<>();
		for (Set<Place> set : smallCongestions) {
			for (Place place : set) {
				list.add(place);
			}
		}
		System.out.println("small Congestions list :: " +list.size());
		return list;
	}

	public List<Place> getLargeCongestions() {
		List<Place> list = new LinkedList<>();
		for (Set<Place> set : largeCongestions) {
			for (Place place : set) {
				list.add(place);
			}
		}
		System.out.println("large Congestions list :: " +list.size());
		return list;
	}
}
