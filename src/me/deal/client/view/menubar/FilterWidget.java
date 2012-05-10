package me.deal.client.view.menubar;

import me.deal.client.servlets.DealServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FilterWidget extends Composite {

	private static FilterWidgetUiBinder uiBinder = GWT
			.create(FilterWidgetUiBinder.class);

	interface FilterWidgetUiBinder extends UiBinder<Widget, FilterWidget> {
	}
	
	private final DealServiceAsync dealService;
	private final HandlerManager eventBus;

	public FilterWidget(final DealServiceAsync dealService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dealService = dealService;
		this.eventBus = eventBus;
		initialize();
	}

	private void initialize() {
		/*
		 * When you modify the Deals singleton be sure to fire this event
		 * to notify other people the deals singleton has been modified.
		 * 
		 * eventBus.fireEvent(new DealsEvent());
		 * 
		 * eventBus.addHandler(DealsEvent.TYPE,
	        new DealsEventHandler() {
	          public void onDeal(DealsEvent event) {
	            //Insert Code Here
	          }
	        });
	        
	        dealService.getYipitDeals(<coor>, <radius>, <limit>, <tags>, 
					new AsyncCallback<ArrayList<Deal>>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Failed to load deals.")
						}
	
						@Override
						public void onSuccess(ArrayList<Deal> result) {
							
						}
			});
		 */
	}
}
