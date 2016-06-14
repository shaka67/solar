/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 * Bus event that forces strong type usage
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class AbstractBusEvent {

    private Object src;
    private Object object;
    private String id;
    private int action;
    
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
    	this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
    	this.id = id;
    }

    public Object getSrc() {
        return src;
    }

    public void setSrc(Object src) {
    	this.src = src;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
