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
import com.traffic.mapper.PlacesGraphMapper;
import com.traffic.model.PlaceIdNode;
import com.traffic.mongo.Mapper;
import com.traffic.mongo.MongoConstants;

public class PlaceIdGraphDao implements MongoConstants {
	private final String collectionName = "PlaceIdGraph";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;
	private final Mapper<PlaceIdNode> mapper;

	public PlaceIdGraphDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		mapper = new PlacesGraphMapper();
	}

	public synchronized void addOrUpdate(PlaceIdNode node) {
		try {
			Document doc = mapper.toDocument(node);
			collection.insertOne(doc);
		} catch (MongoWriteException e) {
			PlaceIdNode nodeFromDB = this.getByPlaceId(node.getPlaceId());
			node.getNext().addAll(nodeFromDB.getNext());
			node.getPrevious().addAll(nodeFromDB.getPrevious());
			Document doc = mapper.toDocument(node);
			collection.updateOne(eq(id, node.getPlaceId()), Updates.set(details, doc.get(details, Document.class)));
		}
	}

	public Map<String, PlaceIdNode> getAll() {
		Map<String, PlaceIdNode> placeIdNodeMap = new LinkedHashMap<>();
		FindIterable<Document> documents = collection.find();
		for (Document doc : documents) {
			PlaceIdNode node = mapper.fromDocument(doc);
			placeIdNodeMap.put(node.getPlaceId(), node);
		}
		return placeIdNodeMap;
	}
	
	public boolean has(String placeId) {
		Document document = collection.find(eq(id, placeId)).first();
		return (null != document);
	}

	public PlaceIdNode getByPlaceId(String placeId) {
		Document document = collection.find(eq(id, placeId)).first();
		return (null == document) ? null : mapper.fromDocument(document);
	}
}
