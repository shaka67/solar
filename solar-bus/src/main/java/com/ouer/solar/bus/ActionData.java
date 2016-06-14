/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

import java.util.HashMap;

/**
 * ActionData is used to pass information from one bus to another with Action classes listening and modifyingh this
 * data.  ActionData can also be validated against a catalog to validate its context.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ActionData<T> extends HashMap<String, String> {

    private static final long serialVersionUID = 1L;

    public static final String NO_CATALOG = "NO_CATALOG";
    public static final String ACTION_EVENT = "ACTION_EVENT";
    public static final String RESULT = "ACTION_RESULT";
    
    private String catalog;
    private String bus;
    private T payload;

    public ActionData() {
    	this(NO_CATALOG);
    }

    public ActionData(String catalog) {
    	if (catalog == null || catalog.length() == 0) {
            catalog = NO_CATALOG;
        } else {
        	this.catalog = catalog;
        }

    }

    public void setCatalog(String catalog) {
    	this.catalog = catalog;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
    	this.bus = bus;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getString(String key) {
        String returnStr = null;
        try {
            returnStr = get(key);
        } catch (final Exception e) {
            // no-op
        }
        if (returnStr != null) {
            return returnStr.trim();
        }

        return null;
    }

}
