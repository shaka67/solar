/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;

import com.google.common.base.Preconditions;

/**
 * Intercept method invocation and log info messages before and after.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class LoggingInterceptor implements MethodInterceptor {

    public LoggingInterceptor(Log log, String subject) {
        Preconditions.checkArgument(log != null, "log instance is required.");
        log_ = log;
        subject_ = subject;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (log_.isInfoEnabled()) {
            log_.info(subject_ + " is going to proceed method: " + invocation.getMethod());
        }
        final Object result = invocation.proceed();
        if (log_.isInfoEnabled()) {
            log_.info(subject_ + " finished method invocation.");
        }
        return result;
    }

    private final Log log_;
    private final String subject_;
}