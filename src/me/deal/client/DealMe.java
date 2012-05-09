package me.deal.client;

import java.util.ArrayList;

import me.deal.client.model.UserLocation;
import me.deal.client.servlets.DealService;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.client.servlets.DirectionsService;
import me.deal.client.servlets.DirectionsServiceAsync;
import me.deal.client.servlets.GeocodingService;
import me.deal.client.servlets.GeocodingServiceAsync;
import me.deal.client.view.header.HeaderWidget;
import me.deal.client.view.main.GoogleMapWidget;
import me.deal.client.view.main.ListWidget;
import me.deal.client.view.menubar.FilterWidget;
import me.deal.client.view.menubar.LocationWidget;
import me.deal.client.view.menubar.MenuWidget;
import me.deal.shared.Category;
import me.deal.shared.Deal;
import me.deal.shared.LatLng;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DealMe extends Composite implements EntryPoint {
	
	@UiField
	HeaderWidget headerWidget;
	
	@UiField
	MenuWidget menuWidget;
	@UiField
	FilterWidget filterWidget;
	@UiField
	LocationWidget locationWidget;
	
	@UiField
	ListWidget listWidget;
	@UiField
	GoogleMapWidget googleMapWidget;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DealServiceAsync dealService = GWT.create(DealService.class);
		DirectionsServiceAsync directionsService = GWT.create(DirectionsService.class);
		GeocodingServiceAsync geocodingService = GWT.create(GeocodingService.class);
		HandlerManager eventBus = new HandlerManager(null);
		menuWidget = new MenuWidget(eventBus);
		filterWidget = new FilterWidget(dealService, eventBus);
		locationWidget = new LocationWidget(geocodingService, eventBus);
		listWidget = new ListWidget(directionsService, eventBus);
		googleMapWidget = new GoogleMapWidget(dealService, eventBus);
		
		getUserLocation();
	}
	
	private void getUserLocation() {
		Geolocation geo;
		if((geo = Geolocation.getIfSupported()) != null) {
			geo.getCurrentPosition(
			new Callback<Position, PositionError>() {
					@Override
					public void onFailure(PositionError reason) {
						Window.alert(reason.toString());
					}

					@Override
					public void onSuccess(Position result) {
						Coordinates userCoor = result.getCoordinates();
						LatLng userLatLng = new LatLng(userCoor.getLatitude(), userCoor.getLongitude());
						// TODO: Set the Location in the model to userLatLng and then fire a UserLocationEvent
					}
			});
		}
	}
}
