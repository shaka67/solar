/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RetryOperationTemplate {

    private static final Logger LOG = LoggerFactory.getLogger(RetryOperationTemplate.class);
    
    private final RetryPolicy policy;
    
    public RetryOperationTemplate()
    {
        this(new SimpleRetryPolicy());
    }

    public RetryOperationTemplate(RetryPolicy policy)
    {
        Preconditions.checkArgument(policy != null, "retry policy cannot be null.");
        this.policy = policy;
    }

    public final <T> T execute(RetryCallback<T> callback) throws Throwable
    {
        return doExecute(callback, null);
    }

    public final <T> T execute(RetryCallback<T> retry, RecoveryCallback<T> recovery) throws Throwable {
        return doExecute(retry, recovery);
    }

    protected <T> T doExecute(RetryCallback<T> retryCallback, RecoveryCallback<T> recoveryCallback) throws Throwable
    {
        final RetryContext context = policy.open();

        try
        {
            Throwable cause = null; // last exception

            while (policy.canRetry(context))
            {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("[Retry] count = " + context.getRetryCount());
                }

                try {
                    return retryCallback.apply(context);
                } catch (final Throwable e) {
                    cause = e;
                    context.incrementRetryCount();
                }

                if (policy.getRetryInterval() > 0) {
                    Thread.sleep(policy.getRetryInterval());
                }
            }

            if (recoveryCallback != null) {
                return recoveryCallback.recover(context);
            }

            final String error = "Retry exhausted after last attempt with NO specified recovery callback.";
            LOG.warn(error);

            if (cause != null) {
                LOG.error("The cause of the retry exception is: " + cause.getMessage(), cause);
                throw cause;
            }

            throw new RetryException(error, cause); // this line should not be executed in theory
        }
        finally
        {
            policy.close();
        }
    }

}