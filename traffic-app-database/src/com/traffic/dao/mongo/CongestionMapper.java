package com.traffic.dao.mongo;

import java.util.LinkedList;

import org.bson.Document;

import com.traffic.model.Congestion;
import com.traffic.model.Place;

public class CongestionMapper implements Mapper<Congestion> {

	private final String congestionId = "congestionId";
	private final String firstPlace = "firstPlace";
	private final String lastPlace = "lastPlace";
	private final String status = "status";
	private final String isUsualCongestion = "isUsualCongestion";
	private final String startTime = "startTime";
	private final String lastUpdatedTime = "lastUpdatedTime";
	private final String lastUpdatedBy = "lastUpdatedBy";
	private final String congestionDuration = "congestionDuration";
	private final String delayCaused = "delayCaused";
	private final String congestionDistance = "congestionDistance";
	private final String averageSpeed = "averageSpeed";
	private final String congestedPlaces = "congestedPlaces";
	private final String resolvedPlaces = "resolvedPlaces";

	private final Mapper<Place> mapper = new PlacesMapper();

	@Override
	public Document toDocument(Congestion congestion) {
		Document congestionDetails = new Document(congestionId, congestion.getCongestionId());
		congestionDetails.append(firstPlace,
				mapper.toDocument(congestion.getFirstPlace()).get(details, Document.class));
		congestionDetails.append(lastPlace, mapper.toDocument(congestion.getLastPlace()).get(details, Document.class));
		congestionDetails.append(status, congestion.getStatus());
		congestionDetails.append(isUsualCongestion, congestion.getIsUsualCongestion());
		congestionDetails.append(startTime, congestion.getStartTime());

		congestionDetails.append(lastUpdatedTime, congestion.getLastUpdatedTime());
		congestionDetails.append(lastUpdatedBy, congestion.getLastUpdatedBy());
		congestionDetails.append(congestionDuration, congestion.getCongestionDuration());
		congestionDetails.append(delayCaused, congestion.getDelayCaused());
		congestionDetails.append(congestionDistance, congestion.getCongestionDistance());

		congestionDetails.append(averageSpeed, congestion.getAverageSpeed());
		congestionDetails.append(congestedPlaces, congestion.getCongestedPlaces());
		congestionDetails.append(resolvedPlaces, congestion.getResolvedPlaces());

		Document document = new Document(id, congestion.getCongestionId());
		document.append(details, congestionDetails);
		return document;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Congestion fromDocument(Document document) {
		Document doc = document.get(details, Document.class);
		Congestion congestion = new Congestion(doc.getString(congestionId));
		congestion.setFirstPlace(mapper.fromDocument(document.get(firstPlace, Document.class)));
		congestion.setLastPlace(mapper.fromDocument(document.get(lastPlace, Document.class)));
		congestion.setStatus(doc.get(status, Congestion.AlertStatus.class));
		congestion.setIsUsualCongestion(doc.getBoolean(isUsualCongestion));
		congestion.setStartTime(doc.getLong(startTime));
		congestion.setLastUpdatedTime(doc.getLong(lastUpdatedTime));
		congestion.setLastUpdatedBy(doc.getString(lastUpdatedBy));
		congestion.setCongestionDuration(doc.getInteger(congestionDuration));
		congestion.setDelayCaused(doc.getInteger(delayCaused));
		congestion.setCongestionDistance(doc.getInteger(congestionDistance));
		congestion.setAverageSpeed(doc.getInteger(averageSpeed));
		congestion.setCongestedPlaces((LinkedList<String>) doc.getOrDefault(congestedPlaces, new LinkedList<String>()));
		congestion.setResolvedPlaces((LinkedList<String>) doc.getOrDefault(resolvedPlaces, new LinkedList<String>()));
		return congestion;
	}
}
