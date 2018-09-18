package com.traffic.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.traffic.mapper.CongestionHistoryMapper;
import com.traffic.model.CongestionHistory;
import com.traffic.model.Place;
import com.traffic.mongo.Mapper;
import com.traffic.mongo.MongoConstants;
import com.traffic.utils.HistoryKeyMaker;

public class CongestionHistoryDao implements MongoConstants {
	private final String collectionName = "CongestionHistory";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;
	private final Mapper<CongestionHistory> mapper;

	private final HistoryKeyMaker historyKeyMaker = new HistoryKeyMaker();

	public CongestionHistoryDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		mapper = new CongestionHistoryMapper();
	}

	public void insertOrUpdate(List<Place> congestedPlaces) {
		if (null == congestedPlaces || 0 == congestedPlaces.size()) {
			return;
		}

		CongestionHistory history = new CongestionHistory();
		history.setHistoryKey(historyKeyMaker.getKey());
		history.setCongestedPlaces(
				congestedPlaces.stream().map(place -> place.getPlaceId()).collect(Collectors.toSet()));

		Document doc = mapper.toDocument(history);
		try {
			collection.insertOne(doc);
		} catch (MongoWriteException e) {
			collection.updateOne(eq(id, doc.get(id)), Updates.set(details, doc.get(details)));
		}
	}

	public List<CongestionHistory> getTodaysHistory() {
		List<String> todaysKeys = new HistoryKeyMaker().getTodaysKeys();
		return getHistory(todaysKeys);
	}

	public List<CongestionHistory> getWeeksHistory() {
		List<String> weeksKeys = new HistoryKeyMaker().getLastWeeksKeys();
		return getHistory(weeksKeys);
	}

//	private List<List<String>> getHistory(List<String> keys) {
//		List<List<String>> history = new LinkedList<>();
//		keys.forEach(key -> {
//			Document document = collection.find(eq(id, key)).first();
//			if (null == document) {
//				history.add(new ArrayList<>());
//			} else {
//				history.add(mapper.fromDocument(document));
//			}
//		});
//		return history;
//	}

	private List<CongestionHistory> getHistory(List<String> keys) {
		CongestionHistory empty = new CongestionHistory();
		empty.setCongestedPlaces(new HashSet<>());

		Map<String, CongestionHistory> map = new LinkedHashMap<>();
		for (String key : keys) {
			map.put(key, empty);
		}

		FindIterable<Document> documentList = collection.find(in(id, keys));
		for (Document document : documentList) {
			if (null != document) {
				CongestionHistory temp = mapper.fromDocument(document);
				map.put(temp.getHistoryKey(), temp);
			}
		}

		List<CongestionHistory> history = new LinkedList<>();
		for (String key : map.keySet()) {
			history.add(map.get(key));
		}

		return history;
	}

	public void drop() {
		collection.drop();
	}
}
