package me.deal.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface UserLocationEventHandler extends EventHandler {
  void onUserLocation(UserLocationEvent event);
}
