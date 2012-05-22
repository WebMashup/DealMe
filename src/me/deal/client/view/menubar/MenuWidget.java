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

	private static final Category RESTAURANTS = null;
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
		bind();
		initialize();
	}

	//TODO: bind handlers for onhide filters ->
	//TODO: stop click propagation for dropdown
	private void bind(){
		SubmitButton.addClickHandler(new ClickHandler() {
			

			public void onClick(ClickEvent event) {
				checkDealFilters();
			}
		});
		
		FilterForm.addClickHandler(new ClickHandler() {
			

			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
		});
		DiningCheckBox.addClickHandler(new ClickHandler() {
	

			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
		});
		
		NightlifeCheckBox.addClickHandler(new ClickHandler() {
			

			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
		});
		
		HealthCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
		});
		
		FitnessCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
		});
		
		RetailCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
		});
		
		ServicesCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
		});
		
		ActivitiesCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
		});
		
		
		SpecialInterestCheckBox.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
		});
		
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
	ModifiedDropdown FilterDropdown;
	
	@UiField
	NavWidget FilterForm;
	
	
	private void initialize() {
		SubmitButton = new Button("Submit");
		
		FilterForm = new NavWidget();
		DiningCheckBox = new CheckBox("Dining");
		NightlifeCheckBox = new CheckBox("Nightlife");
		HealthCheckBox = new CheckBox("Health & Beauty");
		FitnessCheckBox = new CheckBox("Fitness");
		RetailCheckBox = new CheckBox("Retail");
		ServicesCheckBox = new CheckBox("Services");
		ActivitiesCheckBox = new CheckBox("Activities & Events");
		SpecialInterestCheckBox = new CheckBox ("Special Interests");
		
		FilterDropdown = new ModifiedDropdown("Filters");
		
		
		 eventBus.addHandler(DealsEvent.TYPE,
	        new DealsEventHandler() {

			@Override
			public void onDeals(DealsEvent event) {
				// TODO Auto-generated method stub
				
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
		
		setDealFilters(FilterArrayList);
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
