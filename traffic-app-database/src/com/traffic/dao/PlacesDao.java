package com.traffic.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.traffic.dao.mongo.Mapper;
import com.traffic.dao.mongo.MongoConstants;
import com.traffic.dao.mongo.PlacesMapper;
import com.traffic.model.Place;

public class PlacesDao implements MongoConstants {
	private final String collectionName = "Places";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;
	private final Mapper<Place> mapper;

	public PlacesDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		mapper = new PlacesMapper();
	}

	public void insertAll(Map<String, Place> placesMap) {
		List<Document> docs = new LinkedList<>();
		for (Map.Entry<String, Place> entry : placesMap.entrySet()) {
			docs.add(mapper.toDocument(entry.getValue()));
		}
		try {
			collection.insertMany(docs);
		} catch (MongoBulkWriteException e) {
			System.out.println(e);
		}
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
	}

	public Map<String, Place> getAll() {
		Map<String, Place> placesMap = new LinkedHashMap<>();
		FindIterable<Document> documents = collection.find();
		for (Document doc : documents) {
			Place place = mapper.fromDocument(doc);
			placesMap.put(place.getPlaceId(), place);
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
