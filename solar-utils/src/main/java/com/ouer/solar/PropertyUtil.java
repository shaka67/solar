/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.ouer.solar.io.StreamUtil;
import com.ouer.solar.logger.Logger;
import com.ouer.solar.logger.LoggerFactory;

/**
 * 有关<code>Properties</code>的工具类。
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月23日 下午3:03:49
 */
public abstract class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * 把properties文件key-value信息转换为Map，如果key中含有下换线转换为驼峰风格
     * <p>
     * 如果<code>resourceName</code>为<code>null</code>，则直接返回<code>null</code>
     * 
     * @param resourceName 资源文件
     * @return Map @see Map
     * @throws IOException
     */
    public static Map<String, String> loadProperties(String resourceName) {
        if (resourceName == null) {
            return null;
        }

        Map<String, String> paramsMap = Collections.synchronizedMap(new CamelCasingHashMap<String>());
        try {
            Properties properties = loadAllProperties(resourceName, PropertyUtil.class.getClassLoader());
            for (Enumeration<?> keys = properties.keys(); keys.hasMoreElements();) {
                String key = (String) keys.nextElement();
                paramsMap.put(key, properties.getProperty(key).trim());
            }
        } catch (IOException e) {
            logger.error("load {} error", e, resourceName);
        }

        return paramsMap;
    }

    /**
     * 加载资源文件所有属性
     * <p>
     * 如果<code>resourceName</code>为<code>null</code>，则直接返回<code>null</code>
     * 
     * @param resourceName resourceName 资源文件
     * @param classLoader 类装载器 @see ClassLoader
     * @return Properties @see Properties
     * @throws IOException
     */
    public static Properties loadAllProperties(String resourceName, ClassLoader classLoader) throws IOException {
        if (resourceName == null) {
            return null;
        }
        URL[] urls = ClassLoaderUtil.getResources(resourceName, classLoader);

        Properties properties = new Properties();
        for (URL url : urls) {
            InputStream input = null;
            try {
                URLConnection con = url.openConnection();
                input = con.getInputStream();
                // // 不使用URL缓存
                // con.setUseCaches(false);
                properties.load(input);
            } finally {
                StreamUtil.close(input);
            }
        }

        return properties;
    }

    public static Properties loadAllProperties(String resourceName) throws IOException {
        return loadAllProperties(resourceName, Thread.currentThread().getContextClassLoader());
    }

    /**
     * 扩展<code>HashMap</code>，将<code>key</code>驼峰化
     * 
     * @author <a href="indra@ixiaopu.com">chenxi</a>
     * @version create on 2014-11-11 下午9:11:37
     */
    private static class CamelCasingHashMap<V> extends HashMap<String, V> {

        /**
		 * 
		 */
        private static final long serialVersionUID = -4594560378634270655L;

        private boolean camel;

        public CamelCasingHashMap() {
            this(false);
        }

        public CamelCasingHashMap(boolean camel) {
            this.camel = camel;
        }

        /**
         * 是否包含<code>key</code>， {@link #HashMap.containsKey(Object)}
         * 
         * @param key 查找键
         * @return 如果存在返回<code>true</code>，否则<code>true</code>
         */
        @Override
		public boolean containsKey(Object key) {
            return super.containsKey(camel ? StringUtil.toCamelCase(key.toString()) : key);
        }

        /**
         * 返回键为<code>key</code>的值， {@link #HashMap.get(Object)}
         * 
         * @return 如果存在返回<code>true</code>，否则<code>true</code>
         */
        @Override
		public V get(Object key) {
            V value = super.get(camel ? StringUtil.toCamelCase(key.toString()) : key);
            if (value == null) {
                String error = format("Get property value failed.the value of '%s' is null.", key);
                throw new IllegalStateException(error);
            }
            return value;
        }

        /**
         * 存入键值对， {@link #HashMap.put(String, Object)}
         * 
         * @param key key with which the specified value is to be associated
         * @param value value to be associated with the specified key
         * @return the previous value associated with <tt>key</tt>, or <tt>null</tt> if there was no mapping for
         *         <tt>key</tt>. (A <tt>null</tt> return can also indicate that the map previously associated
         *         <tt>null</tt> with <tt>key</tt>.)
         */
        @Override
		public V put(String key, V value) {
            return super.put(camel ? StringUtil.toCamelCase(key.toString()) : key, value);
        }

        /**
         * 删除键为<code>key</code>的数据， {@link #HashMap.remove(Object)}
         * 
         * @param key key whose mapping is to be removed from the map
         * @return the previous value associated with <tt>key</tt>, or <tt>null</tt> if there was no mapping for
         *         <tt>key</tt>. (A <tt>null</tt> return can also indicate that the map previously associated
         *         <tt>null</tt> with <tt>key</tt>.)
         */
        @Override
		public V remove(Object key) {
            return super.remove(camel ? StringUtil.toCamelCase(key.toString()) : key);
        }

    }

}
