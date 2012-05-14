package me.deal.client.view.menubar;

import com.github.gwtbootstrap.client.ui.Badge;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Divider;
import com.github.gwtbootstrap.client.ui.Dropdown;
import com.github.gwtbootstrap.client.ui.NavHeader;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavText;
import com.github.gwtbootstrap.client.ui.NavWidget;
import com.github.gwtbootstrap.client.ui.SplitDropdownButton;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ModifiedDropdown extends Dropdown {

	public ModifiedDropdown() {
		// TODO Auto-generated constructor stub
	}

	public ModifiedDropdown(String caption) {
		super(caption);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean canBeAdded(Widget widget) {
		return ((widget instanceof NavLink) || (widget instanceof NavText)
				|| (widget instanceof NavHeader) || (widget instanceof Divider)
				|| (widget instanceof CheckBox) || (widget instanceof Button)
				|| (widget instanceof Badge) || (widget instanceof NavWidget));
	}

}
