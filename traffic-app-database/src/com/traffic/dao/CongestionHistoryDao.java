package com.traffic.dao;

import static com.mongodb.client.model.Filters.eq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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

public class CongestionHistoryDao implements MongoConstants {
	private final String collectionName = "CongestionHistory";
	private final MongoDatabase instance;
	final MongoCollection<Document> collection;

	public CongestionHistoryDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
	}

	private class Key {
		final String pattern = "yyyy-MMMMM-dd-E-HH-mm";
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		final Calendar today = Calendar.getInstance();

		String getKey() {
			int roundMin = today.get(Calendar.MINUTE) / 10;
			today.set(Calendar.MINUTE, roundMin * 10);
			String key = simpleDateFormat.format(today.getTime());
			return key;
		}

		Map<Integer, String> getTodaysKeys() {
			Map<Integer, String> keys = new HashMap<>();

			Calendar cal = Calendar.getInstance();
			int roundMin = cal.get(Calendar.MINUTE) / 10;
			cal.set(Calendar.MINUTE, roundMin * 10);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			for (int i = 0; i < hour; i++) {
				cal.set(Calendar.HOUR_OF_DAY, i);
				keys.put(i, simpleDateFormat.format(cal.getTime()));
			}
			return keys;
		}

		Map<Integer, String> getLastWeeksKeys() {
			Map<Integer, String> keys = new HashMap<>();
			for (int i = 1; i < 7; i++) {
				Calendar cal = Calendar.getInstance();
				int roundMin = cal.get(Calendar.MINUTE) / 10;
				cal.set(Calendar.MINUTE, roundMin * 10);
				cal.add(Calendar.DATE, -i);
				keys.put(i, simpleDateFormat.format(cal.getTime()));
			}
			return keys;
		}
	}

	public void insertOrUpdate(List<Place> congestedPlaces) {
		if (null == congestedPlaces || 0 == congestedPlaces.size()) {
			return;
		}
		String key = new Key().getKey();
		Mapper<List<Place>> mapper = new CongestionHistoryMapper(key);
		Document doc = mapper.toDocument(congestedPlaces);
		try {
			collection.insertOne(doc);
		} catch (MongoWriteException e) {
			collection.updateOne(eq(id, key), Updates.set(details, doc.get(details)));
		}
	}

	public Map<Integer, List<Place>> getTodaysHistory() {
		Map<Integer, String> todaysKeys = new Key().getTodaysKeys();
		Mapper<List<Place>> mapper = new CongestionHistoryMapper("");
		Map<Integer, List<Place>> history = new HashMap<>();
		todaysKeys.forEach((hour, key) -> {
			Document document = collection.find(eq(id, key)).first();
			if (null != document) {
				history.put(hour, mapper.fromDocument(document));
			}
		});
		return history;
	}

	public Map<Integer, List<Place>> getWeeksHistory() {
		Map<Integer, String> weeksKeys = new Key().getLastWeeksKeys();
		Mapper<List<Place>> mapper = new CongestionHistoryMapper("");
		Map<Integer, List<Place>> history = new HashMap<>();
		weeksKeys.forEach((day, key) -> {
			Document document = collection.find(eq(id, key)).first();
			if (null != document) {
				history.put(day, mapper.fromDocument(document));
			}
		});
		return history;
	}

}
