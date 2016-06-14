/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 * A BusRegistry provides the underlying connection mechanism between BusMembers, Publishers, and Subscribers
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface BusRegistry
{
    public void bind(BusMember member, String busname);

    public void unbind(BusMember member, String busname);

    public <T> void send(String bus, ActionData<T> data);

    public <T> void send(String bus, ActionData<T> data, boolean notifyConcurrent);

    public BusPublisherProxy createPublisherProxy();

    public BusSubscriberProxy createSubscriberProxy(BusListener listener);

    public BusSignalManager getSignalManager();

    public static final String BUS_UNDEFINED = "BUS_UNDEFINED";
}
