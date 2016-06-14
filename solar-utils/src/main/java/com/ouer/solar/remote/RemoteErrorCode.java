/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.remote;

/**
 * Enumerate a list of possible error conditions.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum RemoteErrorCode {

    UNKNOWN_EXCEPTION,
    NO_DECLARED_EXCHANGE,
    NO_REGISTERED_REMOTE_METHOD,
    NO_REGISTERED_REMOTE_GROUP,
    NO_REGISTERED_REMOTE_SERVICE,
    NO_SUCH_REMOTE_METHOD_ON_TARGET,
    ILLEGAL_REMOTE_METHOD_ACCESS_ON_TARGET,
    INVOKE_REMOTE_METHOD_EXCEPTION_ON_TARGET,
    INVOKE_REMOTE_METHOD_TIMEOUT,
    ;

    @Override
    public String toString() {
        return "[" + name() + "]";
    }

}