/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 具有主键性质的接口，类似<code>DB</code>表中的<code>id</code>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月19日 上午2:30:48
 * @param <T>
 */
public interface Keyable<T> {

    /**
     * 返回ID
     * 
     * @return ID
     */
    public T getId();

}
