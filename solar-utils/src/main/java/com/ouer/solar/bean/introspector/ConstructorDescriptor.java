/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

import java.lang.reflect.Constructor;

import com.ouer.solar.ReflectionUtil;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月18日 下午8:24:16
 */
public class ConstructorDescriptor extends Descriptor {

    protected final Constructor<?> constructor;
    protected final Class<?>[] parameters;

    public ConstructorDescriptor(ClassDescriptor classDescriptor, Constructor<?> constructor) {
        super(classDescriptor, ReflectionUtil.isPublic(constructor));
        this.constructor = constructor;
        this.parameters = constructor.getParameterTypes();

        annotations = new Annotations(constructor);

        ReflectionUtil.forceAccess(constructor);
    }

    @Override
    public String getName() {
        return constructor.getName();
    }

    public Class<?> getDeclaringClass() {
        return constructor.getDeclaringClass();
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public Class<?>[] getParameters() {
        return parameters;
    }

    public boolean isDefault() {
        return parameters.length == 0;
    }

}