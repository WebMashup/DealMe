package me.deal.server;

import java.net.SocketTimeoutException;

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
		String response;
		try {
			response = HttpSender.sendGetRequest(endPoint, requestParameters);
		} catch(SocketTimeoutException e) {
			return null;
		}
		Gson gson = new GsonBuilder().create();
		JSONGoogleMapsLocations googleMapsLocations = gson.fromJson(response, JSONGoogleMapsLocations.class);
		Location userLocation = parseUserLocation(googleMapsLocations);
		
		return userLocation;
	}

	@Override
	public LatLngCoor convertAddressToLatLng(Location address) {
		String endPoint = "http://maps.googleapis.com/maps/api/geocode/json";
		String requestParameters = generateAddressParamterStr(address);
		System.out.println("requestParameters");
		String response;
		try {
			response = HttpSender.sendGetRequest(endPoint, requestParameters);
		} catch(SocketTimeoutException e) {
			return null;
		}
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
		String addressRequest = "address=";
		if (address.getAddress() != null && !address.getAddress().isEmpty()) {
			addressRequest += address.getAddress().replace(" ", "+");
			addressRequest += ",+";
		}
		
		if (address.getCity() != null && !address.getCity().isEmpty()) {
			addressRequest += address.getCity().replace(" ", "+");
			addressRequest += ",+";
		}
		
		if (address.getState() != null && !address.getState().isEmpty()) {
			addressRequest += address.getState().replace(" ", "+");
			addressRequest += ",+";
		}
		
		if (address.getZipCode() != null && !address.getZipCode().isEmpty()) {
			addressRequest += address.getZipCode().replace(" ", "+");
			addressRequest += ",+";
		}
		
		addressRequest += "&sensor=false";
		
		return addressRequest;
	}
}
