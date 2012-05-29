package me.deal.client.model;

import java.util.ArrayList;

import me.deal.shared.Category;
import me.deal.shared.Deal;
import me.deal.shared.Location;

public class Deals {
	
	private static final Deals INSTANCE = new Deals();
	private ArrayList<Deal> deals;
	private Location location;
	private Location userLocation;
	private Double radius;
	private Integer offset;
	private ArrayList<Category> tags;
	private Integer loadsSinceLastReset;
	
	private Deals() {
		deals = new ArrayList<Deal>();
		location = new Location();
		radius = new Double(20);
		offset = new Integer(0);
		tags = new ArrayList<Category>();
		loadsSinceLastReset = new Integer(0);
	}
	
	public static Deals getInstance() {
		return INSTANCE;
	}
	
	public ArrayList<Deal> getDeals() {
		return deals;
	}
	
	public void setDeals(final ArrayList<Deal> deals) {
		this.deals = deals;
	}
	
	public void addDeal(final Deal deal) {
		this.deals.add(deal);
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}
	
	public Location getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(final Location userLocation) {
		this.userLocation = userLocation;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(final Double radius) {
		this.radius = radius;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(final Integer offset) {
		this.offset = offset;
	}

	public ArrayList<Category> getTags() {
		return tags;
	}

	public void setTags(final ArrayList<Category> tags) {
		this.tags = tags;
	}
	
	public void addTag(final Category tag) {
		this.tags.add(tag);
	}

	public Integer getLoadsSinceLastReset() {
		return loadsSinceLastReset;
	}

	public void setLoadsSinceLastReset(final Integer loadsSinceLastReset) {
		this.loadsSinceLastReset = loadsSinceLastReset;
	}
	
	public void incrementLoadsSinceLastReset() {
		this.loadsSinceLastReset++;
	}
}
