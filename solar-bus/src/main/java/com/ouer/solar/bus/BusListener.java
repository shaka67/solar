/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 * Listens on a bus using the receive method
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface BusListener
{
    public <T> void receive(ActionData<T> event) throws ActionException;
}
