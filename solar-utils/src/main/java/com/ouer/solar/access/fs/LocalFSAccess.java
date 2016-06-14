/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access.fs;

import java.io.File;

import com.ouer.solar.access.AccessStrategy;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 下午5:42:36
 */
public class LocalFSAccess extends FileSystemAccess {

    private String storageHome;

    public LocalFSAccess(AccessStrategy strategy) {
        super(strategy);
    }

    public LocalFSAccess(AccessStrategy strategy, String storageHome) {
        this(strategy);
        this.storageHome = storageHome;
    }

    @Override
    protected String physicalLocation(String logicalLocation) {
        return (storageHome == null) ? logicalLocation : storageHome + File.separator + logicalLocation;
    }

    public void setStorageHome(String storageHome) {
        this.storageHome = storageHome;
    }

    @Override
    public String name() {
        return "Local File System";
    }

}
