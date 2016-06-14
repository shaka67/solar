/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP协议相关的工具类
 * 
 * <p>
 * 这个类中的每个方法都可以“安全”地处理<code>null</code>，而不会抛出<code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-3 下午5:36:51
 */
public abstract class HttpUtil {

    /**
     * URL <code>spec<code>是否连通，如果<code>spec<code>为“空”，则返回<code>false</code；如果测试结果返回<code>200</code>，则返回
     * <code>true</code>
     * 
     * @param spec 给定的<code>URL</code>路径
     * @return 是否连通，如果连通则返回<code>true</code>
     */
    public static boolean connected(String spec) {
        if (StringUtil.isBlank(spec)) {
            return false;
        }

        HttpURLConnection connection = null;
        try {
            URL url = new URL(spec);
            connection = (HttpURLConnection) url.openConnection();
            int code = connection.getResponseCode();

            connection.disconnect();
            return code == 200;
        } catch (IOException e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

}
