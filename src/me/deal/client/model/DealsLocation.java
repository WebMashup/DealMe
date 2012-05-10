package me.deal.client.model;

import me.deal.shared.Location;

public class DealsLocation {
	
	private static final DealsLocation INSTANCE = new DealsLocation();
	private Location dealsLocation;
	
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
