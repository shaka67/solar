/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.biz;

/**
 * 命令接口
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 上午2:48:24
 */

public interface Command {

    /**
     * 执行命令
     * 
     * @param context 命令上下文环境
     */
    void execute(Context context);

}
