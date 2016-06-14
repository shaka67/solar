/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 * Publishes data to a name logical bus while hidding the identity of the sender for security reasons.  Avoids have a
 * rogue listener on the bus introspectand execute the methds of the sender.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class BusPublisherProxy extends AbstractBusMember implements BusPublisher, BusListener {

    public BusPublisherProxy(BusRegistry registry) {
        super(registry);
    }

    @Override
    public <T> void receive(ActionData<T> event) throws ActionException {
    }

    @Override
    public <T> void send(String bus, ActionData<T> data) {
        registry.send(bus,data);
    }

    public <T> void send(String bus, ActionData<T> data, boolean concurent) {
        registry.send(bus, data, concurent);
    }

    public <T> void send(ActionData<T> data) {
    	if (data == null) {
    	    LOG.error("send() null parameter");
    		return;
    	}

    	final String bus = data.getBus();
    	if (bus == null) {
    	    LOG.error("send() Logical bus not set.  The following data was not sent: " + data.getPayload());
           return;
        }

        registry.send(bus,data);
    }

    public <T> void send(ActionData<T> data, boolean notifyConcurrent) {
        if (data == null) {
            LOG.error("send() null parameter");
            return;
        }

        final String bus = data.getBus();

        if (bus == null) {
           LOG.error("send() Logical bus not set.  The following data was not sent: " + data.getPayload());
           return;
        }

        registry.send(bus,data, notifyConcurrent);
    }

}
