/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.i18n;

import java.util.Map;

import com.ouer.solar.ClassLoaderUtil;
import com.ouer.solar.CollectionUtil;
import com.ouer.solar.exception.ClassInstantiationException;

/**
 * 代表一个字符转换器。例如：将简体中文转为繁体中文。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:42:06
 */
public abstract class CharConverter {
    /** 简体中文到繁体中文的转换器名称。 */
    public static final String SIMPLIFIED_TO_TRADITIONAL_CHINESE = "SimplifiedToTraditionalChinese";

    /** 繁体中文到简体中文的转换器名称。 */
    public static final String TRADITIONAL_TO_SIMPLIFIED_CHINESE = "TraditionalToSimplifiedChinese";

    // 私有变量
    private static final Map<String, CharConverter> converters = CollectionUtil.createConcurrentMap();

    /** 取得一个指定名称的转换器。 */
    public static final CharConverter getInstance(String name) {
        CharConverter converter = converters.get(name);

        if (converter == null) {
            CharConverterProvider provider;

            try {
                provider = (CharConverterProvider) ClassLoaderUtil.newServiceInstance("char.converter." + name);
            } catch (ClassInstantiationException e) {
                throw new IllegalArgumentException("Failed to load char converter provider: " + name + ": "
                        + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Failed to load char converter provider: " + name + ": "
                        + e.getMessage());
            }

            converter = provider.createCharConverter();
            converters.put(name, converter);
        }

        return converter;
    }

    /** 转换一个字符。 */
    public abstract char convert(char ch);

    /** 转换一个字符串。 */
    public String convert(CharSequence chars) {
        return convert(chars, 0, chars.length());
    }

    /** 转换一个字符串。 */
    public String convert(CharSequence chars, int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }

        if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        }

        int end = offset + count;

        if (end > chars.length()) {
            throw new StringIndexOutOfBoundsException(offset + count);
        }

        StringBuilder builder = new StringBuilder();

        for (int i = offset; i < end; i++) {
            builder.append(convert(chars.charAt(i)));
        }

        return builder.toString();
    }

    /** 转换一个字符数组，将结果写入原数组。 */
    public void convert(char[] chars) {
        convert(chars, 0, chars.length);
    }

    /**
     * 转换一个字符数组，将结果写入原数组。
     * <p>
     * 该方法假设转换前后的字符数是相等的，这种假设有利于写提高转换的性能。
     * </p>
     */
    public void convert(char[] chars, int offset, int count) {
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }

        if (count < 0) {
            throw new ArrayIndexOutOfBoundsException(count);
        }

        int end = offset + count;

        if (end > chars.length) {
            throw new ArrayIndexOutOfBoundsException(offset + count);
        }

        for (int i = offset; i < end; i++) {
            chars[i] = convert(chars[i]);
        }
    }
}
