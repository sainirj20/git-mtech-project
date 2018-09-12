package com.traffic.utils;

import com.traffic.model.Place;

public class DistanceUtil {

	private static double p = 0.017453292519943295; // Math.PI / 180
	private static int diameterOfEarth = 12742;

	public static synchronized double getDistanceIfNearBy(Place p1, Place p2) {
		double lat1 = p1.getLat();
		double lon1 = p1.getLng();
		double lat2 = p2.getLat();
		double lon2 = p2.getLng();

		double latDiff = lat2 - lat1;
		double lonDiff = lon2 - lon1;
		if (latDiff > 0.01 || lonDiff > 0.01) {
			return 100;
		}

		double a = 0.5 - Math.cos(latDiff * p) / 2
				+ Math.cos(lat1 * p) * Math.cos(lat2 * p) * (1 - Math.cos(lonDiff * p)) / 2;

		return diameterOfEarth * Math.asin(Math.sqrt(a));
	}

	/** return's distance in KM */
	public static double getDistance(Place p1, Place p2) {
		double lat1 = p1.getLat();
		double lon1 = p1.getLng();
		double lat2 = p2.getLat();
		double lon2 = p2.getLng();

		double a = 0.5 - Math.cos((lat2 - lat1) * p) / 2
				+ Math.cos(lat1 * p) * Math.cos(lat2 * p) * (1 - Math.cos((lon2 - lon1) * p)) / 2;

		return diameterOfEarth * Math.asin(Math.sqrt(a));
	}

	private DistanceUtil() {
	}
}
