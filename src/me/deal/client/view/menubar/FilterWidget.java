package me.deal.client.view.menubar;

import java.util.ArrayList;

import me.deal.client.events.DealsEvent;
import me.deal.client.events.DealsEventHandler;
import me.deal.client.model.Deals;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.shared.Category;
import me.deal.shared.Deal;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
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

public class FilterWidget extends Composite {

    private static FilterWidgetUiBinder uiBinder = GWT
            .create(FilterWidgetUiBinder.class);

    interface FilterWidgetUiBinder extends UiBinder<Widget, FilterWidget> {
    }

    @UiField
    Button submitButton;
    
    @UiField
    CheckBox diningCheckBox;
    
    @UiField
    CheckBox nightlifeCheckBox;

    @UiField
    CheckBox healthCheckBox;
    
    @UiField
    CheckBox fitnessCheckBox;
    
    @UiField
    CheckBox retailCheckBox;
    
    @UiField
    CheckBox servicesCheckBox;
    
    @UiField
    CheckBox activitiesCheckBox;
        
    @UiField
    CheckBox specialInterestCheckBox;
            
    @UiField
    CheckBox allCheckBox;
    
    private final DealServiceAsync dealService;
    private final HandlerManager eventBus;
    private boolean mapView = false;
    
    public void setMapSize(boolean mapView)
    {
        this.mapView = mapView;
    }
    
    public @UiConstructor FilterWidget(final DealServiceAsync dealService,
            final HandlerManager eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        this.dealService = dealService;
        this.eventBus = eventBus;
        // initialize();
        // bind();
    }

    @UiHandler("submitButton")
    void handleClick(ClickEvent e) {
        checkDealFilters();
    }
    
    private void checkDealFilters(){
        
        ArrayList<Category> filterArrayList = new ArrayList<Category>();

        if (diningCheckBox.getValue())
            filterArrayList.add(Category.RESTAURANTS);
        
        if (nightlifeCheckBox.getValue())
            filterArrayList.add(Category.BARSLOUNGES);
        
        if (healthCheckBox.getValue())
        {
            filterArrayList.add(Category.MASSAGE);
            filterArrayList.add(Category.FACIALS);
            filterArrayList.add(Category.NAILCARE);
            filterArrayList.add(Category.TANNING);
            filterArrayList.add(Category.HAIRSALONS);
            filterArrayList.add(Category.WAXING);
            filterArrayList.add(Category.SPA);
            filterArrayList.add(Category.TEETHWHITENING);
            filterArrayList.add(Category.VISIONEYECARE);
            filterArrayList.add(Category.MAKEUP);

        }
        
        if(fitnessCheckBox.getValue()){
            filterArrayList.add(Category.PILATES);
            filterArrayList.add(Category.YOGA);
            filterArrayList.add(Category.GYM);
            filterArrayList.add(Category.BOOTCAMP);
        }
        
        if(retailCheckBox.getValue()){
            filterArrayList.add(Category.MENSCLOTHING);
            filterArrayList.add(Category.WOMENSCLOTHING);
            filterArrayList.add(Category.GROCERIES);
            filterArrayList.add(Category.DESSERT);
        }
        
        if(allCheckBox.getValue()){
            filterArrayList.clear();
        }
        
        setDealFilters(filterArrayList);
    }
    
    private void setDealFilters(ArrayList<Category> filterList){
        
        Deals deals = Deals.getInstance();
        deals.setTags(filterList);
        deals.setOffset(0);
        Integer numDealsToLoad = 7;
        if(mapView)
            numDealsToLoad = 20;
        
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
                deals.setLoadsSinceLastReset(new Integer(0));
                deals.setOffset(result.size());
                eventBus.fireEvent(new DealsEvent());
            }
        });
    }
    
}