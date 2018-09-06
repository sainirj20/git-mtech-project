package com.traffic.model;

import java.util.HashSet;

public final class Congestion extends HashSet<Place> {
	private static final long serialVersionUID = -4037137473313165293L;

	private int duration = 0; // in mins

	public enum CongestionType {
		SMALL, LARGE, UNUSUAL
	};

	private CongestionType type = null;

	public Congestion(Place place) {
		this.add(place);
	}

	public Congestion() {
	}

	public CongestionType setType() {
		type = (size() == 1) ? CongestionType.SMALL : CongestionType.LARGE;
		return type;
	}

	public void setTypeUnusual() {
		type = CongestionType.UNUSUAL;
	}

	public void setDuration(int duration) {
		if (this.duration < duration) {
			this.duration = duration;
		}
	}

	public CongestionType getType() {
		return type;
	}

	public int getDuration() {
		return duration;
	}

}
