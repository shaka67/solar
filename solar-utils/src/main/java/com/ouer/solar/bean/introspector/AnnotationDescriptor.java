/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ouer.solar.lang.StringableSupport;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年11月5日 下午3:40:36
 */
public class AnnotationDescriptor extends StringableSupport {

    private final Annotation annotation;

    private final Class<? extends Annotation> annotationType;

    private final ElementType[] elementTypes;

    private final RetentionPolicy policy;

    private final boolean isDocumented;

    private final boolean isInherited;

    public <A extends Annotation> AnnotationDescriptor(A annotation) {
        this.annotation = annotation;
        annotationType = annotation.annotationType();

        Target target = annotationType.getAnnotation(Target.class);
        elementTypes = (target == null) ? ElementType.values() : target.value();

        Retention retention = annotationType.getAnnotation(Retention.class);
        policy = (retention == null) ? RetentionPolicy.CLASS : retention.value();

        Documented documented = annotationType.getAnnotation(Documented.class);
        isDocumented = (documented != null);

        Inherited inherited = annotationType.getAnnotation(Inherited.class);
        isInherited = (inherited != null);
    }

    @SuppressWarnings("unchecked")
    public <A extends Annotation> A getAnnotation() {
        return (A) annotation;
    }

    public Class<? extends Annotation> getAnnotationType() {
        return annotationType;
    }

    public ElementType[] getElementTypes() {
        return elementTypes;
    }

    public RetentionPolicy getPolicy() {
        return policy;
    }

    public boolean isDocumented() {
        return isDocumented;
    }

    public boolean isInherited() {
        return isInherited;
    }

}
