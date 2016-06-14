/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.able.ToStringable;
import com.ouer.solar.cache.FieldCache;

/**
 * 字符串输出，打印对象各 {@link Field java.lang.reflect.Field}
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月16日 下午11:56:52
 */
public class StringableSupport implements ToStringable {

    private static final FieldCache fieldCache = FieldCache.getInstance();

    @Override
	public String toString() {
        return ReflectionUtil.dump(this, fieldCache.getInstanceFields(this.getClass()));
    }

}
