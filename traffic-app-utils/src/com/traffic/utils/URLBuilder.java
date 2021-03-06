package com.traffic.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.traffic.model.Pair;
import com.traffic.model.Place;

public class URLBuilder {
	private static String scheme = "https";

	private static URL getURL(URIBuilder builder) {
		URL url = null;
		try {
			url = builder.build().toURL();
		} catch (MalformedURLException | URISyntaxException e) {
			System.out.println(e.getMessage());
		}
		return url;
	}

	private interface PlaceApi {
		String url = "maps.googleapis.com";
		String path = "/maps/api/place/details/json";
	}

	public static URL getPlaceApiURL(String placeId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(KeyStore.getKey());
		params.add(new BasicNameValuePair("placeid", placeId));

		URIBuilder builder = new URIBuilder();
		builder.setScheme(scheme);
		builder.setHost(PlaceApi.url);
		builder.setPath(PlaceApi.path);
		builder.setParameters(params);

		return getURL(builder);
	}

	public interface RoadApi {
		String url = "roads.googleapis.com";
		String path = "/v1/trafficSpeeds/tile";
		String freeFlow = "FREEFLOW";
		String current = "CURRENT";
		NameValuePair units = new BasicNameValuePair("units", "KPH");
	}

	public static URL getRoadApiURL(Integer x, Integer y, Integer zoom, String type) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("type", type));
		params.add(new BasicNameValuePair("x", x.toString()));
		params.add(new BasicNameValuePair("y", y.toString()));
		params.add(new BasicNameValuePair("zoom", zoom.toString()));

		URIBuilder builder = new URIBuilder();
		builder.setScheme(scheme);
		builder.setHost(RoadApi.url);
		builder.setPath(RoadApi.path);
		builder.setParameters(KeyStore.getKey(), RoadApi.units);
		builder.addParameters(params);
		return getURL(builder);
	}

	private interface MapsApi {
		String url = "maps.googleapis.com";
		String path = "/maps/api/directions/json";
	}

	public synchronized static URL getDirectionApi(Place source, Place dest) {
		String customQuery = "" + new BasicNameValuePair("origin", source.getLatLong()) + '&'
				+ new BasicNameValuePair("destination", dest.getLatLong()) + '&' + KeyStore.getKey();

		URIBuilder builder = new URIBuilder();
		builder.setScheme(scheme);
		builder.setHost(MapsApi.url);
		builder.setPath(MapsApi.path);
		builder.setCustomQuery(customQuery);
		return getURL(builder);
	}

	private interface SnapToRoadsApi {
		String url = "roads.googleapis.com";
		String path = "/v1/snapToRoads";
		NameValuePair interpolate = new BasicNameValuePair("interpolate", "true");
	}

	public static URL getSnapToRoadsURL(Pair source, Pair dest) {
		String path = source + "|" + dest;
		String customQuery = "" + new BasicNameValuePair("path", path) + '&' + KeyStore.getKey() + '&'
				+ SnapToRoadsApi.interpolate;

		URIBuilder builder = new URIBuilder();
		builder.setScheme(scheme);
		builder.setHost(SnapToRoadsApi.url);
		builder.setPath(SnapToRoadsApi.path);
		builder.setCustomQuery(customQuery);
		return getURL(builder);
	}
	
	private interface SpeedLimitsApi {
		String url = "roads.googleapis.com";
		String path = "/v1/speedLimits";
		NameValuePair units = new BasicNameValuePair("units", "KPH");
	}
	
	public static URL getSpeedLimitsUrl(String placeId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("placeId", placeId));

		URIBuilder builder = new URIBuilder();
		builder.setScheme(scheme);
		builder.setHost(SpeedLimitsApi.url);
		builder.setPath(SpeedLimitsApi.path);
		builder.setParameters(KeyStore.getKey(), SpeedLimitsApi.units);
		builder.addParameters(params);
		return getURL(builder);
	}
}
