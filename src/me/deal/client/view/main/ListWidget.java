package me.deal.client.view.main;

import me.deal.client.servlets.DirectionsServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListWidget extends Composite {

	private static ListWidgetUiBinder uiBinder = GWT
			.create(ListWidgetUiBinder.class);

	interface ListWidgetUiBinder extends UiBinder<Widget, ListWidget> {
	}
	
	private final DirectionsServiceAsync directionsService;
	private final HandlerManager eventBus;
	
	public ListWidget(final DirectionsServiceAsync directionsService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.directionsService = directionsService;
		this.eventBus = eventBus;
		initialize();
	}

	@UiField
	VerticalPanel listItemContainer;
	
	private void initialize() {
		
		/*
		 * TODO: Add code to observe the DealsData model and automatically
		 * update the items in the listItemContainer dynamically to reflect
		 * changes in the model.
		 * 
		 * eventBus.addHandler(DealsEvent.TYPE,
        	new DealsEventHandler() {
          	public void onDeal(DealsEvent event) {
            //Insert Code Here
          }
        });
		 */
	}
}
