/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ouer.solar.able.Keyable;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class BatchBuffer<T extends Keyable<String>> implements Buffer<T> {

    private List<Buffer<T>> buffers = Lists.newArrayList();
    private final Map<String, Buffer<T>> bufferMap = Maps.newHashMap();
    private final String name;
    private boolean active;

    public BatchBuffer() {
        this.name = this.getClass().getSimpleName();
    }

    public BatchBuffer(String name) {
        this.name = name;
    }

    @Override
    public void start() throws Exception {
        for (final Buffer<T> buffer : buffers) {
            buffer.start();
        }

        active = true;
    }

    @Override
    public void stop() throws Exception {
        for (final Buffer<T> buffer : buffers) {
            buffer.stop();
        }

        active = false;
    }

    public void add(Buffer<T> buffer) {
        buffers.add(buffer);
        bufferMap.put(buffer.getName(), buffer);
    }

    public void clean() {
        buffers.clear();
        bufferMap.clear();
    }

    public void remove(String key) {
        final Buffer<T> buffer = bufferMap.remove(key);
        if (buffer != null) {
            buffers.remove(buffer);
        }
    }

    public void remove(Buffer<T> buffer) {
        buffers.remove(buffer);
        bufferMap.remove(buffer.getName());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean add(T record) {
        final Buffer<T> buffer = bufferMap.get(record.getId());
        return (buffer == null) ? false : buffer.add(record);
    }

    public void setBuffers(List<Buffer<T>> buffers) {
        this.buffers = buffers;
        for (final Buffer<T> buffer : buffers) {
            bufferMap.put(buffer.getName(), buffer);
        }
    }

    // FIXME 
    @Override
    public boolean add(List<T> records) {
        boolean ret = true;
        for (final T record : records) {
            if (!add(record)) {
                ret = false;
            }
        }

        return ret;
    }

    @Override
    public boolean isActive() {
        return active;
    }

}
