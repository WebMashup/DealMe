package me.deal.client.view.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DisplayWidget extends Composite {

	private static DisplayWidgetUiBinder uiBinder = GWT
			.create(DisplayWidgetUiBinder.class);

	interface DisplayWidgetUiBinder extends UiBinder<Widget, DisplayWidget> {
	}

	public DisplayWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/* @UiField
	GoogleMapWidget mapWidget; */
	@UiField
	ListItemWidget listWidget;

}
