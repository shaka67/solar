/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar;

import static com.ouer.solar.Emptys.NULL_PLACEHOLDER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月23日 下午4:41:27
 */
public class EmptysTest {

    @Test
    public void nullPlaceholder_toString() throws Exception {
        assertEquals("null", NULL_PLACEHOLDER.toString());
    }

    @Test
    public void nullPlaceholder_serialize() throws Exception {
        // write
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(NULL_PLACEHOLDER);
        oos.close();

        // read
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);

        assertSame(NULL_PLACEHOLDER, ois.readObject());
    }

}
