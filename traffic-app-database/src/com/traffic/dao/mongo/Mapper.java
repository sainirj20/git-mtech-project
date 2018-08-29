package com.traffic.dao.mongo;

import org.bson.Document;

public interface Mapper<T> extends MongoConstants{
	Document toDocument(T object);

	T fromDocument(Document document);
}
