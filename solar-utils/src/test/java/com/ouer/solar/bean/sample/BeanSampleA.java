/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.sample;

import com.ouer.solar.lang.StringableSupport;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年11月18日 下午9:04:44
 */
public class BeanSampleA extends StringableSupport {

    protected Integer shared;

    private String fooProp = "abean_value";

    public void setFooProp(String v) {
        fooProp = v;
    }

    public String getFooProp() {
        return fooProp;
    }

    public boolean isSomething() {
        return true;
    }
}
