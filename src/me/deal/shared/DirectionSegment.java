package me.deal.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DirectionSegment implements Serializable {

	private String distance;
	private String time;

	private LatLngCoor startLocation;
	private LatLngCoor endLocation;
	
	private String instruction;
	
	public DirectionSegment() {	
	}
	
	public DirectionSegment(
			final String distance,
			final String time,
			final LatLngCoor startLocation,
			final LatLngCoor endLocation,
			final String instruction) {
		this.distance = distance;
		this.time = time;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.instruction = instruction;
	}
	
	public String getDistance() {
		return this.distance;
	}
	
	public void setDistance(final String distance) {
		this.distance = distance;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public void setTime(final String time) {
		this.time = time;
	}
	
	public LatLngCoor getStartLocation() {
		return startLocation;
	}
	
	public void setStartLocation(final LatLngCoor startLocation) {
		this.startLocation = startLocation;
	}
	
	public LatLngCoor getEndLocation() {
		return this.endLocation;
	}
	
	public void setEndLocation(final LatLngCoor endLocation) {
		this.endLocation = endLocation;
	}
	
	public String getInstruction() {
		return this.instruction;
	}
	
	public void setInstruction(final String instruction) {
		this.instruction = instruction;
	}
}
