/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ouer.solar.EnumUtil;
import com.ouer.solar.file.FileType;
import com.ouer.solar.logger.CachedLogger;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 下午4:28:51
 */
public class EnumUtilTest extends CachedLogger {

    @Test
    public void find() {
        FileType type = EnumUtil.find(FileType.class, "excel");

        assertEquals(FileType.EXCEL, type);
    }

}
