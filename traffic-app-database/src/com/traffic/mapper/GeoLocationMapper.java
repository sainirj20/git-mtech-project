package com.traffic.mapper;

import org.bson.Document;

import com.traffic.model.GeoLocation;
import com.traffic.mongo.Mapper;

public class GeoLocationMapper implements Mapper<GeoLocation> {

	private final String zoom = "zoom";
	private final String name = "name";

	private final String latitude = "latitude";
	private final String longitude = "longitude";

	private final String tileX = "tileX";
	private final String tileY = "tileY";

	@Override
	public Document toDocument(GeoLocation location) {
		Document locationDetails = new Document();
		locationDetails.append(name, location.getName());
		locationDetails.append(zoom, location.getZoom());
		locationDetails.append(latitude, location.getLatitude());
		locationDetails.append(longitude, location.getLongitude());
		locationDetails.append(tileX, location.getTileX());
		locationDetails.append(tileY, location.getTileY());
		Document document = new Document(id, "main");
		document.append(details, locationDetails);
		return document;
	}

	@Override
	public GeoLocation fromDocument(Document document) {
		Document doc = document.get(details, Document.class);
		GeoLocation location = new GeoLocation();
		location.setZoom(doc.getInteger(zoom));
		location.setName(doc.getString(name));
		location.setLatitude(doc.getDouble(latitude));
		location.setLongitude(doc.getDouble(longitude));
		location.setTileX(doc.getInteger(tileX));
		location.setTileY(doc.getInteger(tileY));
		return location;
	}

}
