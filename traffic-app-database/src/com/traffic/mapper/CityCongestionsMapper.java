package com.traffic.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.traffic.model.Congestion;
import com.traffic.model.Place;
import com.traffic.mongo.Mapper;

public class CityCongestionsMapper implements Mapper<Congestion> {
	private final String placeIdList = "placeIdList";
	private final String duration = "duration";
	private final String type = "type";

	private Map<String, Place> placesMap = null;

	public CityCongestionsMapper(Map<String, Place> placesMap) {
		this.placesMap = placesMap;
	}

	@Override
	public Document toDocument(Congestion congestion) {
		List<String> placeIds = new ArrayList<>();
		for (Place place : congestion) {
			placeIds.add(place.getPlaceId());
		}
		Document congestionDetails = new Document(placeIdList, placeIds);
		congestionDetails.append(duration, congestion.getDuration());
		congestionDetails.append(type, congestion.getType());

		Document document = new Document();
		document.append(details, congestionDetails);
		return document;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Congestion fromDocument(Document document) {
		Document doc = document.get(details, Document.class);
		List<String> placeIds = (List<String>) doc.getOrDefault(placeIdList, new ArrayList<String>());
		Congestion congestion = new Congestion();
		for (String placeId : placeIds) {
			congestion.add(placesMap.get(placeId));
		}
		congestion.setDuration(doc.getInteger(duration, 0));
		congestion.setType(doc.getInteger(type, 0));
		return congestion;
	}

}
