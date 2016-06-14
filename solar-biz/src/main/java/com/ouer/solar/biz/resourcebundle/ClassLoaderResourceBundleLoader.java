/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.resourcebundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import com.ouer.solar.ClassLoaderUtil;

/**
 * 通过<code>ClassLoader</code>装入resource bundle的数据.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:12:53
 */

public class ClassLoaderResourceBundleLoader implements ResourceBundleLoader {
    private ClassLoader classLoader;

    /**
     * 创建新loader, 使用当前线程的context class loader.
     */
    public ClassLoaderResourceBundleLoader() {
        this(null);
    }

    /**
     * 创建新loader, 使用指定的class loader.
     * 
     * @param classLoader 指定的class loader
     */
    public ClassLoaderResourceBundleLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            this.classLoader = ClassLoaderUtil.getContextClassLoader();

            if (this.classLoader == null) {
                this.classLoader = ClassLoader.getSystemClassLoader();
            }
        } else {
            this.classLoader = classLoader;
        }
    }

    /**
     * 根据指定的bundle名称, 取得输入流.
     * 
     * @param bundleFilename 要查找的bundle名
     * 
     * @return bundle的数据流, 如果指定bundle文件不存在, 则返回<code>null</code>
     * 
     * @throws ResourceBundleCreateException 如果文件存在, 但读取数据流失败
     */
    @Override
	public InputStream openStream(final String bundleFilename) throws ResourceBundleCreateException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() {
                @Override
				public InputStream run() throws ResourceBundleCreateException {
                    URL url = classLoader.getResource(bundleFilename);

                    // 如果资源不存在, 则返回null
                    if (url == null) {
                        return null;
                    }

                    try {
                        return url.openStream();
                    } catch (IOException e) {
                        throw new ResourceBundleCreateException(ResourceBundleConstant.RB_FAILED_OPENING_STREAM,
                                new Object[] { bundleFilename }, e);
                    }
                }
            });
        } catch (PrivilegedActionException e) {
            throw (ResourceBundleCreateException) e.getException();
        }
    }

    /**
     * 判断两个<code>ResourceBundleLoader</code>是否等效. 这将作为 <code>ResourceBundle</code>的cache的依据. 具有相同的context class loader的
     * <code>ResourceBundleLoader</code>是等效的.
     * 
     * @param obj 要比较的另一个对象
     * 
     * @return 如果等效, 则返回<code>true</code>
     */
    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!ClassLoaderResourceBundleLoader.class.isInstance(obj)) {
            return false;
        }

        return ((ClassLoaderResourceBundleLoader) obj).classLoader == classLoader;
    }

    /**
     * 取得hash值. 等效的<code>ResourceBundleLoader</code>应该具有相同的hash值.
     * 
     * @return hash值
     */
    @Override
	public int hashCode() {
        return (classLoader == null) ? 0 : classLoader.hashCode();
    }
}
