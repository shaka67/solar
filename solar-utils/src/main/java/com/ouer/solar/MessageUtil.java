/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 和<code>ResourceBundle</code>及消息字符串有关的工具类。
 * 
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月20日 上午6:00:56
 */
public abstract class MessageUtil implements StringPool.Symbol {
    /**
     * 从<code>ResourceBundle</code>中取得字符串，并使用<code>MessageFormat</code>格式化字符串.
     * 
     * @param bundle resource bundle
     * @param key 要查找的键
     * @param params 参数表
     * @return key对应的字符串，如果key为<code>null</code>或resource bundle为 <code>null</code>，或resource key未找到，则返回<code>key</code>
     */
    public static String getMessage(ResourceBundle bundle, String key, Object...params) {
        if (bundle == null || key == null) {
            return key;
        }

        try {
            return formatMessage(bundle.getString(key), params);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    /**
     * 使用<code>MessageFormat</code>格式化字符串.
     * 
     * @param message 要格式化的字符串
     * @param params 参数表
     * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
     */
    public static String formatMessage(String message, Object...params) {
        if (message == null || ArrayUtil.isEmpty(params)) {
            return message;
        }

        return MessageFormat.format(message, params);
    }

    /**
     * 采用日志型来格式化字符串.
     * 
     * <pre>
     * MessageUtil.formatLogMessage(&quot;xxxx {} yyy {}&quot;),111,222) = &quot;xxxx 111 yyy 222&quot;;
     * </pre>
     * 
     * @param message 要格式化的字符串
     * @param params 参数表
     * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
     */
    public static String formatLogMessage(String message, Object...params) {
        return formatLogMessage(message, BRACE, params);
    }

    /**
     * 采用日志型来格式化字符串.
     * 
     * <pre>
     * MessageUtil.formatLogMessage(&quot;xxxx {} yyy {}&quot;),{}, 111,222) = &quot;xxxx 111 yyy 222&quot;;
     * </pre>
     * 
     * @param message 要格式化的字符串
     * @param flag 占位符，占位符若为“空”，直接返回<code>message</code>
     * @param params 参数表
     * @return 格式化的字符串，如果message为<code>null</code>，则返回<code>null</code>
     */
    public static String formatLogMessage(String message, String flag, Object...params) {
        if (StringUtil.isBlank(flag)) {
            return message;
        }

        int braceCount = StringUtil.countMatches(message, flag);

        String logMessage = message;
        for (int i = 0; i < braceCount; i++) {
            logMessage = StringUtil.replaceOnce(logMessage, flag, "{" + i + "}");
        }

        return formatMessage(logMessage, params);
    }
}
