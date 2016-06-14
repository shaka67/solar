/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ouer.solar.ClassUtil;
import com.ouer.solar.lang.StringableSupport;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月18日 下午8:18:20
 */
public class ClassDescriptor extends StringableSupport {

    protected final Class<?> type;
    protected final boolean scanAccessible;
    protected final boolean extendedProperties;
    protected final boolean includeFieldsAsProperties;
    protected final String propertyFieldPrefix;
    protected final Class<?>[] interfaces;
    protected final Class<?>[] superclasses;
    protected int usageCount;

    private final boolean isArray;
    private final boolean isMap;
    private final boolean isList;
    private final boolean isSet;
    private final boolean isCollection;
    private Fields fields;
    private Methods methods;
    private Properties properties;
    private Constructors constructors;

    private Annotations annotations;

    public ClassDescriptor(Class<?> type, boolean scanAccessible, boolean extendedProperties,
            boolean includeFieldsAsProperties, String propertyFieldPrefix) {
        this.type = type;
        this.scanAccessible = scanAccessible;
        this.extendedProperties = extendedProperties;
        this.includeFieldsAsProperties = includeFieldsAsProperties;
        this.propertyFieldPrefix = propertyFieldPrefix;

        isArray = type.isArray();
        isMap = ClassUtil.isAssignable(Map.class, type);
        isList = ClassUtil.isAssignable(List.class, type);
        isSet = ClassUtil.isAssignable(Set.class, type);
        isCollection = ClassUtil.isAssignable(Collection.class, type);

        interfaces = ClassUtil.getAllInterfacesAsArray(type);
        superclasses = ClassUtil.getAllSuperclassesAsArray(type);

        fields = new Fields(this);
        methods = new Methods(this);
        properties = new Properties(this);
        constructors = new Constructors(this);

        annotations = new Annotations(type);
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isScanAccessible() {
        return scanAccessible;
    }

    public boolean isExtendedProperties() {
        return extendedProperties;
    }

    public boolean isIncludeFieldsAsProperties() {
        return includeFieldsAsProperties;
    }

    public String getPropertyFieldPrefix() {
        return propertyFieldPrefix;
    }

    protected void increaseUsageCount() {
        usageCount++;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public boolean isArray() {
        return isArray;
    }

    public boolean isMap() {
        return isMap;
    }

    public boolean isList() {
        return isList;
    }

    public boolean isSet() {
        return isSet;
    }

    public boolean isCollection() {
        return isCollection;
    }

    protected Fields getFields() {
        return fields;
    }

    public FieldDescriptor getFieldDescriptor(String name, boolean declared) {
        FieldDescriptor fieldDescriptor = getFields().getFieldDescriptor(name);

        if (fieldDescriptor != null) {
            if (!fieldDescriptor.matchDeclared(declared)) {
                return null;
            }
        }

        return fieldDescriptor;
    }

    public FieldDescriptor[] getAllFieldDescriptors() {
        return getFields().getAllFieldDescriptors();
    }

    protected Methods getMethods() {
        return methods;
    }

    public MethodDescriptor getMethodDescriptor(String name, boolean declared) {
        MethodDescriptor methodDescriptor = getMethods().getMethodDescriptor(name);

        if ((methodDescriptor != null) && methodDescriptor.matchDeclared(declared)) {
            return methodDescriptor;
        }

        return methodDescriptor;
    }

    public MethodDescriptor getMethodDescriptor(String name, Class<?>[] params, boolean declared) {
        MethodDescriptor methodDescriptor = getMethods().getMethodDescriptor(name, params);

        if ((methodDescriptor != null) && methodDescriptor.matchDeclared(declared)) {
            return methodDescriptor;
        }

        return null;
    }

    public MethodDescriptor[] getAllMethodDescriptors(String name) {
        return getMethods().getAllMethodDescriptors(name);
    }

    public MethodDescriptor[] getAllMethodDescriptors() {
        return getMethods().getAllMethodDescriptors();
    }

    // ----------------------------------------------------------------
    // properties

    protected Properties getProperties() {
        return properties;
    }

    public PropertyDescriptor getPropertyDescriptor(String name, boolean declared) {
        PropertyDescriptor propertyDescriptor = getProperties().getPropertyDescriptor(name);

        if ((propertyDescriptor != null) && propertyDescriptor.matchDeclared(declared)) {
            return propertyDescriptor;
        }

        return null;
    }

    public PropertyDescriptor[] getAllPropertyDescriptors() {
        return getProperties().getAllPropertyDescriptors();
    }

    // ----------------------------------------------------------------
    // constructors

    protected Constructors getConstructors() {
        return constructors;
    }

    public ConstructorDescriptor getDefaultCtorDescriptor(boolean declared) {
        ConstructorDescriptor defaultConstructor = getConstructors().getDefaultCtor();

        if ((defaultConstructor != null) && defaultConstructor.matchDeclared(declared)) {
            return defaultConstructor;
        }
        return null;
    }

    public ConstructorDescriptor getConstructorDescriptor(Class<?>[] args, boolean declared) {
        ConstructorDescriptor constructorDescriptor = getConstructors().getCtorDescriptor(args);

        if ((constructorDescriptor != null) && constructorDescriptor.matchDeclared(declared)) {
            return constructorDescriptor;
        }
        return null;
    }

    public ConstructorDescriptor[] getAllConstructorDescriptors() {
        return getConstructors().getAllCtorDescriptors();
    }

    // ----------------------------------------------------------------
    // annotations

    protected Annotations getAnnotations() {
        return annotations;
    }

    public AnnotationDescriptor getAnnotationDescriptor(Class<? extends Annotation> clazz) {
        return annotations.getAnnotationDescriptor(clazz);
    }

    public AnnotationDescriptor[] getAllAnnotationDescriptors() {
        return annotations.getAllAnnotationDescriptors();
    }

    public Class<?>[] getAllInterfaces() {
        return interfaces;
    }

    public Class<?>[] getAllSuperclasses() {
        return superclasses;
    }
}