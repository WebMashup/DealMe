package me.deal.client.servlets;

import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of @GeocodingService.
 */
public interface GeocodingServiceAsync {
	void convertLatLngToAddress(LatLngCoor latLng, AsyncCallback<Location> callback);
	void convertAddressToLatLng(Location address, AsyncCallback<LatLngCoor> callback);
}
