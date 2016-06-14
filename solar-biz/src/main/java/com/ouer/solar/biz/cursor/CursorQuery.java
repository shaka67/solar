/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.cursor;

import com.ouer.solar.able.Keyable;
import com.ouer.solar.biz.entity.GenericID;

/**
 * 一个游标操作的请求条件
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月22日 上午1:54:55
 */

public class CursorQuery<K extends Comparable<K>, V extends Keyable<K>> extends GenericID<K> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5325636023945346027L;

    /**
     * 批量数,默认500
     */
    private int batchSize = 500;

    /**
     * 余数
     */
    private int mod;

    /**
     * 模值
     */
    private int baseMod;

    /**
     * 当前最小ID
     */
    private K minId;

    // private int maxId = Integer.MAX_VALUE;

    /**
     * 当前最大ID
     */
    private K maxId;

    //
    // /**
    // * 上次修改日期
    // */

    /**
     * 创建游标操作请求
     * 
     * @param batchSize 批量大小
     * @param mod 余数
     * @param baseMod 模值
     * @return 游标操作请求 @see Request
     */
    public static <K extends Comparable<K>, V extends Keyable<K>> CursorQuery<K, V> createRequest(int batchSize,
            int mod, int baseMod) {
        CursorQuery<K, V> request = new CursorQuery<K, V>();

        request.setMod(mod).setBaseMod(baseMod).setBatchSize(batchSize);

        return request;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public CursorQuery<K, V> setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public int getMod() {
        return mod;
    }

    public CursorQuery<K, V> setMod(int mod) {
        this.mod = mod;
        return this;
    }

    public int getBaseMod() {
        return baseMod;
    }

    public CursorQuery<K, V> setBaseMod(int baseMod) {
        this.baseMod = baseMod;
        return this;
    }

    public K getMinId() {
        return minId;
    }

    public CursorQuery<K, V> setMinId(K minId) {
        this.minId = minId;
        return this;
    }

    public K getMaxId() {
        return maxId;
    }

    public CursorQuery<K, V> setMaxId(K maxId) {
        this.maxId = maxId;
        return this;
    }

}
