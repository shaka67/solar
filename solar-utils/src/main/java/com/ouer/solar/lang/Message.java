/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.io.Serializable;

import com.ouer.solar.able.Identifiable;

/**
 * 一个消息体
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-2-6 下午6:05:48
 */
public class Message<T> extends DefaultProperty implements Identifiable<String>, Serializable {

    private static final long serialVersionUID = -7115139569553613570L;

    private static final UUID UID = new UUID();

    /**
     * 唯一标识
     */
    protected String uid;

    /**
     * 消息体内容
     */
    protected T body;

    public Message() {
        uid = UID.nextID();
    }

    public Message(T body) {
        uid = UID.nextID();
        this.body = body;
    }

    @Override
    public String getIdentification() {
        return uid;
    }

    @Override
    public void setIdentification(String uid) {
        this.uid = uid;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
