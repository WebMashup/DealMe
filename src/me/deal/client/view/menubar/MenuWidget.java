package me.deal.client.view.menubar;

import me.deal.client.servlets.DealServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MenuWidget extends Composite {

	private static MenuWidgetUiBinder uiBinder = GWT
			.create(MenuWidgetUiBinder.class);
	
	interface MenuWidgetUiBinder extends UiBinder<Widget, MenuWidget> {
	}

	private final DealServiceAsync dealService;
	private final HandlerManager eventBus;
		
	public @UiConstructor MenuWidget(final DealServiceAsync dealService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dealService = dealService;
		this.eventBus = eventBus;
	}
}
