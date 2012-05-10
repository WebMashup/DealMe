package me.deal.client.model;

import me.deal.shared.Location;

public class DealsLocation {
	
	private static final DealsLocation INSTANCE = new DealsLocation();
	private Location dealsLocation;
	public final Double DEFAULT_RADIUS = 20.0;  // Default radius in miles to search around
	
	private DealsLocation() {
	}
	
	public static DealsLocation getInstance() {
		return INSTANCE;
	}
	
	public void setDealsLocation(final Location dealsLocation) {
		this.dealsLocation = dealsLocation;
	}
	
	public Location getDealsLocation() {
		return this.dealsLocation;
	}
}
