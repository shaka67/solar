/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bo;

import java.io.Serializable;

import com.ouer.solar.lang.StringableSupport;

/**
 * 使用GenericDao做映射的对象，必须都继承与这个类
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */
public class BaseObject<KEY extends Serializable> extends StringableSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    public BaseObject() {
    }

    /**
     * 主键字段
     */
    protected KEY id;

    public KEY getId() {
        return id;
    }

    public void setId(KEY id) {
        this.id = id;
    }

}
