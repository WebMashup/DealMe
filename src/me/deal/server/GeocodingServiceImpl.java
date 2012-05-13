package me.deal.server;

import me.deal.client.servlets.GeocodingService;
import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;
import me.deal.shared.json.JSONGoogleMapsLocations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GeocodingServiceImpl extends RemoteServiceServlet implements
	GeocodingService {

	@Override
	public Location convertLatLngToAddress(LatLngCoor latLng) {
		String endPoint = "http://maps.googleapis.com/maps/api/geocode/json";
		String requestParameters = generateLatLngParamterStr(latLng);
		String response = HttpSender.sendGetRequest(endPoint, requestParameters);
		
		Gson gson = new GsonBuilder().create();
		JSONGoogleMapsLocations googleMapsLocations = gson.fromJson(response, JSONGoogleMapsLocations.class);
		Location userLocation = parseUserLocation(googleMapsLocations);
		
		return userLocation;
	}

	@Override
	public LatLngCoor convertAddressToLatLng(Location address) {
		String endPoint = "http://maps.googleapis.com/maps/api/geocode/json";
		String requestParameters = generateAddressParamterStr(address);
		String response = HttpSender.sendGetRequest(endPoint, requestParameters);
		
		Gson gson = new GsonBuilder().create();
		JSONGoogleMapsLocations googleMapsLocations = gson.fromJson(response, JSONGoogleMapsLocations.class);
		LatLngCoor userLatLng = parseUserLatLng(googleMapsLocations);
		
		return userLatLng;
	}
	
	private LatLngCoor parseUserLatLng(JSONGoogleMapsLocations googleMapsLocations) {
		
		if(googleMapsLocations.results.size() == 0)
			return null;
		
		return new LatLngCoor(
				googleMapsLocations.results.get(0).geometry.location.lat,
				googleMapsLocations.results.get(0).geometry.location.lng);
	}
	
	private Location parseUserLocation(JSONGoogleMapsLocations googleMapsLocations) {
		if(googleMapsLocations.results.size() == 0)
			return null;
		
		String fullAddress = googleMapsLocations.results.get(0).formatted_address;
		String address = fullAddress.substring(0, fullAddress.indexOf(","));
		fullAddress = fullAddress.substring(fullAddress.indexOf(",") + 2);
		String city = fullAddress.substring(0, fullAddress.indexOf(","));
		fullAddress = fullAddress.substring(fullAddress.indexOf(",") + 2);
		String state = fullAddress.substring(0, fullAddress.indexOf(" "));
		fullAddress = fullAddress.substring(fullAddress.indexOf(" ") + 1);
		String zipCode = fullAddress.substring(0, fullAddress.indexOf(","));
		
		LatLngCoor userLatLng = new LatLngCoor(
				googleMapsLocations.results.get(0).geometry.location.lat,
				googleMapsLocations.results.get(0).geometry.location.lng);
		
		Location userLocation = new Location(address, city, state, zipCode, userLatLng);
		
		return userLocation;
	}
	
	private String generateLatLngParamterStr(LatLngCoor latLng) {
		return "latlng="+latLng.getLatitude()+","+latLng.getLongitude()+"&sensor=false";
	}
	
	private String generateAddressParamterStr(Location address) {
		return "address=" + address.getAddress().replaceAll(" ", "+") + ",+" +
				address.getCity().replaceAll(" ", "+") + ",+" + address.getState() +
				"+" + address.getZipCode() + "&sensor=false";
				
	}
}