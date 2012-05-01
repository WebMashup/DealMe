package me.deal.client.view.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListWidget extends Composite {

	private static ListWidgetUiBinder uiBinder = GWT
			.create(ListWidgetUiBinder.class);

	interface ListWidgetUiBinder extends UiBinder<Widget, ListWidget> {
	}
	
	public ListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		initialize();
	}

	@UiField
	VerticalPanel listItemContainer;
	
	private void initialize() {
		/*
		 * TODO: Add code to observe the DealsData model and automatically
		 * update the items in the listItemContainer dynamically to reflect
		 * changes in the model.
		 */
	}
}
