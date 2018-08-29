package com.traffic.dao;

import java.util.Map;

import com.traffic.dao.mongo.MongoConstants;

public interface Dao<T> extends MongoConstants{

	Map<String, T> getAll();

	T getById(String id);

	void insertAll(Map<String, T> tMap);

	void insertOrUpdate(T t);

	boolean has(String id);
}
