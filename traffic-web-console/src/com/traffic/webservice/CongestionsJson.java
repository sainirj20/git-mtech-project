package com.traffic.webservice;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CongestionsJson {
	private String status = "OK";

	private Double latitude = 0.0;
	private Double longitude = 0.0;
	private Integer zoom = 0;

	private List<Object> small;
	private List<Object> large;
	private List<Object> unusual;

	public String getStatus() {
		return status;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Integer getZoom() {
		return zoom;
	}

	public List<Object> getSmall() {
		return small;
	}

	public List<Object> getLarge() {
		return large;
	}

	public List<Object> getUnusual() {
		return unusual;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	public void setSmall(List<Object> small) {
		this.small = small;
	}

	public void setLarge(List<Object> large) {
		this.large = large;
	}

	public void setUnusual(List<Object> unusual) {
		this.unusual = unusual;
	}
}
