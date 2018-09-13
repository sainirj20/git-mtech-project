package com.traffic.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import com.traffic.utils.PropertiesUtil;

final public class DatabaseInstance {
	private static Object mutex = new Object();
	private static MongoDatabase DATABASE;

	private final MongoClient mongo;
	
	private final String host;
	private final Integer port;
	private final String userName;
	private final String dbName;
	private final String password;

	private DatabaseInstance() {
		host = PropertiesUtil.getPropertyValue("host");
		port = Integer.parseInt(PropertiesUtil.getPropertyValue("port"));
		userName = PropertiesUtil.getPropertyValue("userName");
		dbName = PropertiesUtil.getPropertyValue("dbName");
		password = PropertiesUtil.getPropertyValue("password");
		
		mongo = new MongoClient(host, port);
		
		MongoCredential.createCredential(userName, dbName, password.toCharArray());
		System.out.println("Connected to the database successfully");
		DATABASE = mongo.getDatabase(dbName);
	}

	public static MongoDatabase getInstance() {
		if (DATABASE == null) {
			synchronized (mutex) {
				new DatabaseInstance();
			}
		}
		return DATABASE;
	}
}
