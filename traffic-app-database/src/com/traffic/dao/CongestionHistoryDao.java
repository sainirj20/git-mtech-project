package com.traffic.dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CongestionHistoryDao {
	private final String collectionName = "CongestionHistory";
	private final MongoDatabase instance;
	final MongoCollection<Document> collection;

	public CongestionHistoryDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
	}

	public boolean containsPlaceId(String placeId) {
		// TODO Auto-generated method stub
		return false;
	}

}
