/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.able;

/**
 * 代表一个系统进程
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 下午8:16:07
 */
public interface Processable extends Closure {

    /**
     * info about execute results
     * 
     * @return execute results
     */
    String getInfo();

    /**
     * 执行命令
     * 
     * @param input 命令参数集
     */
    public void execute(String...inputs);

    /**
     * 执行命令
     * 
     * @param input 命令参数
     */
    public void execute(String input);

}
