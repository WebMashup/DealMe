package me.deal.client.servlets;

import me.deal.shared.LatLng;
import me.deal.shared.Location;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.  GeolocationService is the
 * RPC service used to geolocate the user's coordinates.  The client uses
 * this service to ask the server for its location.  The server geolocates
 * based on the user's IP address.
 */
@RemoteServiceRelativePath("geolocation")
public interface GeolocationService extends RemoteService {
	// Convert the user's IP address into a LatLng
	LatLng getUserLatLng();
	
	// Given an address, return a latitude and longitude coordinate
	LatLng geolocateLocation(Location loc);
}
