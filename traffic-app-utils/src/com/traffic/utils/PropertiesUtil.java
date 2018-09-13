package com.traffic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties prop = new Properties();

	static {
		try {
			prop.load(new FileInputStream("config.properties"));
			System.out.println(":: properties loaded ::");
		} catch (IOException ex) {
			try {
				System.out.println("config.properties file to this location :: " + new File(".").getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	private PropertiesUtil() {
	}

	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}

}
