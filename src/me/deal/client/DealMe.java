package me.deal.client;

import java.util.ArrayList;

import me.deal.client.events.DealsEvent;
import me.deal.client.model.Deals;
import me.deal.client.servlets.DealService;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.client.servlets.DirectionsService;
import me.deal.client.servlets.DirectionsServiceAsync;
import me.deal.client.servlets.GeocodingService;
import me.deal.client.servlets.GeocodingServiceAsync;
import me.deal.client.view.header.HeaderWidget;
import me.deal.client.view.main.GoogleMapWidget;
import me.deal.client.view.main.ListWidget;
import me.deal.client.view.menubar.FilterWidget;
import me.deal.client.view.menubar.LocationWidget;
import me.deal.client.view.menubar.MenuWidget;
import me.deal.shared.Deal;
import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.ResponsiveNavbar;
import com.github.gwtbootstrap.client.ui.constants.Alignment;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DealMe implements EntryPoint {
	
    interface MyUiBinder extends UiBinder<Widget,DealMe> {
        MyUiBinder INSTANCE = GWT.create(MyUiBinder.class);
}


	private final DealServiceAsync dealService =  GWT.create(DealService.class);
	private final DirectionsServiceAsync directionsService = GWT.create(DirectionsService.class);
	private final GeocodingServiceAsync geocodingService = GWT.create(GeocodingService.class);
	
	private final HandlerManager eventBus = new HandlerManager(null);
	
//	@UiField
//	HeaderWidget headerWidget;
	
	GoogleMapWidget newMapWidget;
	
	@UiField (provided=true)
	ScrollPanel mainScrollPanel;
	
	@UiField (provided=true)
	FilterWidget filterWidget;	
	
//	@UiField (provided=true)
//	MenuWidget menuWidget;
	
	@UiField (provided=true)
	LocationWidget locationWidget;
	
	@UiField (provided=true)
	ListWidget listWidget;
	
	@UiField (provided=true)
	GoogleMapWidget googleMapWidget;
	
	@UiField 
	FlowPanel filterPanel;
	
	@UiField
	FlowPanel locationPanel;
	
	@UiField
	Button filterButton;
	
	@UiField
	Button locationButton;
	
	Widget w;
	
	ResponsiveNavbar navbar;
	
	VerticalPanel verti;
	
	FlowPanel listVert;
	
	FlowPanel mapVert;
	
	boolean mapView = false;
	
	boolean listView = true;
	
	PopupPanel popup;
	
	FlowPanel vert;
	
	@UiHandler("filterButton")
	void handleClick1(ClickEvent e) {
		if (filterPanel.isVisible())
			filterPanel.setVisible(false);
		else
		{
			if (locationPanel.isVisible())
			{
				locationPanel.setVisible(false);
				filterPanel.setVisible(true);
			}
			else
				filterPanel.setVisible(true);
		}
	}
	
	@UiHandler("locationButton")
	void handleClick(ClickEvent e) {
		if (locationPanel.isVisible())
			locationPanel.setVisible(false);
		else
		{
			if (filterPanel.isVisible())
			{
				filterPanel.setVisible(false);
				locationPanel.setVisible(true);
			}
			else
				locationPanel.setVisible(true);
		}
	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

//		headerWidget = new HeaderWidget(dealService, directionsService, eventBus);
		
		mainScrollPanel = new ScrollPanel();
		filterWidget = new FilterWidget(dealService, eventBus);
//		menuWidget = new MenuWidget(dealService, eventBus);
		locationWidget = new LocationWidget(geocodingService, dealService, eventBus);
		listWidget = new ListWidget(mainScrollPanel, dealService, directionsService, eventBus);
		googleMapWidget = new GoogleMapWidget(dealService, eventBus, false);
		
		getUserLocation();
		
		filterPanel = new FlowPanel();
		locationPanel = new FlowPanel();
		
		filterButton = new Button("Filters");
		locationButton = new Button("Location");	
		
		/** NAVIGATION BAR **/
		navbar = new ResponsiveNavbar();
		Brand brand = new Brand("Deal.Me");
		Button listLink = new Button("List View");
		Button mapLink = new Button("Map View");
		
		listLink.setStylePrimaryName("buttonview");
		mapLink.setStylePrimaryName("buttonview");
		
		navbar.add(brand);
		navbar.add(listLink);
		navbar.add(mapLink);
		
		Button contactLink = new Button("Contact Us");
		contactLink.setStylePrimaryName("buttonview");
		contactLink.setHref("mailto:dealmedev@gmail.com");
		Nav nav = new Nav();
		nav.setAlignment(Alignment.RIGHT);
		nav.add(contactLink);
		
		navbar.add(nav);
		
		verti = new VerticalPanel();
		verti.setWidth("100%");
		verti.add(navbar);
		verti.setHeight("40px");
		
		contactLink.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent event) {
	        	  /*CONTACT US BUTTON EMAIL CODE GOES HERE **/

	             
	          }
	      });
		
		mapLink.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent event) {
	              if (!mapView) {
	            	  setMapView();
	            	  mapView = true;
	            	  listView = false;
	          		eventBus.fireEvent(new DealsEvent());

	              } 
	          }
	      });
		
		listLink.addClickHandler(new ClickHandler() {
	          public void onClick(ClickEvent event) {
	              if (!listView) {
	            	  setListView();
	            	  listView = true;
	            	  mapView = false;
	          		eventBus.fireEvent(new DealsEvent());

	              } 
	          }
	      });
		
		/**POPUPPANEL FOR LARGEMAP FILTER/LOCATION**/
		newMapWidget = new GoogleMapWidget(dealService, eventBus, true);
		newMapWidget.setStylePrimaryName("mapStyle");
		popup = new PopupPanel();
		vert = new FlowPanel();
		popup.setHeight("300px");
		popup.add(vert);
		vert.add(locationWidget);
		vert.add(filterWidget);
		int left = (20);
		int top = (Window.getClientHeight() - 20);
		popup.setPopupPosition(left, top);

		w = MyUiBinder.INSTANCE.createAndBindUi(this);		

		listVert = new FlowPanel();
		listVert.setWidth("100%");
		listVert.add(verti);
		listVert.add(w);
		
		mapVert = new FlowPanel();
		mapVert.setWidth("100%");
		w.setHeight("100%");
		RootLayoutPanel.get().add(listVert);
		filterPanel.setVisible(false);
		locationPanel.setVisible(false);
	}
	
	private void setListView()
	{
		RootLayoutPanel.get().clear();
		listVert.clear();
		listVert.add(verti);
		listVert.add(w);
		RootLayoutPanel.get().add(listVert);
		filterPanel.setVisible(false);
		locationPanel.setVisible(false);
		popup.setVisible(false);
	}
	
	private void setMapView()
	{
		RootLayoutPanel.get().clear();
		mapVert.clear();
		mapVert.add(verti);
		mapVert.add(newMapWidget);
		RootLayoutPanel.get().add(mapVert);
		
		vert.clear();
		vert.add(locationWidget);
		vert.add(filterWidget);
		popup.setStylePrimaryName("popupPlacement");
		popup.show();
		
	}
	
	private void getUserLocation() {
		Geolocation geo;
		if((geo = Geolocation.getIfSupported()) != null) {
			geo.getCurrentPosition(
			new Callback<Position, PositionError>() {
					@Override
					public void onFailure(PositionError reason) {
						Window.alert(reason.toString());
					}

					@Override
					public void onSuccess(Position result) {
						Coordinates userCoor = result.getCoordinates();
						LatLngCoor userLatLng = new LatLngCoor(userCoor.getLatitude(), userCoor.getLongitude());
						geocodingService.convertLatLngToAddress(userLatLng, new AsyncCallback<Location>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Failed to geocode!");
							}

							@Override
							public void onSuccess(final Location result) {
								// Window.alert(result.getAddress() + "\n" + result.getCity() + ", " + result.getState() + " " + result.getZipCode());
								Deals.getInstance().setLocation(result);
								Deals.getInstance().setUserLocation(result);
								Integer numDealsToLoad = 7;
								Deals deals = Deals.getInstance();
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
												deals.setDeals(result);
												deals.setOffset(deals.getOffset() + result.size());
												deals.setLoadsSinceLastReset(new Integer(0));
												eventBus.fireEvent(new DealsEvent());
											}
								});
							}
						});
					}
			});
		}
	}
}
