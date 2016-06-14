/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 消息接收
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月14日 下午6:47:15
 */
public interface Receiver<T> {

    /**
     * 接受消息，一般用于异步或远程通信
     * 
     * @param msg 接收信息附带对象
     */
    void messageReceived(T msg);
}
