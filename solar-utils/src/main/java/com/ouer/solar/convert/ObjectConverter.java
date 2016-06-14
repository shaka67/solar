/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ouer.solar.ClassLoaderUtil;
import com.ouer.solar.CollectionUtil;
import com.ouer.solar.PackageUtil;
import com.ouer.solar.convert.TypeConverter.Convert;
import com.ouer.solar.logger.CachedLogger;
import com.ouer.solar.logger.Logger;
import com.ouer.solar.logger.LoggerFactory;

/**
 * FIXME
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月29日 上午1:20:19
 */
@TypeConverter.Convert
public class ObjectConverter<T> extends CachedLogger {

    private static Logger LOGGER = LoggerFactory.getLogger(ObjectConverter.class);

    protected static Map<Class<?>, TypeConverter<?>> typeConverters = CollectionUtil.createHashMap();

    private static final ObjectConverter<Object> instance = createInstance();

    static {
        try {
            init();
        } catch (Exception e) {
            LOGGER.error("clinit error", e);
        }
    }

    static <T> ObjectConverter<T> createInstance() {
        return new ObjectConverter<T>();
    }

    public static ObjectConverter<Object> getInstance() {
        return instance;
    }

    private static void init() throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        // FIXME
        String packName = PackageUtil.getPackage(ObjectConverter.class).getName() + ".*";
        List<String> classNames = PackageUtil.getClassesInPackage(packName);

        for (String classname : classNames) {
            Class<?> clazz = ClassLoaderUtil.loadClass(classname);
            if (clazz.getAnnotation(Convert.class) != null) {
                clazz.newInstance();
            }
        }
    }

    public T toConvert(String value, Class<?> clazz) {
        if (value == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        TypeConverter<T> converter = (TypeConverter<T>) typeConverters.get(clazz);

        return (converter == null) ? null : converter.toConvert(value);
    }

    public String fromConvert(T value) {
        if (value == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        TypeConverter<T> converter = (TypeConverter<T>) typeConverters.get(value.getClass());

        return (converter == null) ? null : converter.fromConvert(value);
    }

    protected void register(Class<T> clazz) {
        // FIXME
        typeConverters.put(clazz, (TypeConverter<?>) this);
    }

    public T toConvert(Object value, Class<?> clazz) {
        if (value == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        TypeConverter<T> converter = (TypeConverter<T>) typeConverters.get(clazz);

        return (converter == null) ? null : converter.toConvert(value);
    }

}
