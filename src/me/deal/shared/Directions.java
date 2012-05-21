package me.deal.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Directions implements Serializable {
	
	private Location origin;
	private Location destination;
	
	private LatLngCoor northEastBounds;
	private LatLngCoor southWestBounds;
	
	private String totalDistance;
	private String totalTime;
	
	private ArrayList<DirectionSegment> segments;
	
	public Directions() {
	}
	
	public Directions(
			final Location origin,
			final Location destination,
			final LatLngCoor northEastBounds,
			final LatLngCoor southWestBounds,
			final String totalDistance,
			final String totalTime,
			final ArrayList<DirectionSegment> segments) {
		this.origin = origin;
		this.destination = destination;
		this.northEastBounds = northEastBounds;
		this.southWestBounds = southWestBounds;
		this.totalDistance = totalDistance;
		this.totalTime = totalTime;
		this.segments = segments;
	}

	public Location getOrigin() {
		return this.origin;
	}

	public void setOrigin(final Location origin) {
		this.origin = origin;
	}

	public Location getDestination() {
		return this.destination;
	}

	public void setDestination(final Location destination) {
		this.destination = destination;
	}

	public LatLngCoor getNorthEastBounds() {
		return this.northEastBounds;
	}

	public void setNorthEastBounds(final LatLngCoor northEastBounds) {
		this.northEastBounds = northEastBounds;
	}

	public LatLngCoor getSouthWestBounds() {
		return southWestBounds;
	}

	public void setSouthWestBounds(final LatLngCoor southWestBounds) {
		this.southWestBounds = southWestBounds;
	}

	public String getTotalDistance() {
		return this.totalDistance;
	}

	public void setTotalDistance(final String totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getTotalTime() {
		return this.totalTime;
	}

	public void setTotalTime(final String totalTime) {
		this.totalTime = totalTime;
	}

	public ArrayList<DirectionSegment> getSegments() {
		return this.segments;
	}

	public void setSegments(final ArrayList<DirectionSegment> segments) {
		this.segments = segments;
	}
}
