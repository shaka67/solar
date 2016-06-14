/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import com.ouer.solar.MessageUtil;
import com.ouer.solar.able.Computable;
import com.ouer.solar.able.Entryable;
import com.ouer.solar.biz.entity.Entity;
import com.ouer.solar.biz.resourcebundle.ResourceBundleConstant;
import com.ouer.solar.biz.resourcebundle.ResourceBundleFactory;
import com.ouer.solar.cache.ConcurrentCache;
import com.ouer.solar.i18n.LocaleUtil;
import com.ouer.solar.logger.CachedLogger;

/**
 * <code>ResultCode</code>辅助类。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 下午6:05:31
 */

public class ResultCodeUtil extends CachedLogger implements Serializable, ResourceBundleConstant {
    private static final long serialVersionUID = -1283084401300246727L;
    private final ResultCode resultCode;
    private transient Map<Locale, ResourceBundle> resourceBundles;

    /**
     * 取得指定<code>ResultCode</code>对应的util实例。
     */
    public ResultCodeUtil(ResultCode resultCode) {
        this.resultCode = resultCode;

        if (!(resultCode instanceof Enum)) {
            throw new IllegalArgumentException("ResultCode should be java.lang.Enum: " + getClass().getName());
        }
    }

    /**
     * 取得<code>ResultCode</code>名称。
     */
    public String getName() {
        return Enum.class.cast(resultCode).name();
    }

    /**
     * 取得<code>ResultCode</code>code值。
     */
    public int getCode() {
        return getMessage().getCode();
    }

    /**
     * 创建result code的描述信息。
     */
    public ResultCodeMessage getMessage() {
        final ResourceBundle resourceBundle = getResourceBundle();

        return new ResultCodeMessage() {
            @Override
			public String getName() {
                return resultCode.getName();
            }

            @Override
			public ResultCode getResultCode() {
                return resultCode;
            }

            @Override
			public String getMessage() {
                return MessageUtil.getMessage(resourceBundle, resultCode.getName());
            }

            @Override
			public int getCode() {
                String code = MessageUtil.getMessage(resourceBundle, resultCode.getName() + XPATH_RESOURCE_CODE_SUFFIX);
                return (code == null) ? 0 : Integer.valueOf(code);
            }

            @Override
			public String toString() {
                return getMessage();
            }
        };
    }

    /**
     * 创建result code的描述信息。
     * 
     * @param args 传入参数
     */
    public ResultCodeMessage getMessage(final Object...args) {
        final ResourceBundle resourceBundle = getResourceBundle();

        return new ResultCodeMessage() {
            @Override
			public String getName() {
                return resultCode.getName();
            }

            @Override
			public ResultCode getResultCode() {
                return resultCode;
            }

            @Override
			public String getMessage() {
                return MessageUtil.getMessage(resourceBundle, resultCode.getName(), args);
            }

            @Override
			public int getCode() {
                String code =
                        MessageUtil.getMessage(resourceBundle, resultCode.getName() + XPATH_RESOURCE_CODE_SUFFIX, args);
                return (code == null) ? 0 : Integer.valueOf(code);
            }

            @Override
			public String toString() {
                return getMessage();
            }
        };
    }

    /**
     * 取得存放result code描述信息的resouce bundle。
     */
    protected final synchronized ResourceBundle getResourceBundle() {
        if (resourceBundles == null) {
            resourceBundles = new HashMap<Locale, ResourceBundle>();
        }

        Locale contextLocale = LocaleUtil.getContext().getLocale();
        ResourceBundle resourceBundle = resourceBundles.get(contextLocale);

        if (resourceBundle == null) {
            Class<?> resultCodeClass = resultCode.getClass();

            do {
                String resourceBundleName = resultCodeClass.getName();

                logger.debug("Trying to load resource bundle: [{}]", resourceBundleName);

                try {
                    resourceBundle = ResourceBundleFactory.getBundle(resourceBundleName);
                } catch (MissingResourceException e) {
                    if (e.getCause() != null) {
                        logger.error(e.getCause().getMessage(), e.getCause());
                    } else {
                        logger.debug("Resource bundle not found: " + resourceBundleName);
                    }

                    resultCodeClass = resultCodeClass.getSuperclass();
                }
            } while (resourceBundle == null && resultCodeClass != null);

            resourceBundles.put(contextLocale, resourceBundle);
        }

        return resourceBundle;
    }

    /**
     * 一个结果集<code>Result</code>的键值对
     * 
     * @author <a href="indra@ixiaopu.com">chenxi</a>
     * @version create on 2014-12-9 下午7:39:30
     */
    static class ResultEntry extends Entity<ResultEntry> implements Entryable<Class<ResultCode>, Integer> {

        /**
		 * 
		 */
        private static final long serialVersionUID = 1343805296854044269L;

        /**
         * 类型
         */
        private Class<ResultCode> type;

        /**
         * code值
         */
        private int code;

        public ResultEntry(Class<ResultCode> type, int code) {
            this.type = type;
            this.code = code;
        }

        @Override
        public Class<ResultCode> getKey() {
            return type;
        }

        @Override
        public Integer getValue() {
            return code;
        }

        @Override
        protected Object hashKey() {
            return type.hashCode() + 31 * code;
        }

        @Override
        protected boolean isEquals(ResultEntry other) {
            return this.type == other.type && this.code == other.code;
        }

    }

    private static final Computable<ResultEntry, ResultCode> resultCache = ConcurrentCache.createComputable();

    public static <T extends ResultCode> ResultCode getResultCode(final T[] resultCodes, final int code, Class<T> type) {
        @SuppressWarnings("unchecked")
        Class<ResultCode> clazz = (Class<com.ouer.solar.biz.result.ResultCode>) type;

        return resultCache.get(new ResultEntry(clazz, code), new Callable<ResultCode>() {

            @Override
            public ResultCode call() throws Exception {
                for (T resultCode : resultCodes) {
                    if (resultCode.getCode() == code) {
                        return resultCode;
                    }
                }

                return null;
            }
        });

    }
}