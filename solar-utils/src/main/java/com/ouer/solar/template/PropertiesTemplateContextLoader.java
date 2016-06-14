/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class PropertiesTemplateContextLoader implements TemplateContextLoader {

	@Override
	public Map<String, Object> getContext(String path) {
		final Properties properties = new Properties();
        final InputStream resource = Thread.currentThread()
                                     .getContextClassLoader()
                                     .getResourceAsStream(path);
        try {
            properties.load(resource);
            final Map<String, Object> context = Maps.newHashMap();
    		final Iterator<Object> it = properties.keySet().iterator();
    		String key;
    		String value;
    		while (it.hasNext()) {
    			key = (String) it.next();
    			value = properties.getProperty(key);
    			context.put(key, value);
    		}
    		return context;
        } catch (final IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
	}

}
