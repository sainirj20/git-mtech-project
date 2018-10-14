package com.traffic.map;

import java.util.List;

import com.traffic.dao.CityCongestionsDao;
import com.traffic.dao.GeoLocationDao;
import com.traffic.model.Congestion;
import com.traffic.model.GeoLocation;

final public class CityCongestionsBackEnd {
	private static CityCongestionsBackEnd instance = new CityCongestionsBackEnd();

	private CityCongestionsDao clustersDao = new CityCongestionsDao();
	private List<Congestion> groups;
	private GeoLocationDao geoLocationDao = new GeoLocationDao();
	private GeoLocation geoLocation = geoLocationDao.getDetails();

	private CityCongestionsBackEnd() {
	}

	public static CityCongestionsBackEnd getInstance() {
		return instance;
	}

	public boolean hasNewCongestion() {
		boolean hasNew = false;
		if (null != groups && groups.size() > 0) {
			hasNew = clustersDao.hasNewCongestion(groups.get(0).getKey());
		} else {
			hasNew = true;
		}
		if (hasNew) {
			groups = clustersDao.getAll();
		}
		return hasNew;
	}

	public List<Congestion> getGroups() {
		if (null == groups) {
			groups = clustersDao.getAll();
		}
		return groups;
	}

	public GeoLocation getGeoLocation() {
		return geoLocation;
	}
	
	public void resetGeoLocation(){
		geoLocation = geoLocationDao.getDetails();
	}

}
