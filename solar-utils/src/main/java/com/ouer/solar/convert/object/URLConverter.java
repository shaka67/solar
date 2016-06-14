/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 下午7:43:41
 */
public class URLConverter extends ObjectConverter<URL> implements TypeConverter<URL> {

    public URLConverter() {
        register(URL.class);
    }

    @Override
    public URL toConvert(String value) {
        return convert(value);
    }

    @Override
    public String fromConvert(URL value) {
        return String.valueOf(value);
    }

    @Override
	public URL toConvert(Object value) {
        if (value instanceof URL) {
            return (URL) value;
        }

        if (value instanceof File) {
            File file = (File) value;
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new ConvertException(value, e);
            }
        }

        if (value instanceof URI) {
            URI uri = (URI) value;
            try {
                return uri.toURL();
            } catch (MalformedURLException e) {
                throw new ConvertException(value, e);
            }
        }

        return convert(value.toString());
    }

    private URL convert(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            throw new ConvertException(value, e);
        }
    }
}