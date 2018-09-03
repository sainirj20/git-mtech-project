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
	final MongoCollection<Document> collection;

	public CongestionHistoryDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
	}

	public void insertOrUpdate(List<Place> congestedPlaces) {
		if (null == congestedPlaces || 0 == congestedPlaces.size()) {
			return;
		}
		String key = new HistoryKeyMaker().getKey();
		Mapper<List<Place>> mapper = new CongestionHistoryMapper(key);
		Document doc = mapper.toDocument(congestedPlaces);
		try {
			collection.insertOne(doc);
		} catch (MongoWriteException e) {
			collection.updateOne(eq(id, key), Updates.set(details, doc.get(details)));
		}
	}

	public List<List<Place>> getTodaysHistory() {
		Map<String, String> todaysKeys = new HistoryKeyMaker().getTodaysKeys();
		Mapper<List<Place>> mapper = new CongestionHistoryMapper("");
		List<List<Place>> history = new LinkedList<>();
		todaysKeys.forEach((hour, key) -> {
			Document document = collection.find(eq(id, key)).first();
			if (null != document) {
				history.add(mapper.fromDocument(document));
			}
		});
		return history;
	}

	public List<List<Place>> getWeeksHistory() {
		Map<Integer, String> weeksKeys = new HistoryKeyMaker().getLastWeeksKeys();
		Mapper<List<Place>> mapper = new CongestionHistoryMapper("");
		List<List<Place>> history = new LinkedList<>();
		weeksKeys.forEach((day, key) -> {
			Document document = collection.find(eq(id, key)).first();
			if (null != document) {
				history.add(mapper.fromDocument(document));
			}
		});
		return history;
	}

}
