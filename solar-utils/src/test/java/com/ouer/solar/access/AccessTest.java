/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.access;

//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ouer.solar.ClassLoaderUtil;
import com.ouer.solar.access.Access;
import com.ouer.solar.access.AccessException;
import com.ouer.solar.access.Resource;
import com.ouer.solar.access.fs.FileResource;
import com.ouer.solar.access.fs.LocalFSAccess;
import com.ouer.solar.access.strategy.DivideThousand;
import com.ouer.solar.io.ByteArray;
import com.ouer.solar.io.StreamUtil;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 上午2:48:25
 */
public class AccessTest {

    private Access access;

    private int id;

    String log4j = ClassLoaderUtil.getClasspath() + File.separator + "log4j.properties";

    @Before
    public void setUp() throws Exception {
        access = new LocalFSAccess(new DivideThousand(), ClassLoaderUtil.getClasspath());

        id = 1234567890;
    }

    @Test
    public void store() {

        File file = new File(log4j);

        try {
            ByteArray byteArray = StreamUtil.readBytes(file, true);
            Resource resource = new FileResource(id, byteArray);
            resource.getHeader().ext("properties");
            assertEquals(733933649L, resource.checksum());
            // 4114700253
            access.store(resource);
            byte[] raw = StreamUtil.readBytes(new File(log4j), true).getRawBytes();
            assertArrayEquals(raw, resource.getBody().getRawBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void retrieve() {

        store();

        try {
            Resource resource = access.retrieve(id, "properties");
            byte[] raw = StreamUtil.readBytes(new File(log4j), true).getRawBytes();

            assertArrayEquals(raw, resource.getBody().getRawBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void remove() {

        store();
        try {
            assertTrue(access.remove(id, "properties"));
        } catch (AccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void name() {

    }

    @After
    public void tearDown() throws Exception {
        access = null;
    }

    // LocalFSAccess

}
