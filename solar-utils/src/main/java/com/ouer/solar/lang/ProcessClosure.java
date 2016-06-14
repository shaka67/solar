/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ouer.solar.StringPool;
import com.ouer.solar.able.Processable;
import com.ouer.solar.logger.CachedLogger;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月31日 下午8:29:32
 */
public class ProcessClosure extends CachedLogger implements Processable, StringPool.Charset {

    private String info;

    @Override
    public void execute(Object...inputs) {

        for (Object cmd : inputs) {
            if (cmd.getClass().isArray()) {
                this.execute(object2Array(cmd));
                continue;
            }
            this.execute(cmd.toString());
        }
    }

    @Override
	public void execute(String...inputs) {
        work(inputs);
    }

    @Override
    public void execute(String input) {
        work(input);
    }

    private void work(String...cmds) {
        StringBuilder builder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmds);
            InputStreamReader isr = new InputStreamReader(process.getInputStream(), GBK);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                logger.info(line);
                builder.append(line).append("\n");
            }

        } catch (IOException e) {
            builder.append(e);
            logger.error("execute process error", e);
        } finally {
            if (process != null) {
                process.destroy();
            }

            info = builder.toString();
        }
    }

    private String[] object2Array(Object obj) {
        Object[] cmds = (Object[]) obj;
        String[] result = new String[cmds.length];
        for (int i = 0; i < cmds.length; i++) {
            result[i] = cmds[i].toString();
        }
        return result;
    }

    @Override
	public String getInfo() {
        return info;
    }

}
