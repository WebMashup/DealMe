package me.deal.client.servlets;

import java.util.ArrayList;

import me.deal.shared.BusinessInfo;
import me.deal.shared.Category;
import me.deal.shared.Deal;
import me.deal.shared.LatLngCoor;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.  DealService is the RPC
 * Service used for retrieving information about deals from the server.
 */

@RemoteServiceRelativePath("deal")
public interface DealService extends RemoteService {
	// Get limit # of deals from yipit within radius distance of coor, using the filter tags.
	// Returns null if no deals found
	ArrayList<Deal> getYipitDeals(LatLngCoor coor, Double radius, Integer limit, Integer offset, ArrayList<Category> tags);
	// Get yelp reviews for a business by phone number
	// Returns null if the business is not found
	BusinessInfo lookupYelpByPhone(String phoneNumber);
}
