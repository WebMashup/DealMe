package me.deal.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LatLng implements Serializable {
	
	private Double latitude;
	private Double longitude;
	
	public LatLng() {
	}
	
	public LatLng(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return this.latitude;
	}
	
	public Double getLongitude() {
		return this.longitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
