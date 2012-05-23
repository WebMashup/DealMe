package me.deal.client.view.main;

import java.util.ArrayList;

import me.deal.client.events.DealsEvent;
import me.deal.client.events.DealsEventHandler;
import me.deal.client.events.DealsLocationEvent;
import me.deal.client.events.DealsLocationEventHandler;
import me.deal.client.model.Deals;
import me.deal.client.model.DealsLocation;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.shared.Deal;
import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapClickHandler.MapClickEvent;
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
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
        
        
        ArrayList <Marker> currentMarks = new ArrayList();
        mapWidget.setSize("350px", "350px");
        mapWidget.addControl(new LargeMapControl());
        mapWidget.setZoomLevel(14);
        eventBus.addHandler(DealsLocationEvent.TYPE,
                new DealsLocationEventHandler() {
            
            @Override
            public void onDealsLocation(DealsLocationEvent event) {
                
                mapWidget.setCenter(DealsLocation.getInstance().getDealsLocation().getLatLng().convert());
                System.out.println("TEST");
            }
        });


        eventBus.addHandler(DealsEvent.TYPE,
                new DealsEventHandler(){
        			@Override
                    public void onDeals(DealsEvent event) {
                        markerUpdate(Deals.getInstance().getDeals(), 100);
                    }
                }
            );
        
        mapWidget.addMapDragEndHandler(new MapDragEndHandler(){
        	public void onDragEnd(MapDragEndEvent e)
        	{
        		Location loc = new Location();
        		loc.setLatLng(new LatLngCoor(mapWidget.getCenter().getLatitude(), mapWidget.getCenter().getLongitude()));
        		DealsLocation.getInstance().setDealsLocation(loc);
                eventBus.fireEvent(new DealsLocationEvent() {
                	}
                );
        	}
   	    }
        );
    
    }
    
    private void markerUpdate(ArrayList<Deal> llist, int number)
    {
        mapWidget.clearOverlays(); 
        int max = 0;
        if(number >= llist.size())
            max = llist.size();
        else
            max = number;
        
        for(int i = 0; i < max; i++)
        {
            mapWidget.addOverlay(createMarker(llist.get(i)));
        }
    }
    
    private void centerMarker(final Deal current)
    {
        mapWidget.setCenter(current.getBusinessAddress().getLatLng().convert());
        InfoWindowContent window;
        try{
                window  = new InfoWindowContent(current.getDealBusinessInfo().getName() + "<br>" + current.getBusinessPhoneNumber() + "</br>");                 
            } catch(NullPointerException n) {
                window  = new InfoWindowContent(current.getTitle() + "<br>" + current.getBusinessPhoneNumber() + "</br>");                 
            }        
            window.setMaxWidth(25);
            mapWidget.getInfoWindow().open(mapWidget.getCenter(), window);
    }
    
    private Marker createMarker(final Deal current)
    {
        final Marker temp = new Marker(current.getBusinessAddress().getLatLng().convert());
        temp.addMarkerClickHandler(new MarkerClickHandler() {
            public void onClick(MarkerClickEvent e)
            {
            	InfoWindowContent window;
                try{
                    window  = new InfoWindowContent(current.getDealBusinessInfo().getName() + "<br>" + current.getBusinessPhoneNumber() + "</br>");                 
                } catch(NullPointerException n) {
                    window  = new InfoWindowContent(current.getTitle() + "<br>" + current.getBusinessPhoneNumber() + "</br>");                 
                }
                window.setMaxWidth(25);
                mapWidget.getInfoWindow().open(temp, window);
            }
        });
        return temp;
    }
    
}

