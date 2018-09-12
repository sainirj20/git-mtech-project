package com.traffic.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.traffic.mapper.CongestionHistoryMapper;
import com.traffic.model.Place;
import com.traffic.mongo.Mapper;
import com.traffic.mongo.MongoConstants;
import com.traffic.utils.HistoryKeyMaker;

public class CongestionHistoryDao implements MongoConstants {
	private final String collectionName = "CongestionHistory";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;
	private final Mapper<List<String>> mapper;

	public CongestionHistoryDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		mapper = new CongestionHistoryMapper();
	}

	public void insertOrUpdate(List<Place> congestedPlaces) {
		if (null == congestedPlaces || 0 == congestedPlaces.size()) {
			return;
		}

		List<String> placeIdList = new LinkedList<>();
		for (Place place : congestedPlaces) {
			placeIdList.add(place.getPlaceId());
		}
		Document doc = mapper.toDocument(placeIdList);
		try {
			collection.insertOne(doc);
		} catch (MongoWriteException e) {
			collection.updateOne(eq(id, doc.get(id)), Updates.set(details, doc.get(details)));
		}
	}

	public List<List<String>> getTodaysHistory() {
		List<String> todaysKeys = new HistoryKeyMaker().getTodaysKeys();
		return getHistory(todaysKeys);
	}

	public List<List<String>> getWeeksHistory() {
		List<String> weeksKeys = new HistoryKeyMaker().getLastWeeksKeys();
		return getHistory(weeksKeys);
	}

	private List<List<String>> getHistory(List<String> keys) {
		List<List<String>> history = new LinkedList<>();
		keys.forEach(key -> {
			Document document = collection.find(eq(id, key)).first();
			if (null == document) {
				history.add(new ArrayList<>());
			} else {
				history.add(mapper.fromDocument(document));
			}
		});
		return history;
	}

	public void drop() {
		collection.drop();
	}
}
