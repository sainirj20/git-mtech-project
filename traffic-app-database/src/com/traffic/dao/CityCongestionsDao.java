package com.traffic.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.traffic.dao.mongo.MongoConstants;
import com.traffic.model.Congestion;
import com.traffic.model.Place;

public class CityCongestionsDao implements MongoConstants {
	private final String collectionName = "CityCongestions";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;

	private Map<String, Place> placesMap = null;

	public CityCongestionsDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		placesMap = new PlacesDao().getAll();
	}

	public synchronized Document toDocument(Congestion congestion) {
		List<String> placeIds = new ArrayList<>();
		for (Place place : congestion) {
			placeIds.add(place.getPlaceId());
		}
		Document document = new Document();
		document.append(details, placeIds);
		return document;
	}

	@SuppressWarnings("unchecked")
	public synchronized Congestion fromDocument(Document document) {
		List<String> placeIds = (List<String>) document.getOrDefault(details, new ArrayList<String>());
		Congestion congestion = new Congestion();
		for (String placeId : placeIds) {
			congestion.add(placesMap.get(placeId));
		}
		return congestion;
	}

	public void drop() {
		collection.drop();
	}

	public void addAll(List<Congestion> congestionList) {
		for (Congestion congestion : congestionList) {
			Document d = toDocument(congestion);
			collection.insertOne(d);
		}
	}

	public List<Congestion> getAll() {
		List<Congestion> list = new ArrayList<>();
		FindIterable<Document> documents = collection.find();
		for (Document doc : documents) {
			Congestion congestion = fromDocument(doc);
			list.add(congestion);
		}
		return list;
	}
}
