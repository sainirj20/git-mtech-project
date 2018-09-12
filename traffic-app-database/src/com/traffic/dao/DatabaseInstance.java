package com.traffic.dao;

import java.io.IOException;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;

final public class DatabaseInstance {
	private static Object mutex = new Object();

	private static final MongoClient mongo = new MongoClient("localhost", 27017);
	private static MongoDatabase DATABASE;

	private DatabaseInstance() {
		MongoCredential.createCredential("sampleUser", "myDb", "password".toCharArray());
		System.out.println("Connected to the database successfully");
		DATABASE = mongo.getDatabase("myDb");
	}

	public static MongoDatabase getInstance() {
		try {
			String current = new java.io.File(".").getCanonicalPath();
			System.out.println("Current dir:" + current);
			System.exit(0);
		} catch (IOException e) {
		}
		if (DATABASE == null) {
			synchronized (mutex) {
				new DatabaseInstance();
			}
		}
		return DATABASE;
	}
}
