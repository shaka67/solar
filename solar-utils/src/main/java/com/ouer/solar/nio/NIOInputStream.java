/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.nio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月25日 上午1:47:48
 */
public class NIOInputStream extends InputStream {

    protected int count;
    protected int position;
    private final ByteBuffer in;

    public NIOInputStream(ByteBuffer in) {
        this.in = in;
    }

    @Override
	public int read() throws IOException {
        try {
            int rc = in.get() & 0xff;
            return rc;
        } catch (BufferUnderflowException e) {
            return -1;
        }
    }

    @Override
	public int read(byte b[], int off, int len) throws IOException {
        if (in.hasRemaining()) {
            int rc = Math.min(len, in.remaining());
            in.get(b, off, rc);
            return rc;
        }

        return len == 0 ? 0 : -1;
    }

    @Override
	public long skip(long n) throws IOException {
        int rc = Math.min((int) n, in.remaining());
        in.position(in.position() + rc);
        return rc;
    }

    @Override
	public int available() throws IOException {
        return in.remaining();
    }

    @Override
	public boolean markSupported() {
        return false;
    }

    // ignore
    @Override
	public void close() throws IOException {
    }
}
