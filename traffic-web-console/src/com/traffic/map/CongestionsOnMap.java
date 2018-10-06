package com.traffic.map;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.traffic.model.Congestion;
import com.traffic.model.Place;

public class CongestionsOnMap {
	private CityCongestionsBackEnd backEnd = CityCongestionsBackEnd.getInstance();
	private Set<Place> smallCongestions = new HashSet<>();
	private Set<Place> largeCongestions = new HashSet<>();
	private Set<Place> unUnsualCongestions = new HashSet<>();

	public boolean hasNewCongestion() {
		return backEnd.hasNewCongestion();
	}

	public void reload() {
		List<Congestion> groups = backEnd.getGroups();
		for (Congestion congestion : groups) {
			if (congestion.getType() == Congestion.CongestionType.SMALL.getValue())
				smallCongestions.addAll(congestion);
			else if (congestion.getType() == Congestion.CongestionType.LARGE.getValue()) {
				int ctr = 0;
				int modFactor = (congestion.size() < 10) ? 3 : (congestion.size() < 50) ? 4 : 5;
				for (Place place : congestion) {
					if (ctr++ % modFactor == 0)
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
			sb.append("\t" + getCongestionsArray(p) + " ,\n");
		}
		sb.deleteCharAt(sb.length() - 2);
		return sb.append("]").toString();
	}

	private String getCongestionsArray(Place p) {
		StringBuilder sb = new StringBuilder("[");
		sb.append(p.getLat() + ", ");
		sb.append(p.getLng() + ", ");
		sb.append("\"" + p.getShortAddress() + "\", ");
		sb.append("\"" + p.getPlaceId() + "\"");
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
