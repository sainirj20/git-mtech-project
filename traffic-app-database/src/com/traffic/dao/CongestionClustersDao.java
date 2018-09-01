package com.traffic.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.traffic.dao.mongo.MongoConstants;
import com.traffic.model.Place;

public class CongestionClustersDao implements MongoConstants {
	private final String collectionName = "CongestionClusters";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;

	private Map<String, Place> placesMap = null;

	public CongestionClustersDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		placesMap = new PlacesDao().getAll();
	}

	public synchronized Document toDocument(Set<Place> set) {
		List<String> placeIds = new ArrayList<>();
		for (Place place : set) {
			placeIds.add(place.getPlaceId());
		}
		Document document = new Document();
		document.append(details, placeIds);
		return document;
	}

	@SuppressWarnings("unchecked")
	public synchronized Set<Place> fromDocument(Document document) {
		List<String> placeIds = (List<String>) document.getOrDefault(details, new ArrayList<String>());
		Set<Place> set = new HashSet<>();
		for (String placeId : placeIds) {
			set.add(placesMap.get(placeId));
		}
		return set;
	}

	public void addAll(List<Set<Place>> congestionCluster) {
//		collection.drop();
//		for (Set<Place> set : congestionCluster) {
//			Document d = toDocument(set);
//			collection.insertOne(d);
//		}
	}

	public List<Set<Place>> getAll() {
		List<Set<Place>> list = new ArrayList<>();
		FindIterable<Document> documents = collection.find();
		for (Document doc : documents) {
			Set<Place> set = fromDocument(doc);
			list.add(set);
		}
		return list;
	}
}
