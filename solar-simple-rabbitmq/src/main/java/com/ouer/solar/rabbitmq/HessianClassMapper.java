/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import java.util.Map;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.ClassMapper;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * Build class mapping relationship into {@link MessageProperties}
 * with classid field name (__TypeId__).
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class HessianClassMapper implements ClassMapper {

	private static final String DEFAULT_CLASSID_FIELD_NAME = "__TypeId__";
	
    private final Map<String, Class<?>> id2class;
    private final Map<Class<?>, String> class2id;
    
    public HessianClassMapper()
    {
        id2class = Maps.newHashMap();
        class2id = Maps.newHashMap();
    }

    @Override
    public void fromClass(Class<?> clazz, MessageProperties properties)
    {
        String classId = class2id.get(clazz);
        if (classId == null) {
            classId = clazz.getName();
            class2id.put(clazz, classId);
        }
        properties.getHeaders().put(DEFAULT_CLASSID_FIELD_NAME, classId);
    }

    @Override
    public Class<?> toClass(MessageProperties properties)
    {
        final Map<String, Object> headers = properties.getHeaders();
        final Object classId = headers.get(DEFAULT_CLASSID_FIELD_NAME);
        Preconditions.checkState(classId != null,
                        "failed to convert Message content. Could not resolve " + DEFAULT_CLASSID_FIELD_NAME+ " in header");
        Class<?> clazz = id2class.get(classId);
        if (clazz == null) {
            try {
                clazz = Class.forName(classId.toString());
            } catch (final Exception e) {
                throw new ClassCastException("failed to resolve class name [" + classId + "]");
            }
            id2class.put(classId.toString(), clazz);
        }
        return clazz;
    }

}