package com.traffic.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.traffic.mapper.GeoLocationMapper;
import com.traffic.model.GeoLocation;
import com.traffic.mongo.Mapper;
import com.traffic.mongo.MongoConstants;

public class GeoLocationDao implements MongoConstants {
	private final String collectionName = "GeoLocation";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;
	private final Mapper<GeoLocation> mapper;

	public GeoLocationDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		mapper = new GeoLocationMapper();
	}

	public void addOrUpdate(GeoLocation geoLocation) {
		if (null == geoLocation) {
			return;
		}
		Document doc = mapper.toDocument(geoLocation);
		try {
			collection.insertOne(doc);
		} catch (MongoWriteException e) {
			collection.updateOne(eq(id, "main"), Updates.set(details, doc.get(details, Document.class)));
		}
	}

	public GeoLocation getDetails() {
		Document document = collection.find(eq(id, "main")).first();
		return (null == document) ? new GeoLocation() : mapper.fromDocument(document);
	}

}
