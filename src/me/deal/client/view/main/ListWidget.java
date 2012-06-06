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
    
    private Boolean scrollLock = false;
    private boolean mapView = false;
    private Integer dealIndex;
    private Integer listItemIndex;
    private boolean loadingMoreDeals = false;
    
    public void setMapSize(boolean mapView)
    {
        this.mapView = mapView;
    }
    
    public @UiConstructor ListWidget(final DealServiceAsync dealService,
            final DirectionsServiceAsync directionsService,
            final HandlerManager eventBus) {
        initWidget(uiBinder.createAndBindUi(this));
        this.dealService = dealService;
        this.directionsService = directionsService;
        this.eventBus = eventBus;
    	
        initialize();
    }
    /*
    public void setCurrDealIcon() {
    	ArrayList<Deal> deals = Deals.getInstance().getDeals();
    	Deal curr = deals.get(dealIndex).
    	for(int i = 0; i < dealIndex; i++) {
    		
    	}
    }
    
    private String getDuplicateURL(ArrayList<Deal> deals, Deal current, Integer dealIndex)
    {
        LatLngCoor test = current.getBusinessAddress().getLatLng();
        for(int i = 0; i < dealIndex; i++)
        {
            double lat1 = test.getLatitude();
            double lat2 = deals.get(i).getBusinessAddress().getLatLng().getLatitude();
            double lon1 = test.getLongitude();
            double lon2 = deals.get(i).getBusinessAddress().getLatLng().getLongitude();
            
            if(lat1 == lat2 && lon1 == lon2) {    
                return deals.get(i).getIDUrl();
            }
        }
        return "";

    }
    */
    private void initialize() {
        /*
         * TODO: Add code to observe the DealsData model and automatically
         * update the items in the listItemContainer dynamically to reflect
         * changes in the model.
         * 
         */
    	dealIndex = 0;
    	listItemIndex = 0;
    	Window.enableScrolling(true);
    	loadingSpinnerImage.setVisible(true);
        listItemContainer.setVisible(false);
    	
    	Window.addWindowScrollHandler(new ScrollHandler() {
			@Override
			public void onWindowScroll(ScrollEvent event) {

                int currPos = Window.getScrollTop();
				System.out.println(currPos);
                if(!scrollLock) {
                    int maxPos = Window.getClientHeight();
                   // int currPos = Window.getScrollTop();
                    int numWidgets = listItemContainer.getWidgetCount();
                	System.out.println("dealIndex = " + dealIndex);
                	System.out.println("right = " + (dealIndex + numWidgets) + ", left = " + (Deals.getInstance().getDeals().size() - (Deals.getInstance().DEFAULT_NUM_DEALS/2)));
                    if(!loadingMoreDeals && dealIndex + numWidgets >= Deals.getInstance().getDeals().size() - (Deals.getInstance().DEFAULT_NUM_DEALS/2)) {
                    	loadingMoreDeals = true;
                    	Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    		public void execute() {
                    			Deals deals = Deals.getInstance();
                    			dealService.getYipitDeals(deals.getUserLocation().getLatLng(),
                                		deals.getRadius(),
                                		deals.DEFAULT_NUM_DEALS,
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
        								}
                    			});
                			}
                		});
                    }
                    if(currPos > maxPos - 1000) {
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
                    	dealIndex++;
                    	scrollLock = false;
                    	/*
                    	scrollLock = true;
                        Deals deals = Deals.getInstance();
                        dealService.getYipitDeals(deals.getUserLocation().getLatLng(),
                        		deals.getRadius(),
                        		deals.DEFAULT_NUM_DEALS/2,
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
	                                
	                                ArrayList<ListItemWidget> tempRemovedWidgets = new ArrayList<ListItemWidget>();

	                                for(int i = 0; i < result.size(); i++) {
	                                	tempRemovedWidgets.add((ListItemWidget)listItemContainer.getWidget(0));
	                                	tempRemovedWidgets.remove(i);
	                                }
	                                
	                                int finalScrollPos = Window.getScrollTop();
	                                for(int i = 0; i < result.size(); i++) {
	                                	System.out.println("remove Widget 0 and adding it to listItemContainer");
	                                	tempRemovedWidgets.get(i).setDeal(result.get(i));
	                                	listItemContainer.add(tempRemovedWidgets.get(i));
	                                }
	                                Window.scrollTo(0, finalScrollPos);
	                                dealIndex += result.size();
	                                scrollLock = false;
	                                eventBus.fireEvent(new DealsEvent());
	                                System.out.println("end dealIndex2 = " + dealIndex);
								}
                        }); */
                    } else if(currPos < 1000 && dealIndex != 0) {
                    	scrollLock = true;
                    	dealIndex--;
                    	Deal currDeal = Deals.getInstance().getDeals().get(dealIndex);
                    	ListItemWidget currLi = (ListItemWidget)(listItemContainer.getWidget(numWidgets-1));
                    	currLi.setDeal(currDeal);
                    	listItemContainer.remove(numWidgets-1);
                    	listItemContainer.insert(currLi, 0);
                    	scrollLock = false;
                    	/*
                    	ArrayList<Deal> deals = Deals.getInstance().getDeals().
                    	Integer numToMove = Deals.getInstance().DEFAULT_NUM_DEALS/2;
                    	ListItemWidget tempRemovedWidget;
                        
                        System.out.println("init = " + (dealIndex - 1)  + " end = " + (dealIndex - numToMove));
                        for(int i = dealIndex - 1; i >= dealIndex - numToMove && i >= 0; i--) {
                        	tempRemovedWidget = (ListItemWidget)(listItemContainer.getWidget(listItemContainer.getWidgetCount()-1));
                        	listItemContainer.remove(listItemContainer.getWidgetCount()-1);
                        	tempRemovedWidget.setDeal(deals.get(i));
                        	listItemContainer.insert(tempRemovedWidget, 0);
                        }

                    	dealIndex = (dealIndex - numToMove < 0) ? 0 : (dealIndex - numToMove);
                        scrollLock = false;
                        System.out.println("end dealIndex = " + dealIndex);
                        */
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
	                    	
	                    	// setCurrDealIcon();
                        	// String url = getDuplicateURL(deals, currDeal, dealIndex);
                            /* if(url != "") {
                                Deals.getInstance().setDuplicates(Deals.getInstance().getDuplicates() + 1);
                               currDeal.setIDUrl(url);
                            } 
                            else {
                                currDeal.setIDUrl("http://www.google.com/mapfiles/marker" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(
                                		((dealIndex + i - Deals.getInstance().getDuplicates()) % 26),
                                		((dealIndex + i - Deals.getInstance().getDuplicates() + 1) % 26)) + ".png");
                            } */
                            //currListItemWidget.setIcon(currDeal.getIDUrl());
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