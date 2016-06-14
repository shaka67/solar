/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import com.ouer.solar.able.Processable;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 下午8:29:58
 */
public class ShellClosure implements Processable {

    private Processable processable;

    public ShellClosure() {
        this(new ProcessClosure());
    }

    public ShellClosure(Processable processable) {
        this.processable = processable;
    }

    @Override
    public void execute(Object...inputs) {
        for (Object cmd : inputs) {
            if (cmd.getClass().isArray()) {
                processable.execute(cmd);
                continue;
            }
            processable.execute(cmd2Shell(cmd.toString()));
        }
    }

    @Override
    public String getInfo() {
        return processable.getInfo();
    }

    @Override
    public void execute(String...inputs) {
        for (String cmd : inputs) {
            processable.execute(cmd2Shell(cmd));
        }
    }

    @Override
    public void execute(String input) {
        processable.execute(cmd2Shell(input));
    }

    private String[] cmd2Shell(String input) {
        return new String[] { "sh", "-c", input };
    }

}
