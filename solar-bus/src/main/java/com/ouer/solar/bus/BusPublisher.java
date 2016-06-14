/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 * A BusPublisher sends information on a bus without have to be a member
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface BusPublisher
{
    public <T> void send(String busName, ActionData<T> data);
}
