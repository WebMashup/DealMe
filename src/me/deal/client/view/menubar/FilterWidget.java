package me.deal.client.view.menubar;

import me.deal.client.servlets.DealServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class FilterWidget extends Composite {

	private static FilterWidgetUiBinder uiBinder = GWT
			.create(FilterWidgetUiBinder.class);

	interface FilterWidgetUiBinder extends UiBinder<Widget, FilterWidget> {
	}
	
	private final DealServiceAsync dealService;
	private final HandlerManager eventBus;

	public FilterWidget(final DealServiceAsync dealService,
			final HandlerManager eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dealService = dealService;
		this.eventBus = eventBus;
		initialize();
	}

	private void initialize() {
		
	}
}
