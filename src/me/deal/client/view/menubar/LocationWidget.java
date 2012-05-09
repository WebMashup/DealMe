package me.deal.client.view.menubar;

import me.deal.client.servlets.GeolocationServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class LocationWidget extends Composite {

	private static LocationWidgetUiBinder uiBinder = GWT
			.create(LocationWidgetUiBinder.class);

	interface LocationWidgetUiBinder extends UiBinder<Widget, LocationWidget> {
	}

	private final GeolocationServiceAsync geolocationService;
	private final HandlerManager eventBus;
	
	public LocationWidget(final GeolocationServiceAsync geolocationService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.geolocationService = geolocationService;
		this.eventBus = eventBus;
		initialize();
	}
	
	private void initialize() {
		
	}
}
