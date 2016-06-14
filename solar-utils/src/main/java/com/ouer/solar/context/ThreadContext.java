/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ouer.solar.bo.BaseObject;

/**
 * 线程执行的上下文内容
 * 
 * @author Darwin(Tianxin)
 */
public class ThreadContext {

    /**
     * 线程上下文变量的持有者
     */
    private static final ThreadLocal<Map<String, Object>> CTX_HOLDER = new ThreadLocal<Map<String, Object>>();

    static {
        CTX_HOLDER.set(new HashMap<String, Object>());
    }

    /**
     * 添加内容到线程上下文中
     * 
     * @param key
     * @param value
     */
    public static final void putContext(String key, Object value) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            // FIXME 需要注意释放，或者用软引用之类的替代，timewait或回收队列均可考虑
            ctx = new HashMap<String, Object>();
            CTX_HOLDER.set(ctx);
        }
        ctx.put(key, value);
    }

    /**
     * 从线程上下文中获取内容
     * 
     * @param key
     */
    @SuppressWarnings("unchecked")
    public static final <T extends Object> T getContext(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return (T) ctx.get(key);
    }

    /**
     * 获取线程上下文
     * 
     * @param key
     */
    public static final Map<String, Object> getContext() {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return ctx;
    }

    /**
     * 删除上下文中的key
     * 
     * @param key
     */
    public static final void remove(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            ctx.remove(key);
        }
    }

    /**
     * 上下文中是否包含此key
     * 
     * @param key
     * @return
     */
    public static final boolean contains(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            return ctx.containsKey(key);
        }
        return false;
    }

    /**
     * 清空线程上下文
     */
    public static final void clean() {
        CTX_HOLDER.set(null);
    }

    /**
     * 初始化线程上下文
     */
    public static final void init() {
        CTX_HOLDER.set(new HashMap<String, Object>());
    }

    /**
     * 获取用来做分库分表的key
     * 
     * @return 下午8:34:15 created by Darwin(Tianxin)
     */
    @SuppressWarnings("unchecked")
    public static final <K extends Serializable> K getShardKey() {
        return (K) getContext(SHARD_KEY);
    }

    /**
     * 设置做分表分库的切分的key
     * 
     * @param shardKey 下午8:37:51 created by Darwin(Tianxin)
     */
    public static final <K extends Serializable> void putShardKey(K shardKey) {
        putContext(SHARD_KEY, shardKey);
    }

    /**
     * 获取Session中的用户信息
     * 
     * @return 下午8:34:15 created by Darwin(Tianxin)
     */
    @SuppressWarnings("unchecked")
    public static final <U extends BaseObject<?>> U getSessionVisitor() {
        return (U) getContext(VISITOR_KEY);
    }

    /**
     * 设置做分表分库的切分的key
     * 
     * @param shardKey 下午8:37:51 created by Darwin(Tianxin)
     */
    public static final <K extends Serializable, U extends BaseObject<K>> void putSessionVisitor(U sessionVisitor) {
        putContext(VISITOR_KEY, sessionVisitor);
    }

    /**
     * 线程日志的级别
     * 
     * @param logLevel 上午11:20:49 created by Darwin(Tianxin)
     */
    public static final void putThreadLog(Integer logLevel) {
        putContext(THREAD_LOG_KEY, logLevel);
    }

    /**
     * 获取线程日志的级别
     * 
     * @return 上午11:22:24 created by Darwin(Tianxin)
     */
    public static final Integer getThreadLog() {
        return getContext(THREAD_LOG_KEY);
    }

    /**
     * 用来做分库分表的切分ID
     */
    private static final String SHARD_KEY = "shardKey";

    /**
     * 当前Session中登陆的user
     */
    private static final String VISITOR_KEY = "sessionVisitor";

    /**
     * 线程的日志级别
     */
    private static final String THREAD_LOG_KEY = "threadLog";
}
