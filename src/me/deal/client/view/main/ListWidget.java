package me.deal.client.view.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import me.deal.client.events.DealsEvent;
import me.deal.client.events.DealsEventHandler;
import me.deal.client.model.Deals;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.client.servlets.DirectionsServiceAsync;
import me.deal.shared.Deal;
import me.deal.shared.LatLngCoor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class ListWidget extends Composite {

    private static ListWidgetUiBinder uiBinder = GWT
            .create(ListWidgetUiBinder.class);

    interface ListWidgetUiBinder extends UiBinder<Widget, ListWidget> {
    }

    @UiField
    Image loadingSpinnerImage;
    @UiField
    VerticalPanel listItemContainer;
    
    private final DealServiceAsync dealService;
    private final DirectionsServiceAsync directionsService;
    private final HandlerManager eventBus;
    
    private final ScrollPanel mainScrollPanel;
    private Boolean scrollLock = false;
    private Integer dealIndex;
    private boolean loadingMoreDeals = false;
    private Character currLetter = 'A';
    
    public @UiConstructor ListWidget(final ScrollPanel mainScrollPanel,
    		final DealServiceAsync dealService,
            final DirectionsServiceAsync directionsService,
            final HandlerManager eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        this.dealService = dealService;
        this.directionsService = directionsService;
        this.eventBus = eventBus;
        this.mainScrollPanel = mainScrollPanel;
    	
        initialize();
    }
    
    private void setIDUrl(ArrayList<Deal> deals, Integer currentDealIndex, Integer direction)
    {
    	Deal current = deals.get(currentDealIndex);
        LatLngCoor test = current.getBusinessAddress().getLatLng();
        for(int i = 0; i < currentDealIndex; i++)
        {
            double lat1 = test.getLatitude();
            double lat2 = deals.get(i).getBusinessAddress().getLatLng().getLatitude();
            double lon1 = test.getLongitude();
            double lon2 = deals.get(i).getBusinessAddress().getLatLng().getLongitude();
            
            if(lat1 == lat2 && lon1 == lon2) {
            	current.setIDUrl(deals.get(i).getIDUrl());
                return;
            }
        }
        current.setIDUrl("http://www.google.com/mapfiles/marker" + currLetter + ".png");
        if(direction == 0) {
        	currLetter = (char) ((currLetter == 'A') ? 'Z' : (currLetter - 1));
	    } else {
	        currLetter = (char) ((currLetter == 'Z') ? 'A' : (currLetter + 1));
	    }
    }
    
    private void initialize() {
        /*
         * TODO: Add code to observe the DealsData model and automatically
         * update the items in the listItemContainer dynamically to reflect
         * changes in the model.
         * 
         */
    	dealIndex = 0;
    	Window.enableScrolling(true);
    	loadingSpinnerImage.setVisible(true);
        listItemContainer.setVisible(false);
    	
    	Window.addWindowScrollHandler(new ScrollHandler() {
			@Override
			public void onWindowScroll(ScrollEvent event) {
				
                if(!scrollLock) {
                	int currPos = mainScrollPanel.getVerticalScrollPosition();
                    int maxPos = mainScrollPanel.getMaximumVerticalScrollPosition();
    				System.out.println("currPos = " + currPos);
    				System.out.println("maxPos = " + maxPos);
                    int numWidgets = listItemContainer.getWidgetCount();
                	System.out.println("dealIndex = " + dealIndex);
                	System.out.println("right = " + (dealIndex + numWidgets) + ", left = " + (Deals.getInstance().getDeals().size() - (3*Deals.getInstance().DEFAULT_NUM_DEALS/4)));
                   
                	if(!loadingMoreDeals && dealIndex + numWidgets >= Deals.getInstance().getDeals().size() - (3*Deals.getInstance().DEFAULT_NUM_DEALS/4)) {
                    	loadingMoreDeals = true;
                    	Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    		public void execute() {
                    			Deals deals = Deals.getInstance();
                    			dealService.getYipitDeals(deals.getUserLocation().getLatLng(),
                                		deals.getRadius(),
                                		deals.DEFAULT_NUM_DEALS * 2,
                                		deals.getOffset(),
                                		deals.getTags(), new AsyncCallback<ArrayList<Deal>> () {

        								@Override
        								public void onFailure(Throwable caught) {
        	                                  Window.alert("Failed to load deals.");
        	                                  scrollLock = false;
        								}

        								@Override
        								public void onSuccess(ArrayList<Deal> result) {
        									Deals deals = Deals.getInstance();
        	                                deals.setOffset(deals.getOffset() + result.size());
        	                                deals.addDeals(result);
        	                                loadingMoreDeals = false;
        	                                System.out.println("Got deals, offset = " + deals.getOffset() + result.size() + ", scroll lock = " + scrollLock);
        								}
                    			});
                			}
                		});
                    }
                	
                    if(currPos >= maxPos && dealIndex + numWidgets < Deals.getInstance().getDeals().size()) {
                    	
                    	scrollLock = true;
                    	Deal currDeal = null;
	                    try {
	                    	currDeal = Deals.getInstance().getDeals().get(dealIndex + numWidgets);
	                    } catch(IndexOutOfBoundsException e) {
	                    	System.out.println(Deals.getInstance().getDeals().size());
	                    	return;
	                    }
                    	ListItemWidget currLi = (ListItemWidget)(listItemContainer.getWidget(0));
                    	currLi.setDeal(currDeal);
                    	listItemContainer.remove(0);
                    	listItemContainer.add(currLi);
                    	if(currDeal.getIDUrl().equals(""))
                    		setIDUrl(Deals.getInstance().getDeals(), dealIndex + numWidgets, 1);
                    	currLi.setIcon(currDeal.getIDUrl());
                    	System.out.println("ID URL for Deal " + dealIndex + " = " + currDeal.getIDUrl());
                    	dealIndex++;
                    	scrollLock = false;
                    }
                    
                    else if(currPos <= 0 && dealIndex != 0) {
                    	scrollLock = true;
                    	dealIndex--;
                    	Deal currDeal = Deals.getInstance().getDeals().get(dealIndex);
                    	ListItemWidget currLi = (ListItemWidget)(listItemContainer.getWidget(numWidgets-1));
                    	currLi.setDeal(currDeal);
                    	currLi.setIcon(currDeal.getIDUrl());
                    	listItemContainer.remove(numWidgets-1);
                    	listItemContainer.insert(currLi, 0);
                    	scrollLock = false;
                    }
                }
            }
        });
        
        
        eventBus.addHandler(DealsEvent.TYPE,
                new DealsEventHandler() {
                @Override
                public void onDeals(DealsEvent event) {
                	System.out.println("dealIndex " + dealIndex);
                	System.out.println("reset = " + Deals.getInstance().isReset());
                    if(Deals.getInstance().isReset()) {
                    	
                        loadingSpinnerImage.setVisible(true);
                        listItemContainer.setVisible(false);
                        
                        Deals.getInstance().setDuplicates(0);
                        final ArrayList<Deal> deals = Deals.getInstance().getDeals();
                    	dealIndex = 0;
                    	
                    	// TopPanel
                    	Integer numWidgets = listItemContainer.getWidgetCount();
                    	
                    	for(Integer i = 0; i < numWidgets; i++) {
                    		Deal currDeal = deals.get(i);
                    		ListItemWidget currListItemWidget  = (ListItemWidget)listItemContainer.getWidget(i);
                        	currListItemWidget.setDeal(currDeal);
                        	setIDUrl(deals, i, 1);
                        	currListItemWidget.setIcon(currDeal.getIDUrl());
                        }
                        
                        listItemContainer.setVisible(true);
                        loadingSpinnerImage.setVisible(false);
                        scrollLock = false;
                        Deals.getInstance().acknowledgeReset();
                    }
                }
        });
    }
}