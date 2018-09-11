package com.traffic.mapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.bson.Document;

import com.traffic.model.PlaceIdNode;
import com.traffic.mongo.Mapper;

public class PlacesGraphMapper implements Mapper<PlaceIdNode> {
	private final String placeId = "placeId";
	private final String next = "next";
	private final String previous = "previous";
	private final String processedIds = "processedIds";

	@Override
	public synchronized Document toDocument(PlaceIdNode node) {
		Document nodeDetails = new Document(placeId, node.getPlaceId());
		nodeDetails.append(next, node.getNext());
		nodeDetails.append(previous, node.getPrevious());
		nodeDetails.append(processedIds, node.getProcessedIds());
		Document document = new Document(id, node.getPlaceId());
		document.append(details, nodeDetails);
		return document;
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized PlaceIdNode fromDocument(Document document) {
		Document doc = document.get(details, Document.class);
		PlaceIdNode node = new PlaceIdNode(doc.getString(placeId));
		node.setNext(new LinkedHashSet<String>((List<String>) doc.getOrDefault(next, new ArrayList<String>())));
		node.setPrevious(new LinkedHashSet<String>((List<String>) doc.getOrDefault(previous, new ArrayList<String>())));
		node.setProcessedIds(
				new LinkedHashSet<String>((List<String>) doc.getOrDefault(processedIds, new ArrayList<String>())));
		return node;
	}

}
