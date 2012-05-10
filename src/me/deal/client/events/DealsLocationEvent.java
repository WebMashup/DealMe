package me.deal.client.events;

import com.google.gwt.event.shared.GwtEvent;


public class DealsLocationEvent extends GwtEvent<DealsLocationEventHandler> {
  public static Type<DealsLocationEventHandler> TYPE = new Type<DealsLocationEventHandler>();
  
  @Override
  public Type<DealsLocationEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(DealsLocationEventHandler handler) {
    handler.onDealsLocation(this);
  }
}
