package com.traffic.model;

import java.util.HashSet;

public final class Congestion extends HashSet<Place> {
	public enum CongestionType {
		SMALL(0), LARGE(1), UNUSUAL(2);
		private final int value;

		private CongestionType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	private static final long serialVersionUID = -4037137473313165293L;
	private int duration = 0; // in mins
	private CongestionType type = null;

	public Congestion(Place place) {
		this.add(place);
	}

	public Congestion() {
	}

	public void setType(Integer type) {
		this.type = CongestionType.values()[type];
	}

	public CongestionType setType() {
		type = (size() == 1) ? CongestionType.SMALL : CongestionType.LARGE;
		return type;
	}

	public void setTypeUnusual(boolean isUnsual) {
		if (isUnsual && CongestionType.SMALL != type) {
			type = CongestionType.UNUSUAL;
		}
	}

	public void setDuration(int duration) {
		if (this.duration < duration) {
			this.duration = duration;
		}
	}

	public Integer getType() {
		return type.getValue();
	}

	public int getDuration() {
		return duration;
	}

}
