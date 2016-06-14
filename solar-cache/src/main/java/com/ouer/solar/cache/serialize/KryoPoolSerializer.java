/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.serialize;

import java.io.IOException;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.ouer.solar.cache.CacheException;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class KryoPoolSerializer implements Serializer {

    private static class KryoHolder {
        static final int BUFFER_SIZE = 1024;

        private final Kryo kryo;
        private final Output output = new Output(BUFFER_SIZE, -1);     //reuse
        private final Input input = new Input();

        KryoHolder(Kryo kryo) {
            this.kryo = kryo;
        }
    }

    interface KryoPool {

    	public KryoHolder get();

    	public void offer(KryoHolder kryo);
    }

    public static class KryoPoolImpl implements KryoPool {
        /**
         * default is 1500
         * online server limit 3K
         */

        /**
         * thread safe list
         */
        private final Deque<KryoHolder> kryoHolderDeque = new ConcurrentLinkedDeque<KryoHolder>();

        private KryoPoolImpl() {}

        /**
         * @return KryoPool instance
         */
        public static KryoPool getInstance() {
            return Singleton.pool;
        }

        /**
         * get o KryoHolder object
         *
         * @return KryoHolder instance
         */
        @Override
        public KryoHolder get() {
        	// Retrieves and removes the head of the queue represented by this table
            final KryoHolder kryoHolder = kryoHolderDeque.pollFirst();
            return kryoHolder == null ? creatInstnce() : kryoHolder;
        }

        /**
         * create a new kryo object to application use
         * @return KryoHolder instance
         */
        public KryoHolder creatInstnce() {
            final Kryo kryo = new Kryo();
            kryo.setReferences(false);//
            return new KryoHolder(kryo);
        }

        /**
         * return object
         * Inserts the specified element at the tail of this queue.
         *
         * @param kryoHolder ...
         */
        @Override
        public void offer(KryoHolder kryoHolder) {
            kryoHolderDeque.addLast(kryoHolder);
        }

        /**
         * creat a Singleton
         */
        private static class Singleton {
            private static final KryoPool pool = new KryoPoolImpl();
        }
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        KryoHolder kryoHolder = null;
        if (obj == null) {
			throw new CacheException("obj can not be null");
		}
        try {
            kryoHolder = KryoPoolImpl.getInstance().get();
            kryoHolder.output.clear();  //clear output    --> reset when every calling
            kryoHolder.kryo.writeClassAndObject(kryoHolder.output, obj);
            return kryoHolder.output.toBytes();// cannot avoid copy...
        } catch (final CacheException e) {
            throw new CacheException("serialize obj exception");
        } finally {
            KryoPoolImpl.getInstance().offer(kryoHolder);
            obj = null; // for gc
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException {
        KryoHolder kryoHolder = null;
        if (bytes == null) {
			throw new CacheException("bytes can not be null");
		}
        try {
            kryoHolder = KryoPoolImpl.getInstance().get();
            kryoHolder.input.setBuffer(bytes, 0, bytes.length);// call it, and then use input object, discard any array
            return kryoHolder.kryo.readClassAndObject(kryoHolder.input);
        } catch (final CacheException e) {
            throw new CacheException("deserialize bytes exception");
        } finally {
            KryoPoolImpl.getInstance().offer(kryoHolder);
            bytes = null; // for gc
        }
    }

}
