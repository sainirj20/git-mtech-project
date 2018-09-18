package com.traffic.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.traffic.mapper.PlacesMapper;
import com.traffic.model.Place;
import com.traffic.mongo.Mapper;
import com.traffic.mongo.MongoConstants;

public class PlacesDao implements MongoConstants {
	private static final Map<String, Place> CACHE = new LinkedHashMap<String, Place>();

	private final String collectionName = "Places";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;
	private final Mapper<Place> mapper;

	public PlacesDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		mapper = new PlacesMapper();
	}

	public void addOrUpdate(Place place) {
		if (null == place) {
			return;
		}
		Document doc = mapper.toDocument(place);
		try {
			collection.insertOne(doc);
		} catch (MongoWriteException e) {
			collection.updateOne(eq(id, place.getPlaceId()), Updates.set(details, doc.get(details, Document.class)));
		}
		CACHE.put(place.getPlaceId(), place);
	}

	public Map<String, Place> getAll() {
		if (CACHE.size() != 0) {
			return CACHE;
		}
		Map<String, Place> placesMap = new LinkedHashMap<>();
		FindIterable<Document> documents = collection.find();
		for (Document doc : documents) {
			Place place = mapper.fromDocument(doc);
			placesMap.put(place.getPlaceId(), place);
			CACHE.put(place.getPlaceId(), place);
		}
		return placesMap;
	}

	public boolean has(String placeId) {
		Document document = collection.find(eq(id, placeId)).first();
		return (null != document);
	}

	public Place getByPlaceId(String placeId) {
		Document document = collection.find(eq(id, placeId)).first();
		return (null == document) ? null : mapper.fromDocument(document);
	}
}
