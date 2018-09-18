package com.traffic.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CongestionHistory {

	private String historyKey;
	private Set<String> congestedPlaces;
	
	public String getHistoryKey() {
		return historyKey;
	}

	public Set<String> getCongestedPlaces() {
		return congestedPlaces;
	}

	public void setHistoryKey(String historyKey) {
		this.historyKey = historyKey;
	}

	public void setCongestedPlaces(Set<String> congestedPlaces) {
		this.congestedPlaces = congestedPlaces;
	}

	public void addAll(Collection<? extends String> collection) {
		this.congestedPlaces = new HashSet<>();
		this.congestedPlaces.addAll(collection);
	}

	public int size() {
		return this.congestedPlaces.size();
	}

	public boolean contains(String placeId) {
		return this.congestedPlaces.contains(placeId);
	}

}
