/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.able.Closure;

/**
 * 并发相关的一些常用工具类
 * 
 * <p>
 * 这个类中的每个方法都可以“安全”地处理 <code>null</code> ，而不会抛出 <code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月15日 下午4:07:30
 */
public abstract class ConcurrentUtil {

    /**
     * 默认超时时间，1分钟
     */
    private static final int DEFAULT_TIMEOUT = 60;

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentUtil.class);

    /**
     * 在1分钟内关闭<code>executorService</code>，如果不能正常结束，将强制关闭。
     * <p>
     * 如果<code>executorService</code>不存在或已关闭，将不做任何处理。
     * 
     * @param executorService ## @see ExecutorService
     */
    public static final void shutdownAndAwaitTermination(ExecutorService executorService) {

        shutdownAndAwaitTermination(executorService, DEFAULT_TIMEOUT, TimeUnit.SECONDS);

    }

    /**
     * 关闭<code>executorService</code>，如果<code>executorService</code> 不存在或已关闭，将不做任何处理。在限定时间内如无法正常结束，将强制关闭。
     * 
     * @param executorService ## @see ExecutorService
     * @param timeout 限定时间
     * @param timeUnit 单位 @see TimeUnit
     */
    public static final void shutdownAndAwaitTermination(ExecutorService executorService, long timeout,
            TimeUnit timeUnit) {
        if (isShutDown(executorService)) {
            return;
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(timeout, timeUnit)) {
                executorService.shutdownNow();
            }
        } catch (final InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        executorService.shutdownNow();

    }

    /**
     * 如果<code>executorService</code>不存在，也当做“已关闭”对待
     * 
     * @param executorService ## @see ExecutorService
     * @return 如果<code>executorService</code>“已关闭”，则返回<code>true</code>
     */
    public static boolean isShutDown(ExecutorService executorService) {
        return (executorService == null || executorService.isShutdown());
    }

    public static void execute(boolean asyn, Runnable runnable) {
        if (asyn) {
            asynExecute(runnable);
            return;
        }

        if (runnable == null) {
            return;
        }
        runnable.run();
    }

    public static void execute(boolean asyn, final Object object, final String methodName, final Object...inputs) {
        if (asyn) {
            asynExecute(object, methodName, inputs);
            return;
        }

        if (object == null || StringUtil.isBlank(methodName)) {
            return;
        }
        doExecute(object, methodName, inputs);
    }

    public static void execute(boolean asyn, final Closure closure, final Object...inputs) {
        if (asyn) {
            asynExecute(closure, inputs);
        }

        if (closure == null) {
            return;
        }
        closure.execute(inputs);
    }

    public static void asynExecute(Runnable runnable) {
        if (runnable == null) {
            return;
        }

        new Thread(runnable).start();
    }

    public static void asynExecute(final Object object, final String methodName, final Object...inputs) {
        if (object == null || StringUtil.isBlank(methodName)) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                doExecute(object, methodName, inputs);
            }

        }).start();
    }

    public static void asynExecute(final Closure closure, final Object...inputs) {
        if (closure == null) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                closure.execute(inputs);
            }

        }).start();
    }

    private static void doExecute(final Object object, final String methodName, final Object...inputs) {
        Class<?>[] parameterTypes = null;
        if (ArrayUtil.isEmpty(inputs)) {
            parameterTypes = new Class<?>[0];
        } else {
            final int size = inputs.length;
            parameterTypes = new Class<?>[size];
            for (int i = 0; i < size; i++) {
                parameterTypes[i] = inputs[i].getClass();
            }
        }

        ReflectionUtil.invokeMethod(object, methodName, parameterTypes, inputs);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException e) {
            logger.error("InterruptedException error", e);
            Thread.currentThread().interrupt();
        }
    }
    
    public static Exception sleep(long timeout, TimeUnit timeUnit) {
		try {
			timeUnit.sleep(timeout);
			return null;
		} catch (final InterruptedException e) {
			return e;
		}
	}

    public static void park(long sleepTime, TimeUnit unit) {
        final long sleepTimeNanos = TimeUnit.NANOSECONDS.convert(sleepTime, unit);
        LockSupport.parkNanos(sleepTimeNanos);
    }

}
