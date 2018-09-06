package com.traffic.model;

import java.util.HashSet;

public final class Congestion extends HashSet<Place> {
	public enum CongestionType {
		SMALL("SMALL"), LARGE("LARGE"), UNUSUAL("UNUSUAL");

		private final String text;

		CongestionType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

//	public enum CongestionType {
//		SMALL, LARGE, UNUSUAL;
//	};

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
		if (isUnsual) {
			type = CongestionType.UNUSUAL;
		}
	}

	public void setDuration(int duration) {
		if (this.duration < duration) {
			this.duration = duration;
		}
	}

	public CongestionType getType() {
		return type;
	}

	public int getTypeInInteger() {
		return type.ordinal();
	}

	public int getDuration() {
		return duration;
	}

}
