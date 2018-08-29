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
import com.traffic.dao.mongo.CongestionMapper;
import com.traffic.dao.mongo.Mapper;
import com.traffic.dao.mongo.MongoConstants;
import com.traffic.model.Congestion;

public class CongestionDao implements MongoConstants {
	private final String collectionName = "Congestion";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;
	private final Mapper<Congestion> mapper;

	public CongestionDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		mapper = new CongestionMapper();
	}

	public Map<String, Congestion> getAll() {
		Map<String, Congestion> congestionMap = new LinkedHashMap<>();
		FindIterable<Document> documents = collection.find();
		for (Document doc : documents) {
			Congestion congestion = mapper.fromDocument(doc);
			congestionMap.put(congestion.getCongestionId(), congestion);
		}
		return congestionMap;
	}

	public void addOrUpdate(Congestion congestion) {
		if (null == congestion) {
			return;
		}
		Document doc = mapper.toDocument(congestion);
		try {
			collection.insertOne(doc);
		} catch (MongoWriteException e) {
			collection.updateOne(eq(id, congestion.getCongestionId()),
					Updates.set(details, doc.get(details, Document.class)));
		}
	}
}
