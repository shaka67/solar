/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

import java.util.Map;

/**
 * 泛型化的属性接口，可用来替代 <tt>java.util.Properties<tt>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月19日 上午2:31:58
 */
public interface Propertyable<T> {

    /**
     * 根据<code>key</code>获取对应的<code>value</code>，如果查找不到，则返还<code>null</code>
     * 
     * @param key the property key.
     * @return the value in this property list with the specified key value.
     */
    T getProperty(String key);

    /**
     * 获取所有键值对信息
     * 
     * @return all key-value properties
     */
    Map<String, T> getProperties();

    /**
     * 设置键值对
     * 
     * @param key the <code>key</code> to be placed into this property list.
     * @param value the <code>value</code> corresponding to <tt>key</tt>.
     */
    void setProperty(String key, T value);

    /**
     * 设置键值对
     * 
     * @param properties 键值对集
     */
    void setProperties(Map<String, T> properties);

    /**
     * 设置属性值并返回
     * 
     * @param key the <code>key</code> to be placed into this property list.
     * @param value the <code>value</code> corresponding to <tt>key</tt>.
     * @return 自身
     */
    Propertyable<T> setAndReturn(String key, T value);
}
