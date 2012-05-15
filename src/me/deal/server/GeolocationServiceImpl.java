package me.deal.server;

import me.deal.client.servlets.GeolocationService;
import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GeolocationServiceImpl extends RemoteServiceServlet implements
		GeolocationService {

	public LatLngCoor getUserLatLng() {
		final String clientIPAddress = getThreadLocalRequest().getRemoteAddr();
		/* TODO: Use the maxmind geolocation API to convert the user's
		 * IP address to a Lat, and Lng.
		 */
		return new LatLngCoor(34.067297, -118.453176);
	}
	
	public LatLngCoor geolocateLocation(Location loc) {
		/*
		 * TODO: Use the Google maps api to convert the client-provided location
		 * to a LatLng.
		 */
		return new LatLngCoor(34.067297, -118.453176);
	}
}
