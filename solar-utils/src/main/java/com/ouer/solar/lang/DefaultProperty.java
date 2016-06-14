/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.able.Propertyable;

/**
 * 默认的键值对实现
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-25 下午5:50:52
 */
public class DefaultProperty extends StringableSupport implements Propertyable<Object>, Serializable {

    private static final long serialVersionUID = 7718207939067495513L;
    
    private Map<String, Object> properties = CollectionUtil.createHashMap();

    @Override
    public Object getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public Map<String, Object> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @Override
    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public void setProperties(Map<String, Object> properties) {
        this.properties.clear();

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            if (null != entry.getValue()) {
                this.properties.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Propertyable<Object> setAndReturn(String key, Object value) {
        properties.put(key, value);

        return this;
    }

}
