/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.ouer.solar.ArrayUtil;
import com.ouer.solar.CollectionUtil;
import com.ouer.solar.CsvUtil;
import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ConverterManagerBean;
import com.ouer.solar.convert.TypeConverter;
import com.ouer.solar.logger.Logger;
import com.ouer.solar.logger.LoggerFactory;

/**
 * FIXME
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月26日 上午3:10:35
 */
public class CollectionConverter<T> implements TypeConverter<Collection<T>> {

    private static final Logger logger = LoggerFactory.getLogger(CollectionConverter.class);

    protected final ConverterManagerBean converterManagerBean;
    protected final Class<? extends Collection<?>> collectionType;
    protected final Class<T> targetComponentType;

    public CollectionConverter(Class<? extends Collection<?>> collectionType, Class<T> targetComponentType) {
        this(ConverterManagerBean.getInstance(), collectionType, targetComponentType);
    }

    public CollectionConverter(ConverterManagerBean converterManagerBean,
            Class<? extends Collection<?>> collectionType, Class<T> targetComponentType) {

        this.converterManagerBean = converterManagerBean;
        this.collectionType = collectionType;
        this.targetComponentType = targetComponentType;
    }

    protected T convertType(Object value) {
        return converterManagerBean.convertType(value, targetComponentType);
    }

    protected Collection<T> createCollection(int length) {
        if (collectionType.isInterface()) {
            if (collectionType.isAssignableFrom(List.class)) {
                if (length > 0) {
                    return CollectionUtil.createArrayList(length);
                }

                return CollectionUtil.createArrayList();
            }

            if (collectionType.isAssignableFrom(Set.class)) {
                if (length > 0) {
                    return CollectionUtil.createHashSet(length);
                }

                return CollectionUtil.createHashSet();
            }

            throw new ConvertException("Unknown collection: " + collectionType.getName());
        }
        if (length > 0) {
            try {
                @SuppressWarnings("unchecked")
                Constructor<Collection<T>> constructor =
                        (Constructor<Collection<T>>) collectionType.getConstructor(int.class);
                return constructor.newInstance(Integer.valueOf(length));
            } catch (Exception e) {
                logger.error("createCollection error", e);
            }
        }

        try {
            @SuppressWarnings("unchecked")
            Collection<T> collection = (Collection<T>) collectionType.newInstance();
            return collection;
        } catch (Exception e) {
            throw new ConvertException(e);
        }
    }

    protected Collection<T> convertToSingleElementCollection(Object value) {
        Collection<T> collection = createCollection(0);

        @SuppressWarnings("unchecked")
        T t = (T) value;
        collection.add(t);

        return collection;
    }

    protected Collection<T> convertValueToCollection(Object value) {
        if (value instanceof Iterable) {
            Iterable<?> iterable = (Iterable<?>) value;
            Collection<T> collection = createCollection(0);

            for (Object element : iterable) {
                collection.add(convertType(element));
            }
            return collection;
        }

        if (value instanceof CharSequence) {
            value = CsvUtil.toStringArray(value.toString());
        }

        Class<?> type = value.getClass();

        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();

            if (componentType.isPrimitive()) {
                return convertPrimitiveArrayToCollection(value);
            }

            Object[] array = (Object[]) value;
            Collection<T> result = createCollection(array.length);
            for (Object a : array) {
                result.add(convertType(a));
            }
            return result;
        }

        return convertToSingleElementCollection(value);
    }

    protected Collection<T> convertCollectionToCollection(Collection<?> value) {
        Collection<T> collection = createCollection(value.size());

        for (Object v : value) {
            collection.add(convertType(v));
        }

        return collection;
    }

    protected Collection<T> convertPrimitiveArrayToCollection(Object value) {
        Object[] array = ArrayUtil.primitiveToWrapper(value);

        Collection<T> result = createCollection(array.length);

        for (Object object : array) {
            result.add(convertType(object));
        }

        return result;
    }

    @Override
    public Collection<T> toConvert(String value) {
        T t = convertType(value);
        Collection<T> result = createCollection(1);
        result.add(t);

        return result;
    }

    @Override
    public String fromConvert(Collection<T> value) {
        return String.valueOf(value);
    }

    @Override
    public Collection<T> toConvert(Object value) {
        if (!(value instanceof Collection)) {
            return convertValueToCollection(value);
        }

        return convertCollectionToCollection((Collection<?>) value);
    }

}
