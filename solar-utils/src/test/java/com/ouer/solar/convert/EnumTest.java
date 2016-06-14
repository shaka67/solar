/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.convert.ConverterManager;

/**
 */
public class EnumTest {

    public enum EnumSample {
        ONE, TWO
    }

    @Test
    public void testCastEnums() {
        EnumSample sample = ConverterManager.convertType("ONE", EnumSample.class);
        assertEquals(EnumSample.ONE, sample);
        sample = ConverterManager.convertType("TWO", EnumSample.class);
        assertEquals(EnumSample.TWO, sample);
    }

}