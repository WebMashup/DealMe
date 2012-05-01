package me.deal.server;

import java.util.ArrayList;

import me.deal.client.servlets.DealService;
import me.deal.shared.BusinessInfo;
import me.deal.shared.Category;
import me.deal.shared.Deal;
import me.deal.shared.LatLng;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DealServiceImpl extends RemoteServiceServlet implements
		DealService {
	
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
		
		return null;
		
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
