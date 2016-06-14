/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 转换器
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午11:23:24
 */
public interface TransformTo<TO> {

    /**
     * 将对象转换成<tt>TO</tt>类型
     * 
     * @return 转换后的结果
     */
    TO transform();

}
