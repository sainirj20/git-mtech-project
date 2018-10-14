package com.traffic.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.traffic.dao.UsersDao;
import com.traffic.map.CongestionsOnMap;
import com.traffic.model.GeoLocation;
import com.traffic.model.Place;

// http://localhost:8080/traffic-web-console/TrafficApp/CongestionsResponse/congestions?user=rajat
@Path("/CongestionsResponse")
public class CongestionsResponse {

	@GET
	@Path("/congestions")
	@Produces(MediaType.APPLICATION_JSON)
	public CongestionsJson getResponse(@Context UriInfo info) {
		String user = info.getQueryParameters().getFirst("user");
		
		CongestionsJson json = new CongestionsJson();
		UsersDao usersDao = new UsersDao();
		if(!usersDao.has(user)) {
			json.setStatus("INVALID USER");
			return json;
		}
		
		CongestionsOnMap congestionsOnMap = new CongestionsOnMap();
		congestionsOnMap.reload();
		GeoLocation geoLocation = congestionsOnMap.getGeoLocation();

		json.setStatus("OK");
		json.setZoom(geoLocation.getZoom());
		json.setLatitude(geoLocation.getLatitude());
		json.setLongitude(geoLocation.getLongitude());

		json.setSmall(getCongestionsList(congestionsOnMap.getCongestion(1)));
		json.setLarge(getCongestionsList(congestionsOnMap.getCongestion(2)));
		json.setUnusual(getCongestionsList(congestionsOnMap.getCongestion(3)));

		return json;
	}

	private List<Object> getCongestionsList(Set<Place> congestionsSet) {
		if (congestionsSet.size() == 0) {
			return new ArrayList<>();
		}
		List<Object> result = new ArrayList<>();
		Iterator<Place> itr = congestionsSet.iterator();
		while (itr.hasNext()) {
			Place p = itr.next();
			Map<String, Object> temp = new HashMap<>();
			temp.put("Latitude", p.getLat());
			temp.put("Longitude", p.getLng());
			temp.put("Address", p.getShortAddress());
			result.add(temp);
		}
		return result;
	}

}
