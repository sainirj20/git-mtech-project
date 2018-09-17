package com.traffic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties prop = new Properties();

	static {
		reloadProperties();
	}

	private PropertiesUtil() {
	}

	public static void reloadProperties() {
		try {
			prop.load(new FileInputStream("config.properties"));
		} catch (IOException ex) {
			try {
				System.out.println("config.properties file to this location :: " + new File(".").getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

	public static Integer getPropertyInteger(String key) {
		return Integer.parseInt(prop.getProperty(key));
	}

	public static Boolean getPropertyBoolean(String key) {
		return Boolean.parseBoolean(prop.getProperty(key));
	}

}
