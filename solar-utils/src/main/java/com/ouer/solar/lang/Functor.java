/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.lang.reflect.Method;

import com.ouer.solar.ArrayUtil;
import com.ouer.solar.Assert;
import com.ouer.solar.Emptys;
import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.able.ClosureResult;

/**
 * 仿函数，使一个类的使用看上去象一个函数， 类中的 {@link #execute(Object...)} 通过方法回调模拟函数的行为。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月15日 下午1:46:57
 */
public class Functor implements ClosureResult<Object> {

    private Object target;
    private Method method;

    private Class<?>[] parameterTypes;

    private Object result;

    public Functor(Object target, String methodName) {
        this(target, methodName, Emptys.EMPTY_CLASS_ARRAY);
    }

    public Functor(Object target, String methodName, Class<?>...parameterTypes) {

        Assert.assertNotNull(target, "target obejct is not null.");

        this.target = target;
        this.parameterTypes = parameterTypes;
        method = ReflectionUtil.getMethod(target.getClass(), methodName, this.parameterTypes);

        Assert.assertNotNull(method, "method [" + target.getClass() + "." + methodName + "] !NOT! exist.");

    }

    @Override
    public void execute(Object...args) {
        result = ReflectionUtil.invokeMethod(method, target, args);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(this.target).append(".").append(this.method.getName())
                .append(ArrayUtil.toString(parameterTypes)).toString();
    }

    @Override
    public Object getResult() {
        return result;
    }
}
