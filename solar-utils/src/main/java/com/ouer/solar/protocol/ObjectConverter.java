/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.protocol;

import java.io.IOException;

/**
 * Object converter used to serialize/deserialize method parameters
 * that need to be sent over wire.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class ObjectConverter {

    /**
     * Implements conversion of the single value.
     *
     * @param value Java value to convert to String.
     * @return converted value
     * @throws IOException if conversion of the value passed as parameter failed for any reason.
     */
    public abstract byte[] toBytes(Object value) throws IOException;

    /**
     * Implements conversion of the single value.
     *
     * @param value Java value to convert to String.
     * @return converted value
     * @throws IOException if conversion of the value passed as parameter failed for any reason.
     */
    public abstract String toString(Object value) throws IOException;

    /**
     * Implements conversion of the single value.
     *
     * @param value a String to convert to a Java object.
     * @return converted Java object
     * @throws IOException if conversion of the data passed as parameter failed for any reason.
     */
    public abstract <T> T fromBytes(byte[] value, Class<T> valueType) throws IOException;

    /**
     * Implements conversion of the single value.
     *
     * @param value a String to convert to a Java object.
     * @return converted Java object
     * @throws IOException if conversion of the data passed as parameter failed for any reason.
     */
    public abstract <T> T fromString(String value, Class<T> valueType) throws IOException;

}