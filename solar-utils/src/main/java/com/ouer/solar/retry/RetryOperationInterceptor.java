/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.retry;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.common.base.Preconditions;

/**
 * Intercept method invocation for retry operation.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RetryOperationInterceptor implements MethodInterceptor {

    private final RetryOperationTemplate template;
    
    public RetryOperationInterceptor(RetryOperationTemplate template)
    {
        Preconditions.checkArgument(template != null, "retry operation template is required.");
        this.template = template;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable
    {
        return template.execute(new RetryCallback<Object>() {

            @Override
            public Object apply(RetryContext context) throws Exception {
                try {
                    return invocation.proceed();
                } catch(Exception e) {
                    throw e;
                } catch (Throwable e) {
                    throw new IllegalStateException(e);
                }
            }

        });
    }

}