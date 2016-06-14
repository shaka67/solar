/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月18日 下午7:59:57
 */
public interface Introspector {

    ClassDescriptor lookup(Class<?> type);

    ClassDescriptor register(Class<?> type);

    void reset();

}
