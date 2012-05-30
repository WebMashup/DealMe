package me.deal.client.view.main;

import java.util.ArrayList;

import me.deal.client.events.DealsEvent;
import me.deal.client.events.DealsEventHandler;
import me.deal.client.model.Deals;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.shared.Deal;
import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.event.MapZoomEndHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import static java.lang.Math.*;

public class GoogleMapWidget extends Composite {

    private static GoogleMapWidgetUiBinder uiBinder = GWT
            .create(GoogleMapWidgetUiBinder.class);

    interface GoogleMapWidgetUiBinder extends UiBinder<Widget, GoogleMapWidget> {
    }

    private final DealServiceAsync dealService;
    private final HandlerManager eventBus;
    private final boolean largeMap;
    
    @UiField
    MapWidget mapWidget;
    
    public @UiConstructor GoogleMapWidget(final DealServiceAsync dealService,
            final HandlerManager eventBus, boolean largeMap) {
        initWidget(uiBinder.createAndBindUi(this));
        this.dealService = dealService;
        this.eventBus = eventBus;
        this.largeMap = largeMap;
        
        initialize();
    }
    
    private double radius = 0.0;
    private LatLng currentCenter = LatLng.newInstance(0,0);
    
    private void setRadius(LatLngBounds coords)
    {
        radius = boundsToRadius(coords);
    }
    private double boundsToRadius(LatLngBounds coords)
    {
        LatLng NE = coords.getNorthEast();
        LatLng center = coords.getCenter();
        double lon = NE.getLongitude() - center.getLongitude();
        double lat = NE.getLatitude() - center.getLatitude();
        double a = pow(sin(lat/2), 2) + cos(center.getLatitude())*cos(NE.getLatitude())*pow(sin(lon/2), 2);
        double c = 2*atan2(sqrt(a), sqrt(1-a))*0.0174532925;
        return 3961.3 * c;
    }

    ArrayList <Marker> currentMarks = new ArrayList();
    private void initialize() {
        
    	if (largeMap)
    		mapWidget.setSize("100%", "100%");
    	else
        mapWidget.setSize("350px", "350px");
    	
        mapWidget.addControl(new LargeMapControl());
        mapWidget.setZoomLevel(14);

        eventBus.addHandler(DealsEvent.TYPE,
            new DealsEventHandler(){
                @Override
                public void onDeals(DealsEvent event) {
                	LatLng tempCenter = Deals.getInstance().getLocation().getLatLng().convert();                
                	if(!mapWidget.getCenter().equals(tempCenter)) {
                    	currentCenter = tempCenter;
                    	mapWidget.setCenter(currentCenter);
                    	setRadius(mapWidget.getBounds());
                	}
                    currentMarks.clear();
                    dealsToMarkers(Deals.getInstance().getDeals());
                    markerUpdate(100);
                }
            }
        );
        
        mapWidget.addMapDragEndHandler(new MapDragEndHandler(){
            public void onDragEnd(MapDragEndEvent e)
            {
                
                if(currentCenter != mapWidget.getCenter())
                {
                	Deals deals = Deals.getInstance();
                Location loc = deals.getLocation();
                loc.setLatLng(new LatLngCoor(mapWidget.getCenter().getLatitude(), mapWidget.getCenter().getLongitude()));
                deals.setLocation(loc);
                deals.setOffset(0);
                Integer numDealsToLoad = 7;
                dealService.getYipitDeals(deals.getLocation().getLatLng(),
	        		deals.getRadius(),
	        		numDealsToLoad,
	        		deals.getTags(),
	        		new AsyncCallback<ArrayList<Deal>>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Failed to load deals.");
						}
	
						@Override
						public void onSuccess(ArrayList<Deal> result) {
							Deals deals = Deals.getInstance();
							deals.setOffset(result.size());
							deals.setLoadsSinceLastReset(new Integer(0));
							deals.setDeals(result);
							eventBus.fireEvent(new DealsEvent());
						}
                	});
                }
            }
           }
        );
        
        mapWidget.addMapZoomEndHandler(new MapZoomEndHandler(){
            public void onZoomEnd(MapZoomEndEvent e)
            {
                setRadius(mapWidget.getBounds());
            }
        });
        
    }
    
    
    private void markerUpdate(int number)
    {
        mapWidget.clearOverlays(); 
        int max = 0;
        if(number >= currentMarks.size())
            max = currentMarks.size();
        else
            max = number;
        
        
        for(int i = 0; i < max; i++)
        {
        	mapWidget.addOverlay(currentMarks.get(i));
        	if(i > 0 && currentMarks.get(i).getLatLng().getLatitude() == currentMarks.get(i - 1).getLatLng().getLatitude() && currentMarks.get(i).getLatLng().getLongitude() == currentMarks.get(i - 1).getLatLng().getLongitude())
        		currentMarks.get(i).setVisible(false);
        }
    }
    
    private void dealsToMarkers(ArrayList<Deal> llist)
    {
        for(int i = 0; i < llist.size(); i++)
        {
        	
        	Marker temp = createMarker(llist.get(i));
            currentMarks.add(temp);
        }
    }
    
    
    private Marker createMarker(final Deal current)
    {
        
        Icon icon = Icon.newInstance(current.getIDUrl());
        icon.setInfoWindowAnchor(Point.newInstance(10, 10));
        icon.setShadowURL("http://www.google.com/mapfiles/shadow50.png");
        MarkerOptions ops = MarkerOptions.newInstance();
        ops.setClickable(true);
        ops.setIcon(icon);
        final Marker temp = new Marker(current.getBusinessAddress().getLatLng().convert(), ops);
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