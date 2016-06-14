/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.protocol;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Implements object conversion with Google Gson library.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class GsonObjectConverter extends ObjectConverter {

    private final GsonBuilder builder;
    private static final DateFormat DEFAULT_DATE_FORMAT = DateFormat.getDateTimeInstance();
    
    static class DateSerializer implements JsonSerializer<Date> {

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(DEFAULT_DATE_FORMAT.format(src));
        }

    }

    static class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                        throws JsonParseException {
            final String src = json.getAsJsonPrimitive().getAsString();
            try {
                return DEFAULT_DATE_FORMAT.parse(src);
            } catch (final ParseException e) {
                final String error = "[ERROR] parsing " + src + " to valid date instance: " + e.getMessage();
                throw new JsonParseException(error, e);
            }
        }

    }

    public GsonObjectConverter() {
    	builder = new GsonBuilder();
    	builder.registerTypeAdapter(Date.class, new DateSerializer());
    	builder.registerTypeAdapter(Date.class, new DateDeserializer());
    }

    @Override
    public byte[] toBytes(Object value) throws IOException {
        if (value == null) {
            return null;
        }
        final String json = toString(value);
        return json.getBytes();
    }

    @Override
    public String toString(Object value) throws IOException {
        if (value == null) {
            return null;
        }
        final Gson gson = builder.create();
        return gson.toJson(value);
    }

    @Override
    public <T> T fromBytes(byte[] value, Class<T> valueType) throws IOException {
        if (value == null) {
            return null;
        }
        final String json = new String(value);
        return fromString(json, valueType);
    }

    @Override
    public <T> T fromString(String value, Class<T> valueType) throws IOException {
        if (value == null) {
            return null;
        }
        final Gson gson = builder.create();
        return gson.fromJson(value, valueType);
    }

}