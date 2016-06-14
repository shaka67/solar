/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.daemon;

import com.google.common.base.Objects;
import com.ouer.solar.Daemon;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DaemonDefinition {

    private final Class<? extends Daemon> daemonClass;
    
    public DaemonDefinition(Class<? extends Daemon> daemonClass) {
        this.daemonClass = daemonClass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(daemonClass);
    }

    public Class<? extends Daemon> getDaemonClass() {
        return daemonClass;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        final DaemonDefinition another = (DaemonDefinition) obj;
        return Objects.equal(daemonClass, another.daemonClass);
    }

}
