package me.deal.shared.json;

import java.util.ArrayList;

public class JSONYipitDeals {
	
	public JSONYipitDeals() {
	}
	
	public JSONYipitDealsResponse response;
	
	public static class JSONYipitDealsResponse {
		public JSONYipitDealsResponse() {
		}
		public ArrayList<JSONYipitDeal> deals;
		
		public static class JSONYipitDeal {
			public JSONYipitDeal() {
			}
			public Integer id;
			public String date_added;
			public String end_date;
			public JSONDiscount discount;
			public JSONPrice price;
			public JSONValue value;
			public String title;
			public String yipit_title;
			public String yipit_url;
			public String mobile_url;
			public JSONImage images;
			public ArrayList<JSONTag> tags;
			public JSONBusiness business;
			public JSONSource source;
			
			public static class JSONDiscount {
				public JSONDiscount() {
				}
				public Double raw;
			}
			
			public static class JSONPrice {
				public JSONPrice() {
				}
				public String raw;
			}
			
			public static class JSONValue {
				public JSONValue() {
				}
				public String raw;
			}
			
			public static class JSONImage {
				public JSONImage() {
				}
				public String image_big;
				public String image_small;
			}
			
			public static class JSONTag {
				public JSONTag() {
				}
				public String name;
				public String slug;
				public String url;
			}
			
			public static class JSONBusiness {
				public JSONBusiness() {
				}
				public String name;
				public String url;
				public ArrayList<JSONLocation> locations;
				
				public static class JSONLocation {
					public JSONLocation() {
					}
					public String address;
					public String locality;
					public String phone;
					public String lat;
					public String lon;
					public String state;
					public String zip_code;
				}
			}
			
			public static class JSONSource {
				public JSONSource() {
				}
				public Integer paid;
				public String name;
			}
		}
	}
}
