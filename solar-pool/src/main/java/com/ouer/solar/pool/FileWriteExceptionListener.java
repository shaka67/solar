/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.util.List;

/**
 * 
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class FileWriteExceptionListener implements ExceptionListener<String> {

    private FileWriteExecutor fileWriteExecutor;

    @Override
    public void onException(List<String> strings) {
        fileWriteExecutor.execute(strings);
    }

    public void setBackupDir(String backupDir) {
        fileWriteExecutor.setFileDir(backupDir);
    }

    public void setFileName(String fileName) {
        fileWriteExecutor.setFileName(fileName);
    }

    public void setFileWriteExecutor(FileWriteExecutor fileWriteExecutor) {
        this.fileWriteExecutor = fileWriteExecutor;
    }

}
