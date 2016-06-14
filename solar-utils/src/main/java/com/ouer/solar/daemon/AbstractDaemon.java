/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.daemon;

import com.ouer.solar.Daemon;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class AbstractDaemon implements Daemon {

    public AbstractDaemon() {
        super();
    }

    /**
     * Override it if you need to have specific steps to activate
     */
    @Override
    public void start() {}

    /**
     * Override it if you need to have specific steps to deactivate
     */
    @Override
    public void shutdown() {}

    /**
     * Override it if you know how to determine the life cycle of your daemon
     */
    @Override
    public boolean isAlive() {
        return false;
    }

}