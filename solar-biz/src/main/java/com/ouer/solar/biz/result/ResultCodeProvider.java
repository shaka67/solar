/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.biz.enums.IntegerEnum;
import com.ouer.solar.biz.resourcebundle.AbstractResourceBundle;
import com.ouer.solar.biz.resourcebundle.ResourceBundleFactory;
import com.ouer.solar.i18n.LocaleUtil;
import com.ouer.solar.logger.Logger;
import com.ouer.solar.logger.LoggerFactory;

/**
 * 支持自定义枚举类的ResultCode
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 下午4:17:21
 */

public class ResultCodeProvider extends IntegerEnum implements ResultCode {

    private static final long serialVersionUID = 3257848762037777207L;

    public static final ResultCode SUCCESS = createResultCode();

    public static final ResultCode GENERIC_FAILURE = createResultCode();

    public static final ResultCode SERVICE_FAILURE = createResultCode();

    public static final ResultCode DAO_FAILURE = createResultCode();

    private transient Logger logger = LoggerFactory.getLogger(ResultCodeProvider.class);
    private Map<Locale, AbstractResourceBundle> resourceBundles;

    /**
     * 创建一个<code>ResultCode</code>结果码
     * 
     * @return <code>ResultCode</code>结果码
     */
    protected static ResultCode createResultCode() {
        return (ResultCode) create();
    }

    @Override
	public ResultCodeMessage getMessage() {
        return new ResultMessageProvider(this, getResourceBundle());
    }

    /**
     * 创建一个<code>ResultCodeMessage</code>结果码信息
     * 
     * @param params 参数化信息
     * @return <code>ResultCodeMessage</code>结果码信息
     */
    public ResultCodeMessage getMessage(Object...params) {
        return new ResultMessageProvider(this, getResourceBundle(), params);
    }

    /**
     * 获取抽象的“资源束” @see AbstractResourceBundle
     * 
     * @return “资源束”
     */
    protected final synchronized AbstractResourceBundle getResourceBundle() {
        if (this.resourceBundles == null) {
            this.resourceBundles = CollectionUtil.createHashMap();
        }

        Locale contextLocale = LocaleUtil.getContext().getLocale();
        AbstractResourceBundle resourceBundle = this.resourceBundles.get(contextLocale);

        if (resourceBundle == null) {
            Class<?> resultCodeClass = super.getClass();
            do {
                String resourceBundleName = resultCodeClass.getName();
                if (logger.isDebugEnabled()) {
                    logger.debug("Trying to load resource bundle: {}", resourceBundleName);
                }

                try {
                    resourceBundle = ResourceBundleFactory.getBundle(resourceBundleName);
                } catch (MissingResourceException e) {
                    logger.error("Resource bundle not found: " + resourceBundleName);
                    resultCodeClass = resultCodeClass.getSuperclass();
                }
            } while ((resourceBundle == null) && (resultCodeClass != null));

            this.resourceBundles.put(contextLocale, resourceBundle);
        }

        return resourceBundle;
    }

    @Override
	public int getCode() {
        return getMessage().getCode();
    }

}
