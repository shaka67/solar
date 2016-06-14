/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.file;

import com.ouer.solar.Assert;
import com.ouer.solar.EnumUtil;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月27日 下午5:48:14
 */
public abstract class ProcessFactory {

    public static FileProcessor create(FileType type) {
        return type.createProcessor();
    }

    public static FileProcessor create(String ext) {
        FileType type = EnumUtil.parseName(FileType.class, ext.toUpperCase());
        Assert.assertNotNull(type, "ext is illegal");

        return type.createProcessor();
    }

}
