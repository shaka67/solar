/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.util.List;

import com.ouer.solar.able.Service;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface Buffer<T> extends Service {

    public String getName();

    public boolean add(T record);

    public boolean add(List<T> records);

}
