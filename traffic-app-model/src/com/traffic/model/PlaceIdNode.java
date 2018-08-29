package com.traffic.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class PlaceIdNode {
	private String placeId;
	private Set<String> next = new LinkedHashSet<String>();
	private Set<String> previous = new LinkedHashSet<String>();
	private Set<String> processedIds = new LinkedHashSet<String>();
	
	public PlaceIdNode(String placeId) {this.placeId = placeId;}

	// ---------------------------------------------------------------------------
	
	public void setNext(Set<String> next) {	this.next = next;}
	public void setPrevious(Set<String> previous) {	this.previous = previous;}
	public void setProcessedIds(Set<String> processedIds) { this.processedIds = processedIds;}
	
	public String getPlaceId() {return placeId;}
	public Set<String> getNext() {return next;}
	public Set<String> getPrevious() {return previous;}
	public Set<String> getProcessedIds() { return processedIds; }

	// ---------------------------------------------------------------------------
	
	public void addNext(String placeId) {
		this.next.add(placeId);
	}

	public void addPrevious(String placeId) {
		this.previous.add(placeId);
	}
	
	public void addProcessedIds(String placeId) {
		this.processedIds.add(placeId);
	}

	public void reset() {
		next.clear();
		previous.clear();
	}
	
	@Override
	public String toString() {
		return "PlaceIdNode [placeId=" + placeId + ", next=" + next.size() + ", previous=" + previous.size() + "]";
	}
}