package me.deal.client.model;

import java.util.ArrayList;

import me.deal.shared.Deal;
import me.deal.shared.Location;

public class Deals {
	
	private static final Deals INSTANCE = new Deals();
	private ArrayList<Deal> deals;
	private Location userLocation;
	
	private Deals() {
		deals = new ArrayList<Deal>();
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
}
