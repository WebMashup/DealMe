package me.deal.client.servlets;

import com.google.gwt.user.client.rpc.AsyncCallback;

import me.deal.shared.Directions;
import me.deal.shared.Location;

/**
 * The async counterpart of @DirectionsService.
 */
public interface DirectionsServiceAsync {
	void getDirections(Location startPoint, Location endPoint, AsyncCallback<Directions> callback);
}
