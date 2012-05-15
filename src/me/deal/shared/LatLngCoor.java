package me.deal.shared;

import java.io.Serializable;

import com.google.gwt.maps.client.geom.LatLng;

@SuppressWarnings("serial")
public class LatLngCoor implements Serializable {
	
	private Double latitude;
	private Double longitude;
	
	public LatLngCoor() {
	}
	
	public LatLngCoor(Double latitude, Double longitude) {
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
	
	public LatLng convert()
    {
        return LatLng.newInstance(this.latitude, this.longitude);
    }
}
