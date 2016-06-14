/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 将一个对象转换成另一个对象的接口.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月16日 上午1:22:18
 */
public interface Transformer<FROM, TO> {

    /**
     * 对象转换，从<code>FROM</code>类型转变成<code>TO</code>类型
     * 
     * @param from 待转换对象
     * @return 转换后的类型<tt>TO</tt>
     */
    TO transform(FROM from);
}
