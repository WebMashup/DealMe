package me.deal.shared.json;

import java.util.ArrayList;

public class JSONGoogleMapsLocations {

	public ArrayList<JSONGoogleMapsLocation> results;
	
	public class JSONGoogleMapsLocation {
		public String formatted_address;
		public JSONGeometry geometry;
		
		public class JSONGeometry {
			public JSONLocation location;
			
			public class JSONLocation {
				public Double lat;
				public Double lng;
			}
		}
	}
}
