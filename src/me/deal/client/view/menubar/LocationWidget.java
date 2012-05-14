package me.deal.client.view.menubar;

import java.util.ArrayList;

import me.deal.client.events.DealsLocationEvent;
import me.deal.client.events.DealsLocationEventHandler;
import me.deal.client.model.DealsLocation;
import me.deal.client.servlets.GeocodingServiceAsync;
import me.deal.shared.Location;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LocationWidget extends Composite {

	private static LocationWidgetUiBinder uiBinder = GWT
			.create(LocationWidgetUiBinder.class);

	interface LocationWidgetUiBinder extends UiBinder<Widget, LocationWidget> {
	}
	
	private final GeocodingServiceAsync geocodingService;
	private final HandlerManager eventBus;
	
	private Integer loadingState = 0;
	private String[] loadingStrings = {
			"Acquiring location", "Acquiring location.",
			"Acquiring location..", "Acquiring location..."};
	private final Integer LOADING_DELAY = 100;
	private Boolean locationInitialized = false;
	
	@UiField
	Label addressLine1;
	@UiField
	Label addressLine2;
	@UiField
	Button changeAddressButton;
	
	public @UiConstructor LocationWidget(final GeocodingServiceAsync geocodingService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.geocodingService = geocodingService;
		this.eventBus = eventBus;
		
		initialize();
	}
	
	private void initialize() {
		changeAddressButton.setEnabled(false);
		addressLine1.setText("Acquiring location");
		loadingState++;
		loadingState %= 3;
		final Timer t = new Timer() {
			public void run() {
				loadingState++;
				loadingState %= 3;
				addressLine1.setText(loadingStrings[loadingState]);
			}
		};
		t.schedule(LOADING_DELAY);
		
		eventBus.addHandler(DealsLocationEvent.TYPE,
				new DealsLocationEventHandler() {
			public void onDealsLocation(DealsLocationEvent event) {
				if(locationInitialized.equals(false)) {
					t.cancel();
					Location userLoc = DealsLocation.getInstance().getDealsLocation();
					String line1 = userLoc.getAddress();
					String line2 = userLoc.getCity() + ", " + userLoc.getState() + " " + userLoc.getZipCode();
					addressLine1.setText(line1);
					addressLine2.setText(line2);
					changeAddressButton.setEnabled(true);
					locationInitialized = true;
				}
			}
		});
		/*
		 * When you modify the UserLocation singleton be sure to fire this event
		 * to notify other people the UserLocation singleton has been modified.
		 * 
		 * eventBus.fireEvent(new UserLocation());
		 */
	}
}
