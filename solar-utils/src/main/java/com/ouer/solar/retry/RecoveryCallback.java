/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.retry;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface RecoveryCallback<T> {

    public T recover(RetryContext context) throws Exception;

}