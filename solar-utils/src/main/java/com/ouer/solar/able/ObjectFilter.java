/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-4-3 下午2:24:00
 */
public interface ObjectFilter<T> {

    boolean accept(T object);

}
