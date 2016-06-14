/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.sample;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年11月18日 下午9:04:49
 */
public class BeanSampleB extends BeanSampleA {

    public static final long serialVersionUID = 42L;

    private Long boo;

    Long getBoo() {
        return boo;
    }

    void setBoo(Long boo) {
        this.boo = boo;
    }
}
