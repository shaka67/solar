/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.protocol;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectMapper.DefaultTyping;
import org.codehaus.jackson.map.PropertyNamingStrategy;

/**
 * Implements object conversion with Jackson Json library.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JsonObjectConverter extends ObjectConverter {

    protected final ObjectMapper mapper;

    public JsonObjectConverter() {
        this(new ObjectMapper());
        // ignoring unknown properties makes us more robust to changes in the schema
        mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // This will allow including type information all non-final types. This allows correct
        // serialization/deserialization of generic collections, for example List<MyType>.
        // FIXME enable or disable?
//        mapper.enableDefaultTyping(DefaultTyping.NON_FINAL);

//        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    public JsonObjectConverter(DefaultTyping typing) {
    	this();
    	mapper.enableDefaultTyping(typing);
    }

    public JsonObjectConverter(PropertyNamingStrategy pns) {
    	this();
    	mapper.setPropertyNamingStrategy(pns);
    }

    public JsonObjectConverter(DefaultTyping typing, PropertyNamingStrategy pns) {
    	this();
    	mapper.enableDefaultTyping(typing);
    	mapper.setPropertyNamingStrategy(pns);
    }

    public JsonObjectConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public byte[] toBytes(Object value) throws IOException {
        if (value == null) {
            return null;
        }
        try {
            return mapper.writeValueAsBytes(value);
        } catch (JsonGenerationException e) {
            throw new IOException(e);
        } catch (JsonMappingException e) {
            throw new IOException(e);
        }
    }

    @Override
    public String toString(Object value) throws IOException {
        if (value == null) {
            return null;
        }
        byte[] bytes = toBytes(value);
        return new String(bytes);
    }

    @Override
    public <T> T fromBytes(byte[] value, Class<T> valueType) throws IOException {
        if (value == null) {
            return null;
        }
        try {
            return mapper.readValue(value, valueType);
        } catch (JsonParseException e) {
            throw new IOException(e);
        } catch (JsonMappingException e) {
            throw new IOException(e);
        }
    }

    @Override
    public <T> T fromString(String value, Class<T> valueType) throws IOException {
        if (value == null) {
            return null;
        }
        byte[] bytes = value.getBytes();
        return fromBytes(bytes, valueType);
    }

}