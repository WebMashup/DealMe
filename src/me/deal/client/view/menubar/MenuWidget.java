package me.deal.client.view.menubar;

import java.util.ArrayList;

import me.deal.client.events.DealsEvent;
import me.deal.client.events.DealsEventHandler;
import me.deal.client.model.Deals;
import me.deal.client.model.DealsLocation;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.shared.Category;
import me.deal.shared.Deal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.github.gwtbootstrap.*;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Form;
import com.github.gwtbootstrap.client.ui.NavWidget;


public class MenuWidget extends Composite {

	private static MenuWidgetUiBinder uiBinder = GWT
			.create(MenuWidgetUiBinder.class);
	
	interface MenuWidgetUiBinder extends UiBinder<Widget, MenuWidget> {
	}

	private final DealServiceAsync dealService;
	private final HandlerManager eventBus;

	private final Integer DEFAULT_NUM_DEALS = 20;
		
	public @UiConstructor MenuWidget(final DealServiceAsync dealService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dealService = dealService;
		this.eventBus = eventBus;
		initialize();
	}

	
	private void initialize() {
		
		 eventBus.addHandler(DealsEvent.TYPE,
	        new DealsEventHandler() {

			@Override
			public void onDeals(DealsEvent event) {
				// TODO Auto-generated method stub
				
			}
	        });
	        
	}
	
	private void setDealFilters(ArrayList<Category> FilterList){
		
        dealService.getYipitDeals(DealsLocation.getInstance().getDealsLocation().getLatLng(),
				DealsLocation.getInstance().DEFAULT_RADIUS, DEFAULT_NUM_DEALS, FilterList, 
				new AsyncCallback<ArrayList<Deal>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to load deals.");
			}

			@Override
			public void onSuccess(ArrayList<Deal> result) {
				Deals.getInstance().setDeals(result);
				eventBus.fireEvent(new DealsEvent());
			}
        });
	}
}
