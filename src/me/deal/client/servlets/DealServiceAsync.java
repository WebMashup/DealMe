package me.deal.client.servlets;

import java.util.ArrayList;

import me.deal.shared.BusinessInfo;
import me.deal.shared.Category;
import me.deal.shared.Deal;
import me.deal.shared.LatLngCoor;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of @DealService.
 */
public interface DealServiceAsync {
	void getYipitDeals(LatLngCoor coor, Double radius, Integer limit, ArrayList<Category> tags,
			AsyncCallback<ArrayList<Deal>> callback);
	void lookupYelpByPhone(String phoneNumber, AsyncCallback<BusinessInfo> callback);
}
