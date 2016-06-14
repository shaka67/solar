package com.ouer.solar.bus;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * This implementation of Registry notifies listners in the order that they were added and in the same thread of
 * execution.  This implementation is ideal for the Servlets because each request on the bus will happen in an
 * independent thread of execution.  Further more the only listeners on the bus are Action classes which have to be
 * written to be thread safe.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DefaultBusRegistry implements BusRegistry {

    private final Map<String, BusDispatcher> busDispatchers = Maps.newHashMap();
    private final BusSignalManager signalManager;
    
    public DefaultBusRegistry() {
        signalManager = new BusSignalManager(this);
    }

    @Override
    public  void bind(BusMember member, String busname) {
        BusDispatcher dispatcher;

        if (busDispatchers.get(busname) == null) {
            dispatcher = new BusDispatcher();
            busDispatchers.put(busname, dispatcher);
        } else {
            dispatcher = busDispatchers.get(busname);
        }

        dispatcher.bind(member);
    }

    @Override
    public  void unbind(BusMember member, String busname) {
        final BusDispatcher dispatcher = busDispatchers.get(busname);

        if (dispatcher != null) {
            dispatcher.unbind(member);
        }
    }

    @Override
    public <T> void send(String bus, ActionData<T> data) {
        send(bus, data, true);
    }

    @Override
    public <T> void send(String bus, ActionData<T> data, boolean notifyConcurrent) {
        data = (data != null)? data : new ActionData<T>();
        data.setBus(bus);
        final BusDispatcher dispatcher = busDispatchers.get(bus);
        if (dispatcher != null) {
            dispatcher.push(data, notifyConcurrent);
        }

    }

    @Override
    public BusPublisherProxy createPublisherProxy() {
        return new BusPublisherProxy(this);
    }

    @Override
    public BusSubscriberProxy createSubscriberProxy(BusListener listener) {
        return new BusSubscriberProxy(listener, this);
    }

    @Override
    public BusSignalManager getSignalManager() {
        return signalManager;
    }

}
