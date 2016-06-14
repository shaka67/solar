/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 * Extends BusMember by actually processing a ActionData supplied on a logical bus
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface BusSubscriber extends BusMember
{
  public <T> void receive(ActionData<T> data) throws ActionException;
}
