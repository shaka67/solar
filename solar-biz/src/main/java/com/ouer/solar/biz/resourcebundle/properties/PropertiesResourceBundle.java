/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.resourcebundle.properties;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.ouer.solar.StringUtil;
import com.ouer.solar.biz.resourcebundle.AbstractResourceBundle;
import com.ouer.solar.biz.resourcebundle.ResourceBundleConstant;
import com.ouer.solar.biz.resourcebundle.ResourceBundleCreateException;
import com.ouer.solar.biz.resourcebundle.ResourceBundleEnumeration;

public class PropertiesResourceBundle extends AbstractResourceBundle {

	protected static final String CODE_SUFFIX = StringUtil.substring(
			ResourceBundleConstant.XPATH_RESOURCE_MESSAGE_CODE,
			ResourceBundleConstant.XPATH_RESOURCE_MESSAGE_SPACE.length());

	protected Map<String, Object> values = new HashMap<String, Object>();
	
	public PropertiesResourceBundle(InputStream stream, String systemId) throws ResourceBundleCreateException {
		init(stream, systemId);
	}
	
	private void init(InputStream stream, String systemId) throws ResourceBundleCreateException {
		final Properties props = new Properties();
		try {
			props.load(stream);
			final Iterator<Object> it = props.keySet().iterator();
			String key;
			String value;
			while (it.hasNext()) {
				key = (String) it.next();
				value = props.getProperty(key);
				if (values.containsKey(key)) {
					throw new ResourceBundleCreateException(
							ResourceBundleConstant.RB_DUPLICATED_RESOURCE_KEY,
							new Object[] { key }, null);
				}
				values.put(key, value);
			}
        } catch (final IOException e) {
            throw new ResourceBundleCreateException(ResourceBundleConstant.RB_FAILED_READING_PROPERTIES_DOCUMENT,
            		new Object[] { systemId }, e);
        } finally {
        	if (stream != null) {
        		try {
					stream.close();
				} catch (final IOException e) {
					throw new ResourceBundleCreateException(ResourceBundleConstant.RB_FAILED_READING_PROPERTIES_DOCUMENT,
		            		new Object[] { systemId }, e);
				}
        	}
        }
	}
	
	@Override
	protected Object handleGetObject(String key) {
		return values.get(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		final java.util.ResourceBundle parent = getParent();

		return new ResourceBundleEnumeration(values.keySet(),
				(parent != null) ? parent.getKeys() : null);
	}

}
