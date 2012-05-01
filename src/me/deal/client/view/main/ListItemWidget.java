package me.deal.client.view.main;

import me.deal.shared.BusinessInfo;
import me.deal.shared.Deal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ListItemWidget extends Composite {

	private static ListItemWidgetUiBinder uiBinder = GWT
			.create(ListItemWidgetUiBinder.class);

	interface ListItemWidgetUiBinder extends UiBinder<Widget, ListItemWidget> {
	}
	
	private Deal deal;
	private BusinessInfo businessInfo;

	public ListItemWidget(final Deal deal, final BusinessInfo businessInfo) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.deal = deal;
		this.businessInfo = businessInfo;
		intialize();
	}

	@UiField
	Button formatMapButton;
	@UiField
	Image dealImage;
	@UiField
	Label dealTitle;
	
	@UiField
	Image avgRating;
	@UiField
	Anchor numReviews;
	@UiField
	Label businessName;
	@UiField
	Label addressLine1;
	@UiField
	Label addressLine2;
	@UiField
	Button getDirectionsButton;
	
	@UiField
	Label dealPrice;
	@UiField
	Label discountPercentage;
	
	@UiField
	Label numDaysLeft;
	@UiField
	Label dealSource;
	
	private void intialize() {
		/*
		 * TODO: Use the data from the deal and businessInfo variables to fill in the
		 * UI with the relevant data.  Add UiHandlers to handle clicks on buttons.
		 * For the formatMapButton you will have to use the eventBus to signal to
		 * the MapWidget the change.  Use the DirectionsService to get directions.
		 * After getting directions, dynamically add the directions to the bottom of
		 * the ListItem and add a close button so that the user can easily close
		 * get rid of the directions.
		 */
	}
}
