/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.resourcebundle.proxy;

import java.util.Enumeration;

import com.ouer.solar.biz.resourcebundle.AbstractResourceBundle;
import com.ouer.solar.biz.resourcebundle.ResourceBundleCreateException;
import com.ouer.solar.biz.resourcebundle.ResourceBundleFactory;
import com.ouer.solar.logger.Logger;
import com.ouer.solar.logger.LoggerFactory;

/**
 * 能够自动重新装载修改后的ResourceBundle的代理类
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:59:45
 */

public class AutoReloadResourceBundleProxy extends AbstractResourceBundle {

    private Logger logger = LoggerFactory.getLogger(AutoReloadResourceBundleProxy.class);

    private volatile AbstractResourceBundle resourceBundle;
    private final ResourceBundleFactory resourceBundleFactory;
    private final String lookupBundleName;

    /**
     * 最后修改时间
     */
    private volatile long lastModified;
    /**
     * 最后一次检测lastModified的时间戳
     */
    private volatile long lastCheckModifyTimestamp;

    /**
     * 检测lastModified的时间间隔，单位：毫秒, 默认 5*1000
     */
    private long checkModifyInterval = 5 * 1000;

    public AutoReloadResourceBundleProxy(AbstractResourceBundle resourceBundle,
            ResourceBundleFactory resourceBundleFactory, String lookupBundleName, long checkModifiInterval) {
        this.resourceBundle = resourceBundle;
        setBaseName(resourceBundle.getBaseName());
        this.setLocale(resourceBundle.getLocale());

        this.resourceBundleFactory = resourceBundleFactory;
        this.lookupBundleName = lookupBundleName;
        lastModified = resourceBundleFactory.lastModified(lookupBundleName);
        lastCheckModifyTimestamp = System.currentTimeMillis();
        checkModifyInterval = checkModifiInterval;
    }

    protected boolean isModified() {
        final long tmp = System.currentTimeMillis() - lastCheckModifyTimestamp;
        if (tmp < checkModifyInterval && tmp >= 0) {
            return false;
        }
        lastCheckModifyTimestamp = System.currentTimeMillis();
        logger.debug("check resource last modify time:[{}]", lookupBundleName);
        final long nowLastModified = resourceBundleFactory.lastModified(lookupBundleName);
        if (lastModified != nowLastModified) {
            lastModified = nowLastModified;
            return true;
        }
        return false;
    }

    @Override
	public Enumeration<String> getKeys() {
        if (isModified()) {
            try {
                resourceBundle = resourceBundleFactory.createBundle(lookupBundleName);
            } catch (final ResourceBundleCreateException e) {
                logger.warn("error resourceBundleFactory.createBundle(lookupBundleName), lookupBundleName:"
                        + lookupBundleName, e);
            }
        }
        // XXX 由于 resourceBundle.handleGetObject(String key)不可访问，故采用了近似的
        // resourceBundle.getObject(key) 替代
        return resourceBundle.getKeys();
    }

    @Override
	protected Object handleGetObject(String key) {
        if (isModified()) {
            try {
                resourceBundle = resourceBundleFactory.createBundle(lookupBundleName);
            } catch (final ResourceBundleCreateException e) {
                logger.warn("error resourceBundleFactory.createBundle(lookupBundleName), lookupBundleName:"
                        + lookupBundleName, e);
            }
        }
        return resourceBundle.getObject(key);
    }
}
