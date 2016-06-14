/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import com.ouer.solar.ReflectionUtil;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月18日 下午8:39:13
 */
public class FieldDescriptor extends Descriptor implements Getter, Setter {

    protected final Field field;
    protected final Type type;
    protected final Class<?> rawType;
    protected final Class<?> rawComponentType;
    protected final Class<?> rawKeyComponentType;

    public FieldDescriptor(ClassDescriptor classDescriptor, Field field) {
        super(classDescriptor, ReflectionUtil.isPublic(field));
        this.field = field;
        this.type = field.getGenericType();
        this.rawType = ReflectionUtil.getRawType(type, classDescriptor.getType());

        Class<?>[] componentTypes = ReflectionUtil.getComponentTypes(type, classDescriptor.getType());
        if (componentTypes != null) {
            this.rawComponentType = componentTypes[componentTypes.length - 1];
            this.rawKeyComponentType = componentTypes[0];
        } else {
            this.rawComponentType = null;
            this.rawKeyComponentType = null;
        }

        annotations = new Annotations(field);

        ReflectionUtil.forceAccess(field);
    }

    @Override
    public String getName() {
        return field.getName();
    }

    public Class<?> getDeclaringClass() {
        return field.getDeclaringClass();
    }

    public Field getField() {
        return field;
    }

    public Class<?> getRawType() {
        return rawType;
    }

    public Class<?> getRawComponentType() {
        return rawComponentType;
    }

    public Class<?> getRawKeyComponentType() {
        return rawKeyComponentType;
    }

    public Class<?>[] resolveRawComponentTypes() {
        return ReflectionUtil.getComponentTypes(type, classDescriptor.getType());
    }

    @Override
	public Object invokeGetter(Object target) throws InvocationTargetException, IllegalAccessException {
        return field.get(target);
    }

    @Override
	public Class<?> getGetterRawType() {
        return getRawType();
    }

    @Override
	public Class<?> getGetterRawComponentType() {
        return getRawComponentType();
    }

    @Override
	public Class<?> getGetterRawKeyComponentType() {
        return getRawKeyComponentType();
    }

    @Override
	public void invokeSetter(Object target, Object argument) throws IllegalAccessException {
        field.set(target, argument);
    }

    @Override
	public Class<?> getSetterRawType() {
        return getRawType();
    }

    @Override
	public Class<?> getSetterRawComponentType() {
        return getRawComponentType();
    }

    @Override
    public String toString() {
        return classDescriptor.getType().getSimpleName() + '#' + field.getName();
    }

}