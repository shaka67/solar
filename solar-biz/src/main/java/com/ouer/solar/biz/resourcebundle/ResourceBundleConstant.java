/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz.resourcebundle;

import com.ouer.solar.StringUtil;

/**
 * 定义<code>ResourceBundle</code>相关的常量和错误信息.
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:15:22
 */

public interface ResourceBundleConstant {
    // ResourceBundle类的常量.
    int INITIAL_CACHE_SIZE = 25;
    float CACHE_LOAD_FACTOR = 1.0f;
    int MAX_BUNDLES_SEARCHED = 3;

    // ResourceBundle类的错误信息.
    String RB_BASE_NAME_IS_NULL = "The basename of the resource bundle should not be null";
    String RB_MISSING_RESOURCE_BUNDLE = "Could not find bundle for base name \"{0}\", locale \"{1}\"";
    String RB_FAILED_LOADING_RESOURCE_BUNDLE = "Failed to load bundle for base name \"{0}\", locale \"{1}\"";
    String RB_RESOURCE_NOT_FOUND = "Could not find resource for bundle \"{0}\", key \"{1}\"";
    String RB_CLONE_NOT_SUPPORTED = "Clone is not supported by class \"{0}\"";
    String RB_BASE_NAME_LONGER_THAN_BUNDLE_NAME = "The basename \"{0}\" is longer than the bundle name \"{1}\"";
    String RB_FAILED_OPENING_STREAM = "Could not open stream for resource \"{0}\"";

    // XMLResourceBundle类的resource类型
    String RB_RESOURCE_TYPE_MESSAGE = "message";
    String RB_RESOURCE_TYPE_MAP = "map";
    String RB_RESOURCE_TYPE_LIST = "list";

    // XMLResourceBundle的文件名后缀
    String RB_RESOURCE_EXT_XML = ".xml";
    String RB_RESOURCE_EXT_PROPERTIES = ".properties";

    // XMLResourceBundle类的错误信息
    String RB_FAILED_READING_XML_DOCUMENT = "Failed to read XML document \"{0}\"";
    String RB_DUPLICATED_RESOURCE_KEY = "Duplicated resource key \"{0}\"";
    String RB_DUPLICATED_MAP_RESOURCE_KEY = "Duplicated mapped resource key \"{0}\" for resource \"{1}\"";
    
    // PropertiesResourceBundle类的错误信息
    String RB_FAILED_READING_PROPERTIES_DOCUMENT = "Failed to read properties document \"{0}\"";

    // XMLResourceBundle的XPATH常量.
    String XPATH_REFERENCE = "string(/resource-bundle/@reference)";
    String XPATH_CHAR_CONVERTER = "string(/resource-bundle/@charConverter)";
    String XPATH_GROUPS = "/resource-bundle/group";
    String XPATH_UNGROUPED_RESOURCES = "/resource-bundle/message | /resource-bundle/map | /resource-bundle/list";
    String XPATH_RESOURCES = "message | map | list";
    String XPATH_RESOURCE_ID = "string(@id)";
    String XPATH_RESOURCE_MESSAGE_DATA = "normalize-space(data)";
    String XPATH_RESOURCE_MESSAGE_CODE = "normalize-space(code)";
    String XPATH_RESOURCE_MESSAGE_SPACE = "normalize-space";
    // code后缀
    String XPATH_RESOURCE_CODE_SUFFIX = StringUtil.substring(XPATH_RESOURCE_MESSAGE_CODE,
            XPATH_RESOURCE_MESSAGE_SPACE.length());
}
