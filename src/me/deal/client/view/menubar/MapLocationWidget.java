package me.deal.client.view.menubar;

import java.util.ArrayList;

import me.deal.client.events.DealsEvent;
import me.deal.client.events.DealsEventHandler;
import me.deal.client.model.Deals;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.client.servlets.GeocodingServiceAsync;
import me.deal.shared.Deal;
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
import com.google.gwt.user.client.ui.Widget;

public class MapLocationWidget extends Composite {

    private static LocationWidgetUiBinder uiBinder = GWT
            .create(LocationWidgetUiBinder.class);

    interface LocationWidgetUiBinder extends UiBinder<Widget, MapLocationWidget> {
    }
    
    private final GeocodingServiceAsync geocodingService;
    private final DealServiceAsync dealService;
    private final HandlerManager eventBus;
    
    private Integer loadingState = 0;
    private String[] loadingStrings = {
            "Acquiring location", "Acquiring location.",
            "Acquiring location..", "Acquiring location..."};
    private final Integer LOADING_DELAY = 100;
    private Boolean locationInitialized = false;
    private boolean mapView = false;
    
    public void setMapSize(boolean mapView)
    {
        this.mapView = mapView;
    }
    
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
                addressLine1.setText("");
                Location userLoc = Deals.getInstance().getLocation();
                userLoc.setLatLng((LatLngCoor) result);
                
                geocodingService.convertLatLngToAddress(result, new AsyncCallback<Location>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        // TODO Auto-generated method stub
                        
                    }

                    @Override
                    public void onSuccess(Location result) {
                        // TODO Auto-generated method stub
                        String line1 = "Current address: " + result.getAddress() + ", " + result.getCity() + ", " + result.getState() + " " + result.getZipCode();
                        addressLine1.setText(line1);
                        Deals deals = Deals.getInstance();
                        deals.setLocation(result);
                        deals.setUserLocation(result);
                        
                        dealService.getYipitDeals(deals.getLocation().getLatLng(),
                                deals.getRadius(),
                                deals.DEFAULT_NUM_DEALS,
                                deals.getOffset(),
                                deals.getTags(),
                                new AsyncCallback<ArrayList<Deal>>() {
                                    @Override
                                    public void onFailure(Throwable caught) {
                                        Window.alert("Failed to load deals.");
                                    }

                                    @Override
                                    public void onSuccess(ArrayList<Deal> result) {
                                        Deals deals = Deals.getInstance();
                                        deals.setDeals(result);
                                        //deals.setLoadsSinceLastReset(new Integer(0));
                                        deals.setOffset(result.size());
                                        
                                        eventBus.fireEvent(new DealsEvent());
                                    }
                        });
                    }
                });
            }
            
        });    
    }
    
    public @UiConstructor MapLocationWidget(final GeocodingServiceAsync geocodingService,
            final DealServiceAsync dealService,
            final HandlerManager eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        this.geocodingService = geocodingService;
        this.dealService = dealService;
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
        
        eventBus.addHandler(DealsEvent.TYPE,
                new DealsEventHandler() {
            @Override
            public void onDeals(DealsEvent event) {
                if(locationInitialized.equals(false)) {
                    t.cancel();
                    Location userLoc = Deals.getInstance().getLocation();
                    Deals.getInstance().setUserLocation(userLoc);
                    String line1 = "Current address: " + userLoc.getAddress() + ", " + userLoc.getCity() + ", " + userLoc.getState() + " " + userLoc.getZipCode();
                    addressLine1.setText(line1);
                    changeLocationButton.setEnabled(true);
                    locationInitialized = true;
                }
            }
        });
    }
}
