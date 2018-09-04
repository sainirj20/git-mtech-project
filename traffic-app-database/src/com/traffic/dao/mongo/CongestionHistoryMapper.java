package com.traffic.dao.mongo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import com.traffic.util.HistoryKeyMaker;

public class CongestionHistoryMapper implements Mapper<List<String>> {

	@Override
	public Document toDocument(List<String> list) {
		Object historyKey = new HistoryKeyMaker().getKey();
		Document document = new Document(id, historyKey);
		document.append(details, list);
		return document;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> fromDocument(Document document) {
		List<String> history = new LinkedList<>();
		history.addAll((Collection<? extends String>) document.getOrDefault(details, new LinkedList<>()));
		return null;
	}
}
