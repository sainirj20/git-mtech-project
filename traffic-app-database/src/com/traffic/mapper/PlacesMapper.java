package com.traffic.mapper;

import org.bson.Document;

import com.traffic.model.Place;
import com.traffic.mongo.Mapper;

public class PlacesMapper implements Mapper<Place> {
	private final String placeId = "placeId";
	private final String lat = "lat";
	private final String lng = "lng";
	private final String address = "address";

	@Override
	public synchronized Document toDocument(Place place) {
		Document placeDetails = new Document(placeId, place.getPlaceId());
		placeDetails.append(lat, place.getLat());
		placeDetails.append(lng, place.getLng());
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
		place.setAddress(doc.getString(address));
		return place;
	}

}
