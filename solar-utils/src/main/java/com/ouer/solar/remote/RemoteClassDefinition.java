/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.remote;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Encapsulate remote service implementation class.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RemoteClassDefinition {

    private final Class<?> remoteClass;

    public RemoteClassDefinition(Class<?> remoteClass) {
        Preconditions.checkArgument(remoteClass != null);
        this.remoteClass = remoteClass;
    }

    public Class<?> getRemoteClass() {
        return remoteClass;
    }

    @Override
	public int hashCode() {
    	return Objects.hashCode(remoteClass);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }

        final RemoteClassDefinition another = (RemoteClassDefinition) obj;
        return Objects.equal(remoteClass, another.remoteClass);
    }

}