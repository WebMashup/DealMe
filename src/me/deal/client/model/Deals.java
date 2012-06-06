package me.deal.client.model;

import java.util.ArrayList;

import me.deal.shared.Category;
import me.deal.shared.Deal;
import me.deal.shared.Location;

public class Deals {

    public static final Integer DEFAULT_NUM_DEALS = 30;
    
    private static final Deals INSTANCE = new Deals();
    private ArrayList<Deal> deals;
    private Location location;
    private Location userLocation;
    private Double radius;
    private Integer offset;
    private ArrayList<Category> tags;
    private Boolean reset;
    private int duplicates;
    private boolean resize;

    private Deals() {
        deals = new ArrayList<Deal>();
        location = new Location();
        radius = new Double(20);
        offset = new Integer(0);
        tags = new ArrayList<Category>();
        reset = new Boolean(false);
        duplicates = 0;
        resize = true;
    }
       
    public static Deals getInstance() {
        return INSTANCE;
    }
    
    public ArrayList<Deal> getDeals() {
        return deals;
    }
    
    public void setDeals(final ArrayList<Deal> deals) {
        this.deals = deals;
        this.reset = true;
    }
    
    public void addDeal(final Deal deal) {
        this.deals.add(deal);
    }
    
    public void addDeals(final ArrayList<Deal> deals) {
    	for(Deal deal : deals) {
    		this.deals.add(deal);
    	}
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

    public void acknowledgeReset() {
    	this.reset = false;
    }
    
    public Boolean isReset() {
    	return this.reset;
    }
    
    public int getDuplicates()
    {
        return this.duplicates;
    }
    
    public void setDuplicates(int dup)
    {
        this.duplicates = dup;
    }
    
    public boolean getResize()
    {
        return this.resize;
    }
    
    public void setResize(boolean bool)
    {
        this.resize = bool;
    }
}