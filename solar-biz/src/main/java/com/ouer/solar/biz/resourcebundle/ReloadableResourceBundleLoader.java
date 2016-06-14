/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.resourcebundle;

/**
 * 装入resource bundle的数据.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:15:00
 */
public interface ReloadableResourceBundleLoader extends ResourceBundleLoader {
    /**
     * 取得资源束配置最近修改时间。
     * 
     * @param bundleName 要查找的bundle文件名
     * @return bundle配置的最近修改时间，如果不支持，则返回<code>0</code>
     */
    long lastModified(String bundleFileName);
}
