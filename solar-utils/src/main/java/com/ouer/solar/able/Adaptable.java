/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 适配器接口
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 上午2:41:25
 */
public interface Adaptable<O, N> {

    /**
     * 将<code>old</code>对象转换成新对象
     * 
     * @param old 旧对象
     * @return 新对象
     */
    N forNew(O old);

}
