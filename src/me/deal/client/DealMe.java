package me.deal.client;

import java.util.ArrayList;

import me.deal.client.servlets.DealService;
import me.deal.client.servlets.DealServiceAsync;
import me.deal.client.view.header.HeaderWidget;
import me.deal.client.view.main.DisplayWidget;
import me.deal.client.view.menubar.MenuWidget;
import me.deal.shared.Category;
import me.deal.shared.Deal;
import me.deal.shared.LatLng;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DealMe extends Composite implements EntryPoint {

	private final DealServiceAsync dealService = GWT
			.create(DealService.class);
	
	@UiField
	HeaderWidget headerWidget;
	@UiField
	MenuWidget menuWidget;
	@UiField
	DisplayWidget displayWidget;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		ArrayList<Category> tags = new ArrayList<Category>();
		tags.add(Category.RESTAURANTS);
		dealService.getYipitDeals(new LatLng(34.067297,-118.453176), new Double(30.0), new Integer(100),
				tags, new AsyncCallback<ArrayList<Deal>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("ERRORROROORR");
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ArrayList<Deal> result) {
				String print = "";
				for(Deal deal : result) {
					print += deal.getTitle() + "\n";
				}
				Window.alert(print);
			}
		});
		//RootPanel.get().add(new GoogleMapWidget());
	}
}
