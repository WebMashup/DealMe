package me.deal.client.view.menubar;

import java.util.ArrayList;

import me.deal.client.events.DealsEvent;
import me.deal.client.events.DealsEventHandler;
import me.deal.client.model.Deals;
import me.deal.client.model.DealsLocation;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.shared.Category;
import me.deal.shared.Deal;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.NavWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FilterWidget extends Composite {

	private static FilterWidgetUiBinder uiBinder = GWT
			.create(FilterWidgetUiBinder.class);

	interface FilterWidgetUiBinder extends UiBinder<Widget, FilterWidget> {
	}
	
	private final DealServiceAsync dealService;
	private final HandlerManager eventBus;
	
	private final Integer DEFAULT_NUM_DEALS = 3;

	public @UiConstructor FilterWidget(final DealServiceAsync dealService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dealService = dealService;
		this.eventBus = eventBus;
		bind();
		initialize();
	}

	private void bind(){
		SubmitButton.addClickHandler(new ClickHandler() {
			

			public void onClick(ClickEvent event) {
				checkDealFilters();
			}
		});
		
		DiningCheckBox.addClickHandler(new ClickHandler() {
	

			public void onClick(ClickEvent event) {
				if(DiningCheckBox.getValue())
					DiningCheckBox.setValue(false);
				else
					DiningCheckBox.setValue(true);
			}
		});
		
		NightlifeCheckBox.addClickHandler(new ClickHandler() {
			

			public void onClick(ClickEvent event) {
				if(NightlifeCheckBox.getValue())
					NightlifeCheckBox.setValue(false);
				else
					NightlifeCheckBox.setValue(true);
			}
		});
		
		HealthCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {		
			if(HealthCheckBox.getValue())
				HealthCheckBox.setValue(false);
			else
				HealthCheckBox.setValue(true);
			}
		});
		
		FitnessCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if(FitnessCheckBox.getValue())
					FitnessCheckBox.setValue(false);
				else
					FitnessCheckBox.setValue(true);
			}
		});
		
		RetailCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if(RetailCheckBox.getValue())
					RetailCheckBox.setValue(false);
				else
					RetailCheckBox.setValue(true);
				}
		});
		
		ServicesCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if(ServicesCheckBox.getValue())
					ServicesCheckBox.setValue(false);
				else
					ServicesCheckBox.setValue(true);
			}
		});
		
		ActivitiesCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if(ActivitiesCheckBox.getValue())
					ActivitiesCheckBox.setValue(false);
				else
					ActivitiesCheckBox.setValue(true);
			}
		});
		
		
		SpecialInterestCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if(SpecialInterestCheckBox.getValue())
					SpecialInterestCheckBox.setValue(false);
				else
					SpecialInterestCheckBox.setValue(true);
			}
		});
		
		AllCheckBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(AllCheckBox.getValue())
					AllCheckBox.setValue(false);
				else
					AllCheckBox.setValue(true);
			}
		});
	}
	
	private void checkDealFilters(){
		
		ArrayList<Category> FilterArrayList = new ArrayList<Category>();

		if (DiningCheckBox.getValue())
			FilterArrayList.add(Category.RESTAURANTS);
		
		if (NightlifeCheckBox.getValue())
			FilterArrayList.add(Category.BARSLOUNGES);
		
		if (HealthCheckBox.getValue())
		{
			FilterArrayList.add(Category.MASSAGE);
			FilterArrayList.add(Category.FACIALS);
			FilterArrayList.add(Category.NAILCARE);
			FilterArrayList.add(Category.TANNING);
			FilterArrayList.add(Category.HAIRSALONS);
			FilterArrayList.add(Category.WAXING);
			FilterArrayList.add(Category.SPA);
			FilterArrayList.add(Category.TEETHWHITENING);
			FilterArrayList.add(Category.VISIONEYECARE);
			FilterArrayList.add(Category.MAKEUP);

		}
		
		if(FitnessCheckBox.getValue()){
			FilterArrayList.add(Category.PILATES);
			FilterArrayList.add(Category.YOGA);
			FilterArrayList.add(Category.GYM);
			FilterArrayList.add(Category.BOOTCAMP);
		}
		
		if(RetailCheckBox.getValue()){
			FilterArrayList.add(Category.MENSCLOTHING);
			FilterArrayList.add(Category.WOMENSCLOTHING);
			FilterArrayList.add(Category.GROCERIES);
			FilterArrayList.add(Category.DESSERT);
		}
		
		if(AllCheckBox.getValue()){
			FilterArrayList.clear();
		}
		
		setDealFilters(FilterArrayList);
	}
	@UiField
	Button SubmitButton;
	
	@UiField
	CheckBox DiningCheckBox;
	
	@UiField
	CheckBox NightlifeCheckBox;

	@UiField
	CheckBox HealthCheckBox;
	
	@UiField
	CheckBox FitnessCheckBox;
	
	@UiField
	CheckBox RetailCheckBox;
	
	@UiField
	CheckBox ServicesCheckBox;
	
	@UiField
	CheckBox ActivitiesCheckBox;
		
	@UiField
	CheckBox SpecialInterestCheckBox;
			
	@UiField
	CheckBox AllCheckBox;
	
	private void initialize() {
		SubmitButton = new Button("Submit");
		
		DiningCheckBox = new CheckBox("Dining");
		NightlifeCheckBox = new CheckBox("Nightlife");
		HealthCheckBox = new CheckBox("Health & Beauty");
		FitnessCheckBox = new CheckBox("Fitness");
		RetailCheckBox = new CheckBox("Retail");
		ServicesCheckBox = new CheckBox("Services");
		ActivitiesCheckBox = new CheckBox("Activities & Events");
		SpecialInterestCheckBox = new CheckBox ("Special Interests");
		AllCheckBox = new CheckBox ("Select All");
				
		
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
