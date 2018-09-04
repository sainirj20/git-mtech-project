package com.traffic.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.traffic.dao.mongo.CongestionHistoryMapper;
import com.traffic.dao.mongo.Mapper;
import com.traffic.dao.mongo.MongoConstants;
import com.traffic.model.Place;
import com.traffic.util.HistoryKeyMaker;

public class CongestionHistoryDao implements MongoConstants {
	private final String collectionName = "CongestionHistory";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;
	private final Mapper<List<String>> mapper;

	private final Map<String, Place> placesMap;

	public CongestionHistoryDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		mapper = new CongestionHistoryMapper();
		placesMap = new PlacesDao().getAll();
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

	public List<List<Place>> getTodaysHistory() {
		List<String> todaysKeys = new HistoryKeyMaker().getTodaysKeys();
		return getHistory(todaysKeys);
	}

	public List<List<Place>> getWeeksHistory() {
		List<String> weeksKeys = new HistoryKeyMaker().getLastWeeksKeys();
		return getHistory(weeksKeys);
	}

	private List<List<Place>> getHistory(List<String> keys) {
		List<List<String>> placeIds = new LinkedList<>();
		keys.forEach(key -> {
			Document document = collection.find(eq(id, key)).first();
			if (null != document) {
				placeIds.add(mapper.fromDocument(document));
			}
		});

		List<List<Place>> history = new LinkedList<>();
		for (List<String> list : placeIds) {
			List<Place> congestedPlaces = new LinkedList<>();
			for (String placeId : list) {
				congestedPlaces.add(placesMap.get(placeId));
			}
			history.add(congestedPlaces);
		}
		return history;
	}
	
	public void drop() {
		collection.drop();
	}
}
