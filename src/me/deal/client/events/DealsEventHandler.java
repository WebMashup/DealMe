package me.deal.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface DealsEventHandler extends EventHandler {
  void onDeals(DealsEvent event);
}

