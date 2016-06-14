/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.image;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;
import com.ouer.solar.bus.BusSignalManager;
import com.ouer.solar.config.image.ImageConfig;
import com.ouer.solar.config.image.ImageConfigLocator;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ImageProxy implements ImageApi {

	private static final String IMAGE_SERVICES_FILE = "image-services.properties";

	private final ImageConfigLocator locator;
	private final BusSignalManager bsm;

	private final Map<String, ImageService> services = Maps.newHashMap();

	public ImageProxy(ImageConfigLocator locator, BusSignalManager bsm) {
		this.locator = locator;
		this.bsm = bsm;
		initServices();
	}

	private void initServices() {
		final Properties properties = new Properties();
        final InputStream resource = Thread.currentThread()
                                     .getContextClassLoader()
                                     .getResourceAsStream(IMAGE_SERVICES_FILE);
        try {
            properties.load(resource);
            final Iterator<Object> it = properties.keySet().iterator();
            String thirdparty;
            String serviceClass;
            ImageService service;
            while (it.hasNext()) {
            	thirdparty = (String) it.next();
            	serviceClass = properties.getProperty(thirdparty);
            	service = (ImageService) Class.forName(serviceClass).newInstance();
            	service.initialize();
            	if (!service.isInitialized()) {
            		throw new RuntimeException("Failed to init serviceClass");
            	}
            	services.put(thirdparty, service);
            }
        } catch (final Exception e) {
            throw new RuntimeException("Failed to init image proxy", e);
        }
	}

	@Override
	public Image store(ImageStoreRequest request) throws ImageStoreException {
		final ImageConfig config = locator.getConfig(request.getAppId());
		final Image image = services.get(config.getThirdparty()).store(config, request.getInput(), request.getKey());
		bsm.signal(new ImageStoreDetails(config, image), true);
		return image;
	}

	@Override
	public String retrieve(ImagePrivateRetrieve request) {
		final ImageConfig config = locator.getConfig(request.getAppId());
		return services.get(config.getThirdparty()).retrieve(config, request.getDetails());
	}

	@Override
	public String retrieve(ImagePublicRetrieve request) {
		return services.get(request.getKey().getThirdparty()).retrieve(request);
	}

}
