package me.deal.client.events;

import com.google.gwt.event.shared.GwtEvent;


public class UserLocationEvent extends GwtEvent<UserLocationEventHandler> {
  public static Type<UserLocationEventHandler> TYPE = new Type<UserLocationEventHandler>();
  
  @Override
  public Type<UserLocationEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(UserLocationEventHandler handler) {
    handler.onUserLocation(this);
  }
}
