package me.deal.client.model;

import me.deal.shared.Location;

public class UserLocation {
	
	private static final UserLocation INSTANCE = new UserLocation();
	private Location userLocation;
	
	private UserLocation() {
	}
	
	public static UserLocation getInstance() {
		return INSTANCE;
	}
	
	public void setUserLocation(final Location userLocation) {
		this.userLocation = userLocation;
	}
	
	public Location getUserLocation() {
		return this.userLocation;
	}
}
