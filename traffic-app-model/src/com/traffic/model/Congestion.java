package com.traffic.model;

import java.util.HashSet;
import java.util.Set;

public final class Congestion extends HashSet<Place> {
	private Boolean isUsualCongestion;
	private Long startTime;
	private Long lastUpdatedTime;

	public Congestion(Place place) {
		this.add(place);
	}
	
	public Congestion() {
	}

}
