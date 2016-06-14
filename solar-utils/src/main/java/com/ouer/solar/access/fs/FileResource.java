/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access.fs;

import java.io.File;
import java.io.Serializable;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

import com.ouer.solar.access.Resource;
import com.ouer.solar.access.ResourceHeader;
import com.ouer.solar.access.ResourceHeaderSupport;
import com.ouer.solar.io.ByteArray;
import com.ouer.solar.logger.CachedLogger;

/**
 * 文件设备简单实现
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 下午7:12:13
 */
public class FileResource extends CachedLogger implements Resource, Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6047557727196586110L;

    private long id;

    private ByteArray body;

    private long value;

    private ResourceHeader header;

    private long size;

    public FileResource(long id, ByteArray body) {
        this(id, body, new Adler32());
    }

    public FileResource(long id, ByteArray body, String where) {
        this(id, body, new Adler32(), where);
    }

    public FileResource(long id, ByteArray body, Checksum checksum) {
        this.id = id;
        this.body = body;

        init(checksum);
    }

    public FileResource(long id, ByteArray body, Adler32 adler32, String where) {
        this(id, body, new Adler32());

        this.size = new File(where).length();
    }

    private void init(Checksum checksum) {
        checksum.update(body.getRawBytes(), body.getOffset(), body.getLength());

        value = checksum.getValue();
    }

    @Override
    public long checksum() {
        return value;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public ByteArray getBody() {
        return body;
    }

    @Override
    public ResourceHeader getHeader() {
        if (header == null) {
            header = new ResourceHeaderSupport();
        }

        return header;
    }

    public void setHeader(ResourceHeader header) {
        this.header = header;
    }

    @Override
	public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
