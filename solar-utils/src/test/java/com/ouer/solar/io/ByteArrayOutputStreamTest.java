/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.io;

import static org.junit.Assert.assertArrayEquals;

import java.io.InputStream;

import org.junit.Test;

import com.ouer.solar.io.ByteArrayOutputStream;
import com.ouer.solar.io.StreamUtil;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月21日 上午8:36:10
 */
public class ByteArrayOutputStreamTest {
    private byte[] data = "0123456789".getBytes();

    @Test
    public void toBytes() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos.write(data);

        assertArrayEquals(data, readStream(baos.toInputStream()));
        assertArrayEquals(data, baos.toByteArray().toByteArray());

        StreamUtil.close(baos);
    }

    private byte[] readStream(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;

        while ((i = is.read()) >= 0) {
            baos.write(i);
        }
        try {
            return baos.toByteArray().toByteArray();
        } finally {
            StreamUtil.close(baos);
        }

    }
}
