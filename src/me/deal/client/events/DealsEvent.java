package me.deal.client.events;

import com.google.gwt.event.shared.GwtEvent;


public class DealsEvent extends GwtEvent<DealsEventHandler> {
  public static Type<DealsEventHandler> TYPE = new Type<DealsEventHandler>();
  
  @Override
  public Type<DealsEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(DealsEventHandler handler) {
    handler.onDeals(this);
  }
}
