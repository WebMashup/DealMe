package me.deal.client.servlets;

import me.deal.shared.LatLng;
import me.deal.shared.Location;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of @GeolocationService.
 */
public interface GeolocationServiceAsync {
	void getUserLatLng(AsyncCallback<LatLng> callback);
	void geolocateLocation(Location loc, AsyncCallback<LatLng> callback);
}
