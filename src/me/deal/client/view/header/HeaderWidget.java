package me.deal.client.view.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class HeaderWidget extends Composite {

	private static HeaderWidgetUiBinder uiBinder = GWT
			.create(HeaderWidgetUiBinder.class);

	interface HeaderWidgetUiBinder extends UiBinder<Widget, HeaderWidget> {
	}

	public HeaderWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
