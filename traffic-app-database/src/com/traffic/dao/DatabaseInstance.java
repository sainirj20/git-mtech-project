package com.traffic.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

final public class DatabaseInstance {
	private static Object mutex = new Object();

	private static final MongoClient mongo = new MongoClient("localhost", 27017);;
	private static MongoDatabase DATABASE;
	private static MongoDatabase TEMP;

	private DatabaseInstance(String dataBaseName) {
		MongoCredential.createCredential("sampleUser", dataBaseName, "password".toCharArray());
		System.out.println("Connected to the database successfully");
		DATABASE = mongo.getDatabase("myDb");
		TEMP = mongo.getDatabase("temp");
	}

	public static MongoDatabase getInstance() {
		if (DATABASE == null) {
			synchronized (mutex) {
				new DatabaseInstance("myDb");
			}
		}
		return DATABASE;
	}

	public static MongoDatabase getTempDbInstance() {
		if (TEMP == null) {
			synchronized (mutex) {
				new DatabaseInstance("temp");
			}
		}
		return TEMP;
	}

}
