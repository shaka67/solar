/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.array;

import com.ouer.solar.ArrayUtil;
import com.ouer.solar.StringPool;
import com.ouer.solar.StringUtil;
import com.ouer.solar.convert.ConvertBean;
import com.ouer.solar.convert.ConvertUtil;
import com.ouer.solar.convert.TypeConverter;

/**
 * FIXME ArrayConverter
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 下午7:18:18
 */
@TypeConverter.Convert
public class ClassArrayConverter extends ArrayConverter<Class<?>> implements TypeConverter<Class<?>[]> {

    protected final ConvertBean convertBean;

    @SuppressWarnings("unchecked")
    public ClassArrayConverter() {
        this.convertBean = ConvertUtil.getInstance();
        converterManagerBean.register(Class[].class, this);

        this.targetComponentType = (Class<Class<?>>) Class[].class.getComponentType();
    }

    public ClassArrayConverter(ConvertBean convertBean) {
        this.convertBean = convertBean;
        converterManagerBean.register(Class[].class, this);
    }

    @Override
    protected Class<?>[] createArray(int length) {
        return new Class[length];
    }

    @Override
    protected String[] convertStringToArray(String value) {
        String[] strings = StringUtil.splitc(value, NUMBER_DELIMITERS);

        int count = 0;

        for (int i = 0; i < strings.length; i++) {
            strings[count] = strings[i].trim();
            if (strings[count].length() == 0) {
                continue;
            }

            if (!strings[count].startsWith(StringPool.Symbol.HASH)) {
                count++;
            }
        }

        if (count != strings.length) {
            return ArrayUtil.subarray(strings, 0, count);
        }

        return strings;
    }

}