package com.traffic.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.traffic.mongo.MongoConstants;

public class UsersDao implements MongoConstants {
	private final String collectionName = "Users";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;

	public UsersDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
	}

	public void addAll(String... users) {
		for (String user : users) {
			if (null == user || "".equals(user))
				continue;
			try {
				Document document = new Document(id, user.toLowerCase());
				collection.insertOne(document);
			} catch (MongoWriteException e) {
			}
		}
	}

	public boolean has(String user) {
		if (null == user)
			return false;
		Document document = collection.find(eq(id, user.toLowerCase())).first();
		return (null != document);
	}

}
