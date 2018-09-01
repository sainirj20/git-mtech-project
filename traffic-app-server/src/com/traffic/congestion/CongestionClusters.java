package com.traffic.congestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.traffic.model.Place;
import com.traffic.utils.DistanceUtil;

public class CongestionClusters {
	private final List<Set<Place>> congestionGroups = new ArrayList<>();

	public Set<Place> getGroup(Place place) {
		for (Set<Place> group : congestionGroups) {
			if (group.contains(place)) {
				return group;
			}
		}
		Set<Place> group = new HashSet<>();
		congestionGroups.add(group);
		group.add(place);
		return group;
	}

	public void addToGroup(Set<Place> group, Place destPlace) {
		if (group.contains(destPlace)) {
			return;
		}
		for (Place place : group) {
			if (DistanceUtil.getDistance(place, destPlace) < 0.2) {
				group.add(destPlace);
				return;
			}
		}
	}
	
	public List<Set<Place>> pptimizeLargeCluster(List<Set<Place>> largeCongestions) {
		return congestionGroups;
	}

	public List<Set<Place>> getAllClusters() {
		return congestionGroups;
	}
}
