/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.primitive;

import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月28日 下午11:11:42
 */
public class CharacterConverter extends ObjectConverter<Character> implements TypeConverter<Character> {

    public CharacterConverter() {
        register(Character.class);
        register(char.class);
    }

    @Override
    public Character toConvert(String value) {
        return value.charAt(0);
    }

    @Override
    public String fromConvert(Character value) {
        return String.valueOf(value);
    }

    @Override
	public Character toConvert(Object value) {
        if (value.getClass() == Character.class) {
            return (Character) value;
        }
        if (value instanceof Number) {
            char c = (char) ((Number) value).intValue();
            return Character.valueOf(c);
        }

        return Character.valueOf(value.toString().charAt(0));
    }

}
