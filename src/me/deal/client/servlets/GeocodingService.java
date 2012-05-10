package me.deal.client.servlets;

import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.  GeocodingService is the
 * RPC service used to convert between latlng and an address.
 */
@RemoteServiceRelativePath("geocoding")
public interface GeocodingService extends RemoteService {
	/*
	 *  Given a lattitude and longitude, convert it to an address
	 */
	Location convertLatLngToAddress(LatLngCoor latLng);
	
	/*
	 * Given an address (location where latLng is null), return
	 * a LatLng 
	 */
	LatLngCoor convertAddressToLatLng(Location address);
}
