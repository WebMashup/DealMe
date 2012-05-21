package me.deal.shared.json;

import java.util.ArrayList;

public class JSONGoogleDirections {
	
	public ArrayList<JSONRoutes> routes;
	
	public class JSONRoutes {
		public JSONBounds bounds;
		public ArrayList<JSONLeg> legs;
		
		public class JSONBounds {
			public JSONLocation northeast;
			public JSONLocation southwest;
		}
		
		public class JSONLeg {
			public JSONDistance distance;
			public JSONDuration duration;
			public String end_address;
			public JSONLocation end_location;
			public String start_address;
			public JSONLocation start_location;
			public ArrayList<JSONStep> steps;
			
			public class JSONDistance {
				public String text;
			}
			
			public class JSONDuration {
				public String text;
			}
			
			public class JSONStep {
				public JSONDistance distance;
				public JSONDuration duration;
				public JSONLocation end_location;
				public String html_instructions;
				public JSONLocation start_location;
			}
		}
	}
	
	public class JSONLocation {
		public Double lat;
		public Double lng;
	}
}
