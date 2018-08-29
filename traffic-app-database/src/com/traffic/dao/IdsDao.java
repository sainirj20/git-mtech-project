package com.traffic.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.traffic.dao.mongo.MongoConstants;

public class IdsDao implements MongoConstants {
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;

	public IdsDao(String collectionName) {
		instance = DatabaseInstance.getTempDbInstance();
		collection = instance.getCollection(collectionName);
	}

	public void addOrUpdate(String placeId) {
		Document document = new Document(id, placeId);
		try {
			collection.insertOne(document);
		} catch (MongoWriteException e) {
			collection.updateOne(eq(id, placeId), Updates.set(id, placeId));
		}
	}

	public boolean hasPlaceId(String placeId) {
		Document document = collection.find(eq(id, placeId)).first();
		return null != document;
	}

	public void drop() {
		collection.drop();
	}
}
