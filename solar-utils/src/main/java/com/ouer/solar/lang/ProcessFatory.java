/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import com.ouer.solar.SystemUtil;
import com.ouer.solar.able.Processable;

/**
 * 创建系统进程的工厂
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014-12-24 下午1:12:07
 */
public abstract class ProcessFatory {

    /**
     * 根据当前系统创建进程
     * 
     * @return 系统进程 @see Processable
     */
    public static Processable create() {
        // 简单判断下windows系统和非windows系统
        if (SystemUtil.getOsInfo().isWindows()) {
            return new ProcessClosure();
        }

        return new ShellClosure();
    }

}
