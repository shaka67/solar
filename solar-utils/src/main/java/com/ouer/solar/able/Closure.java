/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 仿闭包，接口中的 {@link #execute(Object...)} 通过回调模拟闭包。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月15日 下午2:12:44
 */
public interface Closure {
    /**
     * Performs an action on the specified input object.
     * 
     * @param input the input to execute on
     */
    public void execute(Object...input);
}
