/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

/**
 * Implements object conversion with Hessian binary protocol.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class HessianObjectConverter extends ObjectConverter {

    @Override
    public byte[] toBytes(Object value) throws IOException {
        if (value == null) {
            return null;
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(buffer);
        output.startMessage();
        output.writeObject(value);
        output.completeMessage();
        output.close();
        return buffer.toByteArray();
    }

    @Override
    public String toString(Object value) throws IOException {
        if (value == null) {
            return null;
        }
        byte[] bytes = toBytes(value);
        return Base64.encodeBase64String(bytes);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T fromBytes(byte[] value, Class<T> valueType) throws IOException {
        if (value == null) {
            return null;
        }
        ByteArrayInputStream buffer = new ByteArrayInputStream(value);
        Hessian2Input input = new Hessian2Input(buffer);
        Object object;
        input.startMessage();
        object = input.readObject(valueType);
        input.completeMessage();
        input.close();
        buffer.close();
        return (T) object;
    }

    @Override
    public <T> T fromString(String value, Class<T> valueType) throws IOException {
        if (value == null) {
            return null;
        }
        byte[] bytes = Base64.decodeBase64(value);
        return fromBytes(bytes, valueType);
    }

}