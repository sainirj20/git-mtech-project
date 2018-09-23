package com.traffic.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.traffic.dao.CityCongestionsDao;
import com.traffic.model.Congestion;
import com.traffic.model.Place;

public class Groups {
	private CityCongestionsDao clustersDao = new CityCongestionsDao();

	private List<Congestion> groups = new ArrayList<>();

	private Set<Place> smallCongestions = new HashSet<>();
	private Set<Place> largeCongestions = new HashSet<>();
	private Set<Place> unUnsualCongestions = new HashSet<>();

	public boolean hasNewCongestion() {
		if (null != groups && groups.size() > 0) {
			return clustersDao.hasNewCongestion(groups.get(0).getKey());
		}
		return true;
	}

	public void init() {
		groups = clustersDao.getAll();
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

	private String getCongestionsList(Set<Place> congestionsSet) {
		if (congestionsSet.size() == 0) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder("[\n");
		Iterator<Place> itr = congestionsSet.iterator();
		while (itr.hasNext()) {
			Place p = itr.next();
			sb.append("\t[" + p.getLat() + ", " + p.getLng() + ", \"" + p.getAddress() + "\"] ,\n");
		}
		sb.deleteCharAt(sb.length() - 2);
		return sb.append("]").toString();
	}

	public String getSmallCongestions() {
		return getCongestionsList(smallCongestions);
	}

	public String getLargeCongestions() {
		return getCongestionsList(largeCongestions);
	}

	public String getUnUnsualCongestions() {
		return getCongestionsList(unUnsualCongestions);
	}
}
