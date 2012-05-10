package me.deal.client.view.menubar;

import me.deal.client.servlets.GeocodingServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LocationWidget extends Composite {

	private static LocationWidgetUiBinder uiBinder = GWT
			.create(LocationWidgetUiBinder.class);

	interface LocationWidgetUiBinder extends UiBinder<Widget, LocationWidget> {
	}

	private final GeocodingServiceAsync geocodingService;
	private final HandlerManager eventBus;
	
	public LocationWidget(final GeocodingServiceAsync geocodingService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.geocodingService = geocodingService;
		this.eventBus = eventBus;
		initialize();
	}
	
	private void initialize() {
		/*
		 * When you modify the UserLocation singleton be sure to fire this event
		 * to notify other people the UserLocation singleton has been modified.
		 * 
		 * eventBus.fireEvent(new UserLocation());
		 */
	}
}
