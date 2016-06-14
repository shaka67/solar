/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Simplified mechanism for message passing and notification which uses the bus framework under the covers.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class BusSignalManager {

    private final Map<BusSignalListener<?>, BusSubscriberProxy> listeners;
    private final BusPublisherProxy publisher;
    private final BusRegistry registry;
    
    public BusSignalManager(BusRegistry registry) {
        listeners = Maps.newHashMap();
        publisher = registry.createPublisherProxy();
        this.registry = registry;
    }

    @SuppressWarnings("rawtypes")
    public <T> void bind(Class<?> signalClass, BusSignalListener listener) {
        BusSubscriberProxy proxy = listeners.get(listener);
        if (proxy == null) {
            proxy = registry.createSubscriberProxy(new BusListenerProxy(listener));
            listeners.put(listener, proxy);
        }

        proxy.bind(signalClass.getName());
    }

    @SuppressWarnings("rawtypes")
    public void unbind(Class<?> signalClass, BusSignalListener listener) {
        final BusSubscriberProxy proxy = listeners.get(listener);
        if (proxy != null) {
            proxy.unbind(signalClass.getName());

            /** If we are not registered on any buses we can free some objects in memory **/
            if (proxy.getRegisteredBuses().isEmpty()) {
                listeners.remove(listener);
            }
        }
    }

    public <T> void signal(T signal) {
        final ActionData<T> data = new ActionData<T>();
        data.setPayload(signal);
        data.setBus(signal.getClass().getName());
        publisher.send(data, false);
    }

    public <T> void signal(T signal, boolean notifyConcurrent) {
        final ActionData<T> data = new ActionData<T>();
        data.setPayload(signal);
        data.setBus(signal.getClass().getName());
        publisher.send(data, notifyConcurrent);
    }

    @SuppressWarnings("rawtypes")
    public void bind(String bus, BusSignalListener listener) {
        BusSubscriberProxy proxy = listeners.get(listener);
        if (proxy == null) {
            proxy = registry.createSubscriberProxy(new BusListenerProxy(listener));
            listeners.put(listener, proxy);
        }

        proxy.bind(bus);
    }

    @SuppressWarnings("rawtypes")
    public void unbind(String bus, BusSignalListener listener) {
        final BusSubscriberProxy proxy = listeners.get(listener);
        if (proxy != null) {
            proxy.unbind(bus);

            // If we are not registered on any buses we can free some objects in memory
            if (proxy.getRegisteredBuses().isEmpty()) {
                listeners.remove(listener);
            }
        }
    }

    public <T> void signal(String bus, T signal) {
        final ActionData<T> data = new ActionData<T>();
        data.setPayload(signal);
        data.setBus(bus);
        publisher.send(data, false);
    }

    public <T> void signal(String bus, T signal, boolean notifyConcurrent) {
        final ActionData<T>  data = new ActionData<T>();
        data.setPayload(signal);
        data.setBus(bus);
        publisher.send(data, notifyConcurrent);
    }

    public void cleanup() {
    	listeners.clear();
    }

    private class BusListenerProxy implements BusListener {

        @SuppressWarnings("rawtypes")
        BusSignalListener listener;

        @SuppressWarnings("rawtypes")
        public BusListenerProxy(BusSignalListener listener) {
            this.listener = listener;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> void receive(ActionData<T> event) throws ActionException {
            listener.signalFired(event.getPayload());
        }
    }

}

