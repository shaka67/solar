/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.template;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class TemplateClassRegister {

	private final Multimap<Class<?>, Field> templateFields;
	private final Map<Field, String> fieldVariables;

    public TemplateClassRegister(Set<TemplateClassDefinition> definitions)
    {
        Preconditions.checkArgument(definitions != null);
        templateFields = HashMultimap.create();
        fieldVariables = Maps.newHashMap();
        for (final TemplateClassDefinition definition : definitions) {
            register(definition);
        }
    }

    void register(TemplateClassDefinition definition)
    {
        final Class<?> objClass = definition.getTemplateClass();

        final Field[] fields = objClass.getDeclaredFields();
        TemplateVariable annotation;
        String variable;
        for (final Field field : fields) {
        	if (!field.isAnnotationPresent(TemplateVariable.class)) {
				continue;
			}

        	annotation = field.getAnnotation(TemplateVariable.class);
        	variable = annotation.variable();
        	if (variable == null) {
				continue;
			}

        	templateFields.put(objClass, field);
        	fieldVariables.put(field, variable);
        }
    }

    public boolean isTemplateClass(Class<?> clazz) {
    	return templateFields.containsKey(clazz);
    }

    public Collection<Field> getTemplateFields(Class<?> clazz) {
    	return templateFields.get(clazz);
    }

    public String getFieldVariable(Field field) {
    	return fieldVariables.get(field);
    }

}
