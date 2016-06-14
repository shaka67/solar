/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 * Proxy class that can be used to subscribe to a Bus without having to implement the full BusMember
 * interface
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class BusSubscriberProxy extends AbstractBusMember implements BusSubscriber {
    
    public BusSubscriberProxy(BusListener listener, BusRegistry registry) {
        super(listener, registry);
    }

    @Override
    public <T> void receive(ActionData<T> data) throws ActionException {
        final BusListener listener = getBusListener();
        if (listener != null) {
            listener.receive(data);
        }
    }

}
