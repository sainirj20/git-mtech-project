package com.traffic.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

interface Keys {
	String[] keys = { "AIzaSyCd3gQRLIhHL7RPXWuMp2xwv3qlv662h7k", "AIzaSyB7Qbh8p3IbWZMGnKKRZ-oDpCBPsDHzQL0" };
}

public class KeyStore implements Keys {

	private static final List<String> keyStore = new ArrayList<>();
	private static int pointer = 0;

	static {
		for (int i = 0; i < keys.length; i++) {
			keyStore.add(keys[i]);
		}
	}

	private KeyStore() {
	}

	public static synchronized NameValuePair getKey() {
		if (keyStore.size() == 0) {
			System.out.println("All Keys Died");
			System.exit(1);
		}
		NameValuePair key = new BasicNameValuePair("key", keyStore.get(pointer));
		pointer = (pointer + 1) % keyStore.size();
		return key;
	}

	public static synchronized void removeKey(String key) {
		keyStore.remove(key);
	}
}
