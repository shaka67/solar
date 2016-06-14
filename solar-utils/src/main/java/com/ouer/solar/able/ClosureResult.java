/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 带返回值得<code> Closure</code>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 上午1:10:06
 */
public interface ClosureResult<T> extends Closure {

    /**
     * 获取返回值
     * 
     * @return 返回值
     */
    T getResult();

}
