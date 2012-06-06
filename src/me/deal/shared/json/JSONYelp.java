package me.deal.shared.json;

import java.util.ArrayList;

public class JSONYelp {
	
	public ArrayList<JSONYelpBusiness> businesses;
	public JSONYelp() {
	}
	public static class JSONYelpBusiness {
		public JSONYelpBusiness() {
		}
		public String avg_rating;
		public String id;
		public String mobile_url;
		public String review_count;
		public String rating_img_url_small;
		public String name;
		public String url;
		public String photo_url_small;
	}
}
