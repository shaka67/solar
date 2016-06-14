/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.resourcebundle.properties;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

import java.io.InputStream;

import com.ouer.solar.biz.resourcebundle.AbstractResourceBundle;
import com.ouer.solar.biz.resourcebundle.AbstractResourceBundleFactory;
import com.ouer.solar.biz.resourcebundle.ResourceBundleConstant;
import com.ouer.solar.biz.resourcebundle.ResourceBundleCreateException;
import com.ouer.solar.biz.resourcebundle.ResourceBundleFactory;
import com.ouer.solar.biz.resourcebundle.ResourceBundleLoader;
import com.ouer.solar.biz.resourcebundle.proxy.AutoReloadResourceBundleProxy;

public class PropertiesResourceBundleFactory extends AbstractResourceBundleFactory {

	public PropertiesResourceBundleFactory() {
		super();
	}

	public PropertiesResourceBundleFactory(ClassLoader classLoader) {
		super(classLoader);
	}

	public PropertiesResourceBundleFactory(ResourceBundleLoader loader) {
		super(loader);
	}
	
	@Override
	protected AbstractResourceBundle parse(InputStream stream, String systemId)
			throws ResourceBundleCreateException {
		AbstractResourceBundle resourceBundle = new PropertiesResourceBundle(stream, systemId);
		if (ResourceBundleFactory.isAutoReload()) {
			final String bundleName = systemId.substring(0, systemId.length()
					- ResourceBundleConstant.RB_RESOURCE_EXT_PROPERTIES.length());
			resourceBundle = new AutoReloadResourceBundleProxy(
					resourceBundle, this, bundleName, ResourceBundleFactory
							.getCheckModifyInterval());
		}
		return resourceBundle;
	}
	
	@Override
	protected String getFilename(String bundleName) {
		return super.getFilename(bundleName)
				+ ResourceBundleConstant.RB_RESOURCE_EXT_PROPERTIES;
	}

}
