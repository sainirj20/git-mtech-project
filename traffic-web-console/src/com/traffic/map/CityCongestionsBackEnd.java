package com.traffic.map;

import java.util.List;

import com.traffic.dao.CityCongestionsDao;
import com.traffic.model.Congestion;

final public class CityCongestionsBackEnd {
	private static CityCongestionsBackEnd instance = new CityCongestionsBackEnd();

	private CityCongestionsDao clustersDao = new CityCongestionsDao();
	private List<Congestion> groups;

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
}
