package me.deal.client.view.main;

import me.deal.client.events.DealsLocationEvent;
import me.deal.client.events.DealsLocationEventHandler;
import me.deal.client.events.UserLocationEvent;
import me.deal.client.events.UserLocationEventHandler;
import me.deal.client.model.DealsLocation;
import me.deal.client.model.UserLocation;
import me.deal.client.servlets.DealServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GoogleMapWidget extends Composite {

	private static GoogleMapWidgetUiBinder uiBinder = GWT
			.create(GoogleMapWidgetUiBinder.class);

	interface GoogleMapWidgetUiBinder extends UiBinder<Widget, GoogleMapWidget> {
	}

	private final DealServiceAsync dealService;
	private final HandlerManager eventBus;
	
	@UiField
	MapWidget mapWidget;
	
	public @UiConstructor GoogleMapWidget(final DealServiceAsync dealService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dealService = dealService;
		this.eventBus = eventBus;
		initialize();
	}

	private void initialize() {
		mapWidget.setSize("350px", "350px");
		
		eventBus.addHandler(DealsLocationEvent.TYPE,
				new DealsLocationEventHandler() {
			
			@Override
			public void onDealsLocation(DealsLocationEvent event) {
				mapWidget.setZoomLevel(12);
				mapWidget.setCenter(DealsLocation.getInstance().getDealsLocation().getLatLng().convert());
			}
        });
	}
}
