/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

/**
 * 方法描述
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2015-7-11 下午2:47:50
 */
public class MethodDesc extends StringableSupport {
    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法参数
     */
    private Object[] params;

    public MethodDesc() {

    }

    public MethodDesc(String methodName, Object...params) {
        this.methodName = methodName;
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

}
