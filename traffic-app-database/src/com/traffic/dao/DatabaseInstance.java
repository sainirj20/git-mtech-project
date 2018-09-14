package com.traffic.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import com.traffic.log.MyLogger;
import com.traffic.utils.PropertiesUtil;

final public class DatabaseInstance {
	private static Object mutex = new Object();
	private static MongoDatabase DATABASE;
	{
		Logger mongoLogger = MyLogger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
	}

	private Logger logger = MyLogger.getLogger(DatabaseInstance.class.getName());

	private final MongoClient mongo;

	private final String host = PropertiesUtil.getPropertyValue("mongo.host");
	private final Integer port = Integer.parseInt(PropertiesUtil.getPropertyValue("mongo.port"));
	private final String userName = PropertiesUtil.getPropertyValue("mongo.userName");
	private final String dbName = PropertiesUtil.getPropertyValue("mongo.dbName");
	private final String password = PropertiesUtil.getPropertyValue("mongo.password");

	private DatabaseInstance() {
		String mode = PropertiesUtil.getPropertyValue("mongo.mode");
		if ("local".equalsIgnoreCase(mode)) {
			mongo = new MongoClient(host, port);
			MongoCredential.createCredential(userName, dbName, password.toCharArray());
		} else {
			MongoClientURI uri = new MongoClientURI(PropertiesUtil.getPropertyValue("mongo.uri"));
			mongo = new MongoClient(uri);
		}
		logger.log(Level.INFO, "Connected to the database successfully");
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
