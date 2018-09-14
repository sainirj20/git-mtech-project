package com.traffic.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.traffic.mapper.CityCongestionsMapper;
import com.traffic.model.Congestion;
import com.traffic.model.Place;
import com.traffic.mongo.Mapper;
import com.traffic.mongo.MongoConstants;

public class CityCongestionsDao implements MongoConstants {
	private final String collectionName = "CityCongestions";
	private final MongoDatabase instance;
	private final MongoCollection<Document> collection;
	private final Mapper<Congestion> mapper;

	private Map<String, Place> placesMap = null;

	public CityCongestionsDao() {
		instance = DatabaseInstance.getInstance();
		collection = instance.getCollection(collectionName);
		placesMap = new PlacesDao().getAll();
		mapper = new CityCongestionsMapper(placesMap);
	}

	public void drop() {
		collection.drop();
	}

	public void addAll(List<Congestion> congestionList) {
		List<Document> allCongestions = new LinkedList<>();
		for (Congestion congestion : congestionList) {
			Document d = mapper.toDocument(congestion);
			allCongestions.add(d);
		}
		collection.insertMany(allCongestions);
	}

	public List<Congestion> getAll() {
		List<Congestion> list = new ArrayList<>();
		FindIterable<Document> documents = collection.find();
		for (Document doc : documents) {
			Congestion congestion = mapper.fromDocument(doc);
			list.add(congestion);
		}
		return list;
	}
}
