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
    private boolean largeMap;
    
    @UiField
    MapWidget mapWidget;
    
    public void setMapSize(boolean mapView)
    {
        this.largeMap = mapView;
    }
    
    
    public @UiConstructor GoogleMapWidget(final DealServiceAsync dealService,
            final HandlerManager eventBus, boolean largeMap) {
        initWidget(uiBinder.createAndBindUi(this));
        this.dealService = dealService;
        this.eventBus = eventBus;
        this.largeMap = largeMap;
        initialize();
    }
    
    public boolean dragged = false;
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

    private int boundsToFarthestDeal(ArrayList<Deal> llist)
    {
        double distance = 0;
        int zoom = 0;
        for(int i = 0; i < llist.size(); i++)
        {
            LatLng sw = Deals.getInstance().getLocation().getLatLng().convert();
            LatLng ne =  llist.get(i).getBusinessAddress().getLatLng().convert();        
            if(sw.getLatitude() > ne.getLatitude())
            {
                LatLng temp = ne;
                ne = sw;
                sw = temp;
            }
            if(sw.getLongitude() > ne.getLongitude())
            {
                LatLng temp = sw;
                sw = LatLng.newInstance(temp.getLatitude(), temp.getLongitude() - 2*(temp.getLongitude() - ne.getLongitude()));
            }
            LatLngBounds a = LatLngBounds.newInstance(sw, ne);
            if(boundsToRadius(a) > distance)
            {
                distance = boundsToRadius(a);
                zoom = mapWidget.getBoundsZoomLevel(a);
            }
        }
        return zoom;
    }
    
    ArrayList <Marker> currentMarks = new ArrayList();
    private void initialize() {
        
        if (largeMap)
        {
            mapWidget.setSize("100%", "100%");
            mapWidget.setZoomLevel(13);
        }
        else
        mapWidget.setSize("350px", "350px");

        mapWidget.addControl(new LargeMapControl());
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
                    if(!dragged && !largeMap && Deals.getInstance().getResize())
                    {
                    mapWidget.setZoomLevel(boundsToFarthestDeal(Deals.getInstance().getDeals()) - 1);
                    }
                    Deals.getInstance().setResize(true);
                    dragged = false;
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
                if(largeMap)
                    numDealsToLoad = 20;
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
                            deals.setOffset(result.size());
                            deals.setDeals(result);
                            dragged = true;
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
                Deals.getInstance().setRadius(boundsToRadius(mapWidget.getBounds()));
            }
        });
        

    }
    
    
    private void markerUpdate(int number)
    {
        mapWidget.clearOverlays(); 
        int max = 0;
        int start = 0;
        if(number >= currentMarks.size())
            max = currentMarks.size();
        else
            max = number;
        
        if(!(Deals.getInstance().getRangeStart() == 0 && Deals.getInstance().getRangeEnd() == 0))
        {
        	start = Deals.getInstance().getRangeStart();
        	max = Deals.getInstance().getRangeEnd();
        	Deals.getInstance().setRange(0,0);
        }
        	
        for(int i = start; i < max; i++)
        {
            mapWidget.addOverlay(currentMarks.get(i));
          //  if(i > 0 && currentMarks.get(i).getLatLng().getLatitude() == currentMarks.get(i - 1).getLatLng().getLatitude() && currentMarks.get(i).getLatLng().getLongitude() == currentMarks.get(i - 1).getLatLng().getLongitude())
           //     currentMarks.get(i).setVisible(false);
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
                if(!largeMap)
                {
                    try
                    {
                        window  = new InfoWindowContent(current.getDealBusinessInfo().getName() + "<br>" + current.getBusinessPhoneNumber() + "</br>");                 
                    } 
                    catch(NullPointerException n) 
                    {
                        window  = new InfoWindowContent(current.getTitle() + "<br>" + current.getBusinessPhoneNumber() + "</br>");                 
                    }
                    window.setMaxWidth(25);
                }
                else
                {
                    ListItemWidget temp = new ListItemWidget();
                    if (current.getDealBusinessInfo() != null)
                    {
                        temp.setAvgRatingImageUrl(current.getDealBusinessInfo().getAvgRatingImageUrl());
                        temp.setBusinessName(current.getDealBusinessInfo().getName());
                        temp.setNumReviews(current.getDealBusinessInfo().getNumReviews());
                        temp.setReviewsUrl(current.getDealBusinessInfo().getWebUrl());
                    }

                    temp.setTitle(current.getTitle());
                    temp.setSubtitle(current.getSubtitle());
                    temp.setPrice(current.getPrice());
                    temp.setDiscountPercentage(current.getDiscountPercentage());
                    temp.setBusinessAddress(current.getBusinessAddress());
                    temp.setBigImageUrl(current.getBigImageUrl());
                    temp.setYipitUrl(current.getYipitWebUrl());
                    temp.setEndDate(current.getEndDate());
                    temp.setDealSource(current.getDealSource());
                    window = new InfoWindowContent(temp);
                }
                mapWidget.getInfoWindow().open(temp, window);
            }
        });

        return temp;
    }
    
    public void manualUpdate()
    {
    	for(int i = 0; i< currentMarks.size();i++)
    	{
    		currentMarks.get(i).setVisible(false);
    	}
        markerUpdate(100); 	
    }
    
    public void centerMarker(Deal current)
    {
        mapWidget.setCenter(current.getBusinessAddress().getLatLng().convert());
    }
}