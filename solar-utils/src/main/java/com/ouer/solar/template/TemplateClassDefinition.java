/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class TemplateClassDefinition {

    private final Class<?> templateClass;

	public TemplateClassDefinition(Class<?> templateClass) {
        Preconditions.checkArgument(templateClass != null);
        this.templateClass = templateClass;
    }

    public Class<?> getTemplateClass() {
        return templateClass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(templateClass);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }

        final TemplateClassDefinition another = (TemplateClassDefinition) obj;
        return Objects.equal(templateClass, another.templateClass);
    }

}
