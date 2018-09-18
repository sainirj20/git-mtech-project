package com.traffic.mapper;

import java.util.Collection;
import java.util.HashSet;

import org.bson.Document;

import com.traffic.model.CongestionHistory;
import com.traffic.mongo.Mapper;

public class CongestionHistoryMapper implements Mapper<CongestionHistory> {

	@Override
	public Document toDocument(CongestionHistory history) {
		Document document = new Document(id, history.getHistoryKey());
		document.append(details, history.getCongestedPlaces());
		return document;
	}

	@Override
	@SuppressWarnings("unchecked")
	public CongestionHistory fromDocument(Document document) {
		CongestionHistory history = new CongestionHistory();
		history.setHistoryKey(document.getString(id));
		history.addAll((Collection<? extends String>) document.getOrDefault(details, new HashSet<>()));
		return history;
	}
}
