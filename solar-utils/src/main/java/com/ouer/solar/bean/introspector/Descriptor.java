/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

import java.lang.annotation.Annotation;

import com.ouer.solar.lang.StringableSupport;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月18日 下午8:32:16
 */
public abstract class Descriptor extends StringableSupport {

    protected final ClassDescriptor classDescriptor;
    protected final boolean isPublic;

    protected Annotations annotations;

    protected Descriptor(ClassDescriptor classDescriptor, boolean isPublic) {
        this.classDescriptor = classDescriptor;
        this.isPublic = isPublic;
    }

    public ClassDescriptor getClassDescriptor() {
        return classDescriptor;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public boolean matchDeclared(boolean declared) {
        if (!declared) {
            return isPublic;
        }

        return true;
    }

    protected Annotations getAnnotations() {
        return annotations;
    }

    public AnnotationDescriptor getAnnotationDescriptor(Class<? extends Annotation> clazz) {
        return annotations.getAnnotationDescriptor(clazz);
    }

    public <A extends Annotation> A getAnnotation(Class<A> clazz) {
        AnnotationDescriptor annotationDescriptor = annotations.getAnnotationDescriptor(clazz);
        if (annotationDescriptor == null) {
            return null;
        }

        return annotationDescriptor.getAnnotation();
    }

    public AnnotationDescriptor[] getAllAnnotationDescriptors() {
        return annotations.getAllAnnotationDescriptors();
    }

    public abstract String getName();

}