/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Provides the base level funtionality for implementing  a synchronous BusMember
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class AbstractBusMember implements BusMember {
	
    protected Logger LOG;
    
	private List<String> registeredBuses = null;
    private Object handle = null;
    private BusListener listener = null;
    protected BusRegistry registry = null;

    public AbstractBusMember(BusRegistry registry) {
        registeredBuses = Lists.newArrayList();
        this.registry = registry ;
        LOG = LoggerFactory.getLogger(getClass());
    }

    public AbstractBusMember(BusListener listener, BusRegistry registry) {
        registeredBuses = Lists.newArrayList();
        this.registry = registry;
        this.listener = listener;
    }

    @Override
    public void bind(String busName) {
        registeredBuses.add(busName);
        registry.bind(this,busName);
    }

    @Override
    public void unbind(String busName) {
        registeredBuses.remove(busName);
        registry.unbind(this, busName);
    }

    @Override
    public List<String> getRegisteredBuses() {
        return registeredBuses;
    }

    @Override
    public Object getMemberHandle() {
        return handle;
    }

    @Override
    public void setMemberHandle(Object handle) {
    	this.handle = handle;
    }

    public BusListener getBusListener() {
        return listener;
    }

    public void setBusListener(BusListener listener) {
        this.listener = listener;
    }

    public BusRegistry getBusRegistry() {
        return registry;
    }

}
