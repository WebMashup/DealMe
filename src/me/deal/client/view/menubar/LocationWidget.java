package me.deal.client.view.menubar;

import me.deal.client.events.DealsLocationEvent;
import me.deal.client.events.DealsLocationEventHandler;
import me.deal.client.model.DealsLocation;
import me.deal.client.servlets.GeocodingServiceAsync;
import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.base.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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
	TextBox address;
	
	@UiField
	TextBox city;
	
	@UiField
	TextBox state;
	
	@UiField
	TextBox zip;
	
	@UiField
	Button changeLocationButton;
	
	@UiHandler("address")
	void onAddressChange(ChangeEvent event) {
		// TODO Check for valid input
	}
	
	@UiHandler("city")
	void onCityChange(ChangeEvent event) {
		// TODO Check for valid input
	}
	
	@UiHandler("state")
	void onStateChange(ChangeEvent event) {
		// TODO Check for valid input
	}
	
	@UiHandler("zip")
	void onZipChange(ChangeEvent event) {
		// TODO Check for valid input
	}
	
	@UiHandler("changeLocationButton")
	void handleClick(ClickEvent e) {
		String addressValue = address.getValue();
		String cityValue = city.getValue();
		String stateValue = state.getValue();
		String zipValue = zip.getValue();
		
		Location loc = new Location();
		loc.setAddress(addressValue);
		loc.setCity(cityValue);
		loc.setState(stateValue);
		loc.setZipCode(zipValue);

		geocodingService.convertAddressToLatLng(loc, new AsyncCallback<LatLngCoor>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				// Failed, do nothing
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(LatLngCoor result) {
				// TODO Auto-generated method stub
				Location userLoc = DealsLocation.getInstance().getDealsLocation();
				userLoc.setLatLng((LatLngCoor) result);
				eventBus.fireEvent(new DealsLocationEvent());
			}
			
		});    
	}
	
	public @UiConstructor LocationWidget(final GeocodingServiceAsync geocodingService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.geocodingService = geocodingService;
		this.eventBus = eventBus;
		
		initialize();
	}
	
	private void initialize() {
		changeLocationButton.setEnabled(false);
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
					String line1 = "Current address: " + userLoc.getAddress() + ", " + userLoc.getCity() + ", " + userLoc.getState() + " " + userLoc.getZipCode();
					addressLine1.setText(line1);
					changeLocationButton.setEnabled(true);
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
