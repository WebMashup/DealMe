package me.deal.shared.json;

import java.util.ArrayList;

public class JSONGoogleMapsLocations {

	public ArrayList<JSONGoogleMapsLocation> results;
	public JSONGoogleMapsLocations() {
	}
	
	public static class JSONGoogleMapsLocation {
		
		public JSONGoogleMapsLocation() {
		}
		public String formatted_address;
		public JSONGeometry geometry;
		
		public static class JSONGeometry {
			public JSONGeometry() {
			}
			public JSONLocation location;
			
			public static class JSONLocation {
				public JSONLocation() {
				}
				public Double lat;
				public Double lng;
			}
		}
	}
}
