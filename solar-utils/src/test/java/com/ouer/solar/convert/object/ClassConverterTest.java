/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.object.ClassConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午2:20:47
 */
public class ClassConverterTest {

    @Test
    public void toConvert() {
        ClassConverter classConverter = new ClassConverter();

        assertEquals(String.class, classConverter.toConvert(String.class));
        assertEquals(Integer.class, classConverter.toConvert("java.lang.Integer"));

    }

}
