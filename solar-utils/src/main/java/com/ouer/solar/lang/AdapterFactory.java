/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import com.ouer.solar.able.Adaptable;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 上午12:22:19
 */
public class AdapterFactory<O, N> {

    public static <O, N> N create(Adaptable<O, N> adaptable, O old) {
        return adaptable.forNew(old);
    }

}
