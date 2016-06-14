/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.resourcebundle.xml;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.ouer.solar.StringUtil;
import com.ouer.solar.biz.resourcebundle.AbstractResourceBundle;
import com.ouer.solar.biz.resourcebundle.AbstractResourceBundleFactory;
import com.ouer.solar.biz.resourcebundle.ResourceBundleConstant;
import com.ouer.solar.biz.resourcebundle.ResourceBundleCreateException;
import com.ouer.solar.biz.resourcebundle.ResourceBundleFactory;
import com.ouer.solar.biz.resourcebundle.ResourceBundleLoader;
import com.ouer.solar.biz.resourcebundle.proxy.AutoReloadResourceBundleProxy;
import com.ouer.solar.biz.resourcebundle.proxy.ConvertResourceBundleProxy;
import com.ouer.solar.i18n.CharConverter;

/**
 * 从XML文件中创建<code>ResourceBundle</code>的实例的工厂.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:40:18
 */

public class XMLResourceBundleFactory extends AbstractResourceBundleFactory {
    /**
     * 创建factory, 使用当前线程的context class loader作为bundle装入器.
     */
    public XMLResourceBundleFactory() {
        super();
    }

    /**
     * 创建factory, 使用指定的class loader作为bundle装入器.
     * 
     * @param classLoader 装入bundle的class loader
     */
    public XMLResourceBundleFactory(ClassLoader classLoader) {
        super(classLoader);
    }

    /**
     * 创建factory, 使用指定的loader作为bundle装器
     * 
     * @param loader bundle装入器
     */
    public XMLResourceBundleFactory(ResourceBundleLoader loader) {
        super(loader);
    }

    /**
     * 根据bundle的名称取得resource的文件名称.
     * 
     * @param bundleName bundle的名称
     * 
     * @return resource的名称
     */
    @Override
	protected String getFilename(String bundleName) {
        return super.getFilename(bundleName) + ResourceBundleConstant.RB_RESOURCE_EXT_XML;
    }

    /**
     * 以XML格式解析输入流, 并创建<code>ResourceBundle</code>.
     * 
     * @param stream 输入流
     * @param systemId 标志输入流的字符串
     * 
     * @return resource bundle
     * 
     * @throws ResourceBundleCreateException 如果解析失败
     */
    @Override
	protected AbstractResourceBundle parse(InputStream stream, String systemId) throws ResourceBundleCreateException {
        try {
            AbstractResourceBundle resourceBundle;
            SAXReader reader = new SAXReader();
            Document doc = reader.read(stream, systemId);
            String reference = (String) doc.selectObject(ResourceBundleConstant.XPATH_REFERENCE);
            if (StringUtil.isNotEmpty(reference)) {// 有引用项
                if (logger.isDebugEnabled()) {
                    logger.debug("XMLResourceBundle " + systemId + " reference to:" + reference);
                }
                String charConverter = (String) doc.selectObject(ResourceBundleConstant.XPATH_CHAR_CONVERTER);
                if (StringUtil.isNotEmpty(charConverter)) {// 使用自动字符转换
                    if (logger.isDebugEnabled()) {
                        logger.debug("XMLResourceBundle " + systemId + " auto charConverter:" + charConverter);
                    }
                    resourceBundle = createBundle(reference);
                    return new ConvertResourceBundleProxy(resourceBundle, CharConverter.getInstance(charConverter));
                }
                // 无需自动字符转换
                resourceBundle = createBundle(reference);
                return resourceBundle;
            }
            resourceBundle = new XMLResourceBundle(doc);
            // 判断是否需要自动重加载
            if (ResourceBundleFactory.isAutoReload()) {
                String bundleName =
                        systemId.substring(0, systemId.length() - ResourceBundleConstant.RB_RESOURCE_EXT_XML.length());
                resourceBundle =
                        new AutoReloadResourceBundleProxy(resourceBundle, this, bundleName,
                                ResourceBundleFactory.getCheckModifyInterval());
            }
            return resourceBundle;
        } catch (DocumentException e) {
            throw new ResourceBundleCreateException(ResourceBundleConstant.RB_FAILED_READING_XML_DOCUMENT,
                    new Object[] { systemId }, e);
        }
    }
}
