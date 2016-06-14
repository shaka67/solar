/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 * Simple listening interface for in VM signals.  Each signal is processed off of a limited sized thread pool
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface BusSignalListener<T>
{
    public void signalFired(T signal);
}
