/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.ObjectUtil;
import com.ouer.solar.ReflectionUtil;
import com.ouer.solar.lang.StringableSupport;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 上午1:57:43
 */
public class Fields extends StringableSupport {

    protected final ClassDescriptor classDescriptor;
    protected final Map<String, FieldDescriptor> fieldsMap;

    // cache
    private FieldDescriptor[] allFields;

    public Fields(ClassDescriptor classDescriptor) {
        this.classDescriptor = classDescriptor;
        this.fieldsMap = inspectFields();
    }

    protected Map<String, FieldDescriptor> inspectFields() {
        boolean scanAccessible = classDescriptor.isScanAccessible();
        Class<?> type = classDescriptor.getType();

        Field[] fields =
                scanAccessible ? ReflectionUtil.getAccessibleFields(type) : ReflectionUtil.getAllFieldsOfClass(type);

        Map<String, FieldDescriptor> map = CollectionUtil.createHashMap(fields.length);

        for (Field field : fields) {
            String fieldName = field.getName();

            if (fieldName.equals(ObjectUtil.SERIAL_VERSION_UID)) {
                continue;
            }

            map.put(fieldName, createFieldDescriptor(field));
        }

        return map;
    }

    protected FieldDescriptor createFieldDescriptor(Field field) {
        return new FieldDescriptor(classDescriptor, field);
    }

    public FieldDescriptor getFieldDescriptor(String name) {
        return fieldsMap.get(name);
    }

    public FieldDescriptor[] getAllFieldDescriptors() {
        if (allFields == null) {
            FieldDescriptor[] allFields = new FieldDescriptor[fieldsMap.size()];

            int index = 0;
            for (FieldDescriptor fieldDescriptor : fieldsMap.values()) {
                allFields[index] = fieldDescriptor;
                index++;
            }

            Arrays.sort(allFields, new Comparator<FieldDescriptor>() {
                @Override
				public int compare(FieldDescriptor fd1, FieldDescriptor fd2) {
                    return fd1.getField().getName().compareTo(fd2.getField().getName());
                }
            });

            this.allFields = allFields;
        }

        return allFields;
    }

}