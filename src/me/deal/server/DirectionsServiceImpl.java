package me.deal.server;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import me.deal.client.servlets.DirectionsService;
import me.deal.shared.DirectionSegment;
import me.deal.shared.Directions;
import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;
import me.deal.shared.json.JSONGoogleDirections;
import me.deal.shared.json.JSONGoogleDirections.JSONRoutes.JSONLeg.JSONStep;
import me.deal.shared.json.JSONYipitDeals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DirectionsServiceImpl extends RemoteServiceServlet implements
	DirectionsService {

	public Directions getDirections(Location startPoint, Location endPoint) {
		
		String endPointAddress = "http://maps.googleapis.com/maps/api/directions/json";
		String requestParameters = generateParamterStr(startPoint, endPoint);
		
		String response;
		try {
			response = HttpSender.sendGetRequest(endPointAddress, requestParameters);
		} catch(SocketTimeoutException e) {
			return null;
		}
		
		System.out.println(response);
		
		Gson gson = new GsonBuilder().create();
		JSONGoogleDirections googleDirections = gson.fromJson(response, JSONGoogleDirections.class);
		Directions directions = convertGoogleDirections(googleDirections, startPoint, endPoint);
		
		return directions;
	}
	
	private String generateParamterStr(Location startPoint, Location endPoint) {
		String parameterStr = "";
		parameterStr += "origin=" + startPoint.getAddress() + "," + startPoint.getCity() +
				"," + startPoint.getState() + "," + startPoint.getZipCode();
		parameterStr += "&destination=" + endPoint.getAddress() + "," + endPoint.getCity() +
				"," + endPoint.getState() + "," + endPoint.getZipCode();
		parameterStr += "&sensor=false";
		parameterStr = parameterStr.replaceAll(" ", "%20");
		return parameterStr;
	}
	
	private Directions convertGoogleDirections(JSONGoogleDirections googleDirections,
			Location startPoint, Location endPoint) {
		
		ArrayList<DirectionSegment> segments = new ArrayList<DirectionSegment>();
		for(JSONStep step : googleDirections.routes.get(0).legs.get(0).steps) {
			segments.add(new DirectionSegment(
					step.distance.text,
					step.duration.text,
					new LatLngCoor(step.start_location.lat, step.start_location.lng),
					new LatLngCoor(step.end_location.lat, step.end_location.lng),
					step.html_instructions));
		}
		
		Directions directions = new Directions(
				startPoint,
				endPoint,
				new LatLngCoor(googleDirections.routes.get(0).bounds.northeast.lat,
						googleDirections.routes.get(0).bounds.northeast.lng),
				new LatLngCoor(googleDirections.routes.get(0).bounds.southwest.lat,
						googleDirections.routes.get(0).bounds.southwest.lng),
				googleDirections.routes.get(0).legs.get(0).distance.text,
				googleDirections.routes.get(0).legs.get(0).duration.text,
				segments);
		
		return directions;
	}
}
