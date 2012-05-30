package me.deal.client.view.header;

import me.deal.client.servlets.DealService;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.client.servlets.DirectionsService;
import me.deal.client.servlets.DirectionsServiceAsync;
import me.deal.client.servlets.GeocodingService;
import me.deal.client.servlets.GeocodingServiceAsync;
import me.deal.client.view.main.GoogleMapWidget;
import me.deal.client.view.menubar.FilterWidget;
import me.deal.client.view.menubar.LocationWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HeaderWidget extends Composite {

	private static HeaderWidgetUiBinder uiBinder = GWT
			.create(HeaderWidgetUiBinder.class);

	interface HeaderWidgetUiBinder extends UiBinder<Widget, HeaderWidget> {
	}
	
	private final DealServiceAsync dealService;
	private final DirectionsServiceAsync directionsService;
	private final HandlerManager eventBus;
	
	@UiField 
	GoogleMapWidget googleMapWidget;
	
	LocationWidget locationWidget;
	FilterWidget filterWidget;

	public HeaderWidget(final DealServiceAsync dealService,
			final DirectionsServiceAsync directionsService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dealService = dealService;
		this.directionsService = directionsService;
		this.eventBus = eventBus;
		initialize();
	}
	
	@UiFactory GoogleMapWidget makeGoogleMapWidget() {
		return new GoogleMapWidget(dealService, eventBus, true);
	}
	
	private void initialize(){
//		googleMapWidget = new GoogleMapWidget(dealService, eventBus, true);
		googleMapWidget = makeGoogleMapWidget();
		
		PopupPanel popup = new PopupPanel();
		VerticalPanel vert = new VerticalPanel();
		popup.add(vert);
		vert.add(locationWidget);
		vert.add(filterWidget);
		int left = (20);
		int top = (Window.getClientHeight() - 20);
		
		popup.setPopupPosition(left, top);
		popup.show();
	}
}
