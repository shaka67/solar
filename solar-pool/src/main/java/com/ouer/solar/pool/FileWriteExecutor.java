/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.DateUtil;
import com.ouer.solar.FileUtil;
import com.ouer.solar.io.StreamUtil;

/**
 * 
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class FileWriteExecutor implements IBatchExecutor<String> {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileWriteExecutor.class);

    private String fileDir;
    private String fileName;

    /**
     * The default maximum file size is 64MB.
     */
    private int maxFileSize = 64 * 1024 * 1024;

    @Override
    public void execute(List<String> strings) {
    	if (LOG.isDebugEnabled()) {
			LOG.debug("execute - size=[{}]", strings.size());
		}

        // FIXME cache index
        File file = null;
        try {
            file = genFile(0);
        } catch (final IOException e) {
        	LOG.error("", e);
        }
        if (file != null) {
            writeToFile(file, strings);
        }
    }

    File genFile(int index) throws IOException {
        final String filepath =
                fileDir + File.separator + DateUtil.formatDate(new Date(), "yyyy-MM-dd") + File.separator + fileName
                        + "." + index;

        final File file = new File(filepath);
        if (file.exists()) {
            final int fileSize = FileUtil.getFileSize(filepath);
            if (fileSize >= maxFileSize) {
                index = index + 1;
                return genFile(index);
            }

            return file;
        }

        if (LOG.isInfoEnabled()) {
			LOG.info("create file [{}]", filepath);
		}

        try {
            return FileUtil.createAndReturnFile(filepath);
        } catch (final Exception e) {
        	LOG.error(">>>> Create File Exception: ", e);
            return null;
        }

    }

    private void writeToFile(File file, List<String> strings) {
        BufferedWriter bufferedOut = null;
        try {

            final FileOutputStream out = new FileOutputStream(file, true);
            bufferedOut = new BufferedWriter(new PrintWriter(out));
            for (final String s : strings) {
                bufferedOut.write(s + "\n");
            }

            bufferedOut.flush();
        } catch (final Exception e) {
        	LOG.error(">>>> Excute write Exception: ", e);
        } finally {
            StreamUtil.close(bufferedOut);
        }
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }
}
