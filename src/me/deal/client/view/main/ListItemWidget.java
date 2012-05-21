package me.deal.client.view.main;

import java.util.Date;

import me.deal.shared.BusinessInfo;
import me.deal.shared.Deal;
import me.deal.shared.LatLngCoor;
import me.deal.shared.Location;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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
	final int MILLSECS_PER_DAY = 86400000;

	public ListItemWidget(final Deal deal, final BusinessInfo businessInfo) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.deal = deal;
		this.businessInfo = businessInfo;
		initialize();
	}

	//@UiField
	//Button formatMapButton;
	@UiField
	Image dealImage;
	@UiField
	Anchor dealTitle;
	@UiField
	Label dealSubtitle;
	
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
	//@UiField
	//Button getDirectionsButton;
	@UiField
	Anchor getDirectionsLink;
	
	@UiField
	Anchor facebookLink;
	@UiField
	Anchor twitterLink;
	@UiField
	Anchor googlePlusLink;
	
	@UiField
	Label dealPrice;
	@UiField
	Label discountPercentage;
	
	@UiField
	Label numDaysLeft;
	@UiField
	Label daysText;
	@UiField
	Label dealSource;
	@UiField
	Image avgYelpRating;
	
	
	@SuppressWarnings("deprecation")
	private void initialize() {
		/*
		 * TODO: Use the data from the deal and businessInfo variables to fill in the
		 * UI with the relevant data.  Add UiHandlers to handle clicks on buttons.
		 * For the formatMapButton you will have to use the eventBus to signal to
		 * the MapWidget the change.  Use the DirectionsService to get directions.
		 * After getting directions, dynamically add the directions to the bottom of
		 * the ListItem and add a close button so that the user can easily close
		 * get rid of the directions.
		 */
		
		
		
		String yelpRatingURL = null;
		
		if (deal.getDealBusinessInfo() != null)
		{
			BusinessInfo bizInfo = deal.getDealBusinessInfo();
			yelpRatingURL = bizInfo.getAvgRatingImageUrl();
			avgYelpRating.setUrl(bizInfo.getAvgRatingImageUrl());
			businessName.setText(bizInfo.getName());
			numReviews.setText(Integer.toString(bizInfo.getNumReviews())+ " Reviews");
			numReviews.setHref(bizInfo.getWebUrl());
			numReviews.setTarget("_blank");
		}
		
		//set image to be middle 200 x 300 of the full-size image
		String url = deal.getBigImageUrl();
		Location dealAddr = deal.getBusinessAddress();
		dealImage.setUrl(deal.getBigImageUrl());
		int centeredLeft = dealImage.getWidth() > 300 ? (dealImage.getWidth() - 300)/2 : 0;
		int centeredTop = dealImage.getHeight() > 200 ? (dealImage.getHeight() - 200)/2 : 0;		
		dealImage.setVisibleRect(centeredLeft, centeredTop, 300, 200);
		
		dealTitle.setText(deal.getSubtitle());
		dealTitle.setHref(deal.getYipitWebUrl());
		dealTitle.setTarget("_blank");
		dealSubtitle.setText(deal.getTitle());
		addressLine1.setText(deal.getBusinessAddress().getAddress());
		addressLine2.setText(dealAddr.getCity() + ", " + dealAddr.getState());
				
		dealPrice.setText(deal.getPrice().toString());
		discountPercentage.setText(deal.getDiscountPercentage() + "%");		
		
		//set the directions url
		//http://maps.google.com/?saddr=34.052222,-118.243611&daddr=37.322778,-122.031944
		LatLngCoor latlng = deal.getBusinessAddress().getLatLng();
		String directionsURL = "http://maps.google.com/?q="+latlng.getLatitude()+","+latlng.getLongitude();
		getDirectionsLink.setHref(directionsURL);
		getDirectionsLink.setText("Directions");
		
		//social media integration
		String yipitURL= deal.getYipitWebUrl();
		String facebookLinkURL="https://www.facebook.com/sharer.php?u="+yipitURL;
		String twitterLinkURL="http://twitter.com/share?count=horiztonal&url="+yipitURL+"&text="+deal.getTitle();
		String googlePlusLinkURL="https://plusone.google.com/_/+1/confirm?hl=en-US&url="+yipitURL;
		facebookLink.setHref(facebookLinkURL);
		Image facebookImage = new Image("images/facebook.png");
		facebookLink.getElement().appendChild(facebookImage.getElement());
		twitterLink.setHref(twitterLinkURL);
		Image twitterImage = new Image("images/twitter.png");
		twitterLink.getElement().appendChild(twitterImage.getElement());
		googlePlusLink.setHref(googlePlusLinkURL);
		Image googlePlusImage= new Image("images/google.png");
		googlePlusLink.getElement().appendChild(googlePlusImage.getElement());
				
		
		//parse date strings to determine how many days are left compared to current date
		Date today = new Date();
		String endDate = deal.getEndDate();
		Date endDay = new Date();
		endDay.setYear(Integer.parseInt(endDate.substring(0, 4))-1900);
		endDay.setMonth(Integer.parseInt(endDate.substring(5, 7))-1);
		endDay.setDate(Integer.parseInt(endDate.substring(8, 10)));
		long deltaDays = (endDay.getTime() - today.getTime()) / MILLSECS_PER_DAY;		
		numDaysLeft.setText(String.valueOf(deltaDays));
		//set days to plural if needed
		if (deltaDays > 1){
			daysText.setText("\tdays left on");
		}
		else {
			daysText.setText("\tday left on ");
		}
		//TODO: parse deal source from yipit
		dealSource.setText(deal.getDealSource());	
		
			
	}
}
