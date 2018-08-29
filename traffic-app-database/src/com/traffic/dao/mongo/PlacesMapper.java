package com.traffic.dao.mongo;

import org.bson.Document;

import com.traffic.model.Place;

public class PlacesMapper implements Mapper<Place> {
	private final String placeId = "placeId";
	private final String lat = "lat";
	private final String lng = "lng";
	private final String freeFlowSpeed = "freeFlowSpeed";
	private final String averageSpeed = "averageSpeed";
	private final String currentSpeed = "currentSpeed";
	private final String address = "address";

	@Override
	public synchronized Document toDocument(Place place) {
		Document placeDetails = new Document(placeId, place.getPlaceId());
		placeDetails.append(lat, place.getLat());
		placeDetails.append(lng, place.getLng());
		placeDetails.append(freeFlowSpeed, place.getFreeFlowSpeed());
		placeDetails.append(averageSpeed, place.getAverageSpeed());
		placeDetails.append(currentSpeed, place.getCurrentSpeed());
		placeDetails.append(address, place.getAddress());

		Document document = new Document(id, place.getPlaceId());
		document.append(details, placeDetails);
		return document;
	}

	@Override
	public synchronized Place fromDocument(Document document) {
		Document doc = document.get(details, Document.class);
		Place place = new Place(doc.getString(placeId));
		place.setLat(doc.getDouble(lat));
		place.setLng(doc.getDouble(lng));
		place.setFreeFlowSpeed(doc.getInteger(freeFlowSpeed));
		place.setAverageSpeed(doc.getInteger(averageSpeed));
		place.setCurrentSpeed(doc.getInteger(currentSpeed));
		place.setAddress(doc.getString(address));
		return place;
	}

}
