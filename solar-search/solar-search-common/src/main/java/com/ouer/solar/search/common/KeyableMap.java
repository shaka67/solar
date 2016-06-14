/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.common;

import java.util.HashMap;

import com.ouer.solar.able.Keyable;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class KeyableMap<K, V, T> extends HashMap<K, V> implements Keyable<Long> {

    private static final long serialVersionUID = 1L;

    @Override
	public Long getId() {
        return (Long) super.get("id");
    }

}
