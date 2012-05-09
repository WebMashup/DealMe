package me.deal.client;

import java.util.ArrayList;

import me.deal.client.servlets.DealService;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.client.servlets.DirectionsService;
import me.deal.client.servlets.DirectionsServiceAsync;
import me.deal.client.servlets.GeolocationService;
import me.deal.client.servlets.GeolocationServiceAsync;
import me.deal.client.view.header.HeaderWidget;
import me.deal.client.view.main.GoogleMapWidget;
import me.deal.client.view.main.ListWidget;
import me.deal.client.view.menubar.FilterWidget;
import me.deal.client.view.menubar.LocationWidget;
import me.deal.client.view.menubar.MenuWidget;
import me.deal.shared.Category;
import me.deal.shared.Deal;
import me.deal.shared.LatLng;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
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
		GeolocationServiceAsync geolocationService = GWT.create(GeolocationService.class);
		HandlerManager eventBus = new HandlerManager(null);
		menuWidget = new MenuWidget(eventBus);
		filterWidget = new FilterWidget(dealService, eventBus);
		locationWidget = new LocationWidget(geolocationService, eventBus);
		listWidget = new ListWidget(directionsService, eventBus);
		googleMapWidget = new GoogleMapWidget(dealService, eventBus);
	}
}
