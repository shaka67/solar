/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ouer.solar.ArrayUtil;
import com.ouer.solar.CollectionUtil;
import com.ouer.solar.CsvUtil;
import com.ouer.solar.convert.ConverterManagerBean;
import com.ouer.solar.convert.TypeConverter;

/**
 * FIXME
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午8:34:59
 */
public class ArrayConverter<T> implements TypeConverter<T[]> {

    static final char[] NUMBER_DELIMITERS = new char[] { ',', ';', '\n' };

    protected final ConverterManagerBean converterManagerBean;
    protected Class<T> targetComponentType;

    protected ArrayConverter() {
        this.converterManagerBean = ConverterManagerBean.getInstance();
    }

    public ArrayConverter(Class<T> targetComponentType) {
        this.converterManagerBean = ConverterManagerBean.getInstance();
        this.targetComponentType = targetComponentType;
    }

    public ArrayConverter(ConverterManagerBean converterManagerBean, Class<T> targetComponentType) {
        this.converterManagerBean = converterManagerBean;
        this.targetComponentType = targetComponentType;
    }

    @SuppressWarnings("unchecked")
    protected void register(Class<T[]> type) {
        converterManagerBean.register(type, this);
        this.targetComponentType = (Class<T>) type.getComponentType();
    }

    protected T convertType(Object value) {
        return converterManagerBean.convertType(value, targetComponentType);
    }

    @SuppressWarnings("unchecked")
    protected T[] createArray(int length) {
        return (T[]) Array.newInstance(targetComponentType, length);
    }

    protected T[] convertToSingleElementArray(Object value) {
        T[] singleElementArray = createArray(1);

        singleElementArray[0] = convertType(value);
        // singleElementArray[0] = (T) value;

        return singleElementArray;
    }

    // FIXME
    protected T[] convertValueToArray(Object value) {
        if (value instanceof List) {
            List<?> list = (List<?>) value;
            T[] target = createArray(list.size());

            for (int i = 0; i < list.size(); i++) {
                Object element = list.get(i);
                target[i] = convertType(element);
            }

            return target;
        }

        if (value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            T[] target = createArray(collection.size());

            int i = 0;
            for (Object element : collection) {
                target[i] = convertType(element);
                i++;
            }

            return target;
        }

        if (value instanceof Iterable) {
            Iterable<?> iterable = (Iterable<?>) value;
            List<T> list = CollectionUtil.createArrayList();

            for (Object element : iterable) {
                list.add(convertType(element));
            }

            T[] target = createArray(list.size());
            return list.toArray(target);
        }

        if (value instanceof CharSequence) {
            String[] strings = convertStringToArray(value.toString());
            return convertArrayToArray(strings);
        }

        return convertToSingleElementArray(value);
    }

    protected String[] convertStringToArray(String value) {
        return CsvUtil.toStringArray(value);
    }

    protected T[] convertArrayToArray(Object value) {
        Class<?> valueComponentType = value.getClass().getComponentType();

        if (valueComponentType == targetComponentType) {
            @SuppressWarnings("unchecked")
            T[] result = (T[]) value;
            return result;
        }

        T[] result;

        if (valueComponentType.isPrimitive()) {
            return convertPrimitiveArrayToArray(value, valueComponentType);
        }

        Object[] array = (Object[]) value;
        result = createArray(array.length);

        for (int i = 0; i < array.length; i++) {
            result[i] = convertType(array[i]);
        }

        return result;
    }

    protected T[] convertPrimitiveArrayToArray(Object value, Class<?> primitiveComponentType) {

        return ArrayUtil.primitiveToWrapper(value);

    }

    @Override
    public T[] toConvert(String value) {
        String[] strings = convertStringToArray(value);
        return convertArrayToArray(strings);
    }

    @Override
    public String fromConvert(T[] value) {
        return Arrays.toString(value);
    }

    @Override
    public T[] toConvert(Object value) {
        Class<?> valueClass = value.getClass();

        if (!valueClass.isArray()) {
            return convertValueToArray(value);
        }

        return convertArrayToArray(value);
    }

}