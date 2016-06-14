/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import com.ouer.solar.convert.ConverterManager;
import com.ouer.solar.convert.object.FileConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午3:12:26
 */
public class ConverterManagerTest {

    @Test
    public void testRegistration() {
        FileConverter fileTypeConverter = (FileConverter) ConverterManager.lookup(File.class);

        assertNotNull(fileTypeConverter);

    }
}
