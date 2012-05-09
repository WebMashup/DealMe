package me.deal.server;

import java.util.ArrayList;

import me.deal.client.servlets.DealService;
import me.deal.shared.BusinessInfo;
import me.deal.shared.Category;
import me.deal.shared.Deal;
import me.deal.shared.json.JSONYipitDeals;
import me.deal.shared.json.JSONYipitDeals.JSONYipitDeal;
import me.deal.shared.json.JSONYipitDeals.JSONYipitDeal.JSONTag;
import me.deal.shared.LatLng;
import me.deal.shared.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DealServiceImpl extends RemoteServiceServlet implements
		DealService {

	final String apiKey = "tvw7u8QvGqetCNUf";
	
	/*
	 *  Implemented using the Yipit API.  Returns deals relating to the tags within radius distance of
	 *  the given coordinate.  Returns null if no deals found.
	 */
	public ArrayList<Deal> getYipitDeals(LatLng coor, Double radius, Integer limit, ArrayList<Category> tags) {
		/*
		 * TODO: Implement this method.  Get the deals using the Yipit API and parse the result into
		 * Deal java items, returning them to the user.  See http://yipit.com/about/api/documentation/
		 * for reference.
		 * Yipit API Key = tvw7u8QvGqetCNUf
		 */
		
		String endPoint = "http://api.yipit.com/v1/deals/";
		String requestParameters = generateParamterStr(coor, radius, limit, tags);
		String response = HttpSender.sendGetRequest(endPoint, requestParameters);
		response = formatResponse(response);
		System.out.println(response);
		
		Gson gson = new GsonBuilder().create();
		JSONYipitDeals yipitDeals = gson.fromJson(response, JSONYipitDeals.class);
		ArrayList<Deal> deals = convertYipitDeals(yipitDeals);
		
		return deals;
	}
	
	private ArrayList<Deal> convertYipitDeals(JSONYipitDeals yipitDeals) {
		
		ArrayList<Deal> deals = new ArrayList<Deal>();
		
		for(JSONYipitDeal jsonDeal : yipitDeals.deals) {
			
			ArrayList<Category> tags = new ArrayList<Category>();
			for(JSONTag tag : jsonDeal.tags) {
				tags.add(Category.getCategory(tag.slug));
			}
			
			LatLng latLng = null;
			if(jsonDeal.business.locations.get(0).lat != null &&
				jsonDeal.business.locations.get(0).lon != null) {
				latLng = new LatLng(new Double(jsonDeal.business.locations.get(0).lat),
						new Double(jsonDeal.business.locations.get(0).lon));
			}
			
			Deal deal = new Deal(
					jsonDeal.id,
					jsonDeal.date_added,
					jsonDeal.end_date,
					new Double(jsonDeal.discount.raw),
					new Double(jsonDeal.price.raw),
					new Double(jsonDeal.value.raw),
					jsonDeal.title,
					jsonDeal.yipit_title,
					jsonDeal.yipit_url,
					jsonDeal.mobile_url,
					jsonDeal.images.image_big,
					jsonDeal.images.image_small,
					new Location(jsonDeal.business.locations.get(0).address, "",
							jsonDeal.business.locations.get(0).state,
							jsonDeal.business.locations.get(0).zip_code, latLng),
					jsonDeal.business.locations.get(0).phone,
					new Boolean(jsonDeal.source.paid != 0),
					tags);
			
			deals.add(deal);
		}
		
		return deals;
	}
	
	private String formatResponse(String response) {
		String preBegin = "\"response\": {        ";
		String preEnd = "</pre>";
		return "{ " + response.substring(response.indexOf(preBegin) + preBegin.length(),
				response.indexOf(preEnd)-6) + " }";
	}
	
	private String generateParamterStr(LatLng coor, Double radius, Integer limit, ArrayList<Category> tags) {
		String parameterStr = "";
		parameterStr += "key=" + apiKey + "&";
		parameterStr += coor.getLatitude().isNaN() ? "" : "lat=" + coor.getLatitude() + "&";
		parameterStr += coor.getLongitude().isNaN() ? "" : "lon=" + coor.getLongitude() + "&";
		parameterStr += radius.isNaN() ? "" : "radius=" + radius + "&";
		parameterStr += limit.equals(null) ? "" : "limit=" + limit + "&";
		parameterStr += "tag="+getCategoryParams(tags);
		
		while(parameterStr.endsWith("&"))
			parameterStr.substring(0, parameterStr.length() - 2);
		
		return parameterStr;
	}
	
	private String getCategoryParams(ArrayList<Category> tags) {
		
		String parameterStr = "";
		for(Category tag : tags) {
			parameterStr += tag.getSlug() + ",";
		}
		
		while(parameterStr.endsWith(","))
			parameterStr = parameterStr.substring(0, parameterStr.length() - 2);
		
		return parameterStr;
	}
	
	public BusinessInfo lookupYelpByPhone(String phoneNumber) {
		/*
		 * TODO: Implement this method.  Given a phone number of the business use the Yelp API to
		 * find information about that business and load that info into a BusinessInfo object that
		 * is returned to the user.  See http://www.yelp.com/developers/documentation/phone_api
		 * Yelp API Key = ijeImBEdt2K2hS2Abub6FQ
		 */
		
		return null;
	}
}
