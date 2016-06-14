/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.remote;

import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Encapsulate remote service implementation class.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RemoteClassDefinitions {

    private final Set<Class<?>> remoteClasses;
    
    public RemoteClassDefinitions(Set<Class<?>> remoteClasses) {
        Preconditions.checkArgument(remoteClasses != null);
        this.remoteClasses = remoteClasses;
    }

    public Set<Class<?>> getRemoteClasses() {
        return remoteClasses;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null || !obj.getClass().equals(getClass())) {
//            return false;
//        }
//        final RemoteClassDefinitions another = (RemoteClassDefinitions) obj;
//        return getRemoteClass().equals(another.getRemoteClass());
//    }

}