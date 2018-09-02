package com.traffic.dao.mongo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import com.traffic.model.Place;

public class CongestionHistoryMapper implements Mapper<List<Place>> {

	private String historyKey;

	public CongestionHistoryMapper(String key) {
		this.historyKey = key;
	}

	@Override
	public Document toDocument(List<Place> list) {
		Document document = new Document(id, historyKey);
		document.append(details, list);
		return document;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Place> fromDocument(Document document) {
		List<Place> history = new LinkedList<>();
		history.addAll((Collection<? extends Place>) document.getOrDefault(details, new LinkedList<>()));
		return null;
	}
	
}
