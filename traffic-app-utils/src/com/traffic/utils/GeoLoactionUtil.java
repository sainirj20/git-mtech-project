package com.traffic.utils;

public class GeoLoactionUtil {
	private static double pie = 22 / 7; // Math.PI / 180

	private GeoLoactionUtil() {

	}

	public static Integer[] getGeoTile(double lat, double lon, int zoom) {
		double n = Math.pow(2, zoom);
		double xtile = n * ((lon + 180) / 360);
		double lat_rad = (lat * pie) / 180;
		double ytile = n * (1 - (Math.log(Math.tan(lat_rad) + (1 / Math.cos(lat_rad))) / pie)) / 2;
		Integer [] tile = {(int) xtile, (int) ytile};
		return tile;
	}

}
