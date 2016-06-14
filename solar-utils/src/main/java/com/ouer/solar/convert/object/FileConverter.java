/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert.object;

/**
 * 
 */

import java.io.File;
import java.io.IOException;

import com.ouer.solar.FileUtil;
import com.ouer.solar.convert.ConvertException;
import com.ouer.solar.convert.ObjectConverter;
import com.ouer.solar.convert.TypeConverter;
import com.ouer.solar.io.StreamUtil;
import com.ouer.solar.io.WriterUtil;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 下午3:24:16
 */
public class FileConverter extends ObjectConverter<File> implements TypeConverter<File> {

    public FileConverter() {
        register(File.class);
    }

    @Override
    public File toConvert(String value) {
        return convert(value);
    }

    @Override
    public String fromConvert(File value) {
        return value.getName();
    }

    @Override
	public File toConvert(Object value) {
        if (value instanceof File) {
            return (File) value;
        }

        Class<?> type = value.getClass();
        if (type == byte[].class) {
            try {
                File tempFile = FileUtil.createTempFile();
                StreamUtil.writeBytes((byte[]) value, tempFile, true);

                return tempFile;
            } catch (IOException e) {
                throw new ConvertException(e);
            }
        }
        if (type == String.class) {
            return convert(value.toString());
        }

        throw new ConvertException(value);
    }

    private File convert(String value) {
        try {
            File tempFile = FileUtil.createTempFile();
            WriterUtil.writeLinesAndClose(tempFile, value);
            return tempFile;
        } catch (IOException e) {
            throw new ConvertException(e);
        }
    }

}
