/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.i18n.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.ouer.solar.i18n.CharConverter;
import com.ouer.solar.i18n.CharConverterProvider;
import com.ouer.solar.io.StreamUtil;

/**
 * 用来转换简繁中文的provider。
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月24日 上午3:48:24
 */
public abstract class ChineseCharConverterProvider implements CharConverterProvider {

    public static final String CODE_TABLE_CHARSET = "UTF-16BE";
    private char[] codeTable;

    @Override
	public CharConverter createCharConverter() {
        loadCodeTable();

        return new CharConverter() {
            @Override
            public char convert(char ch) {
                return codeTable[ch];
            }
        };
    }

    /** 装载编码表。 */
    protected final char[] loadCodeTable() {
        if (codeTable == null) {
            InputStream istream = getClass().getResourceAsStream(getCodeTableName() + ".ctable");

            if (istream == null) {
                throw new RuntimeException("Could not find code table: " + getCodeTableName());
            }

            Reader reader = null;

            try {
                reader = new BufferedReader(new InputStreamReader(istream, CODE_TABLE_CHARSET));
                codeTable = new char[65536];

                for (int i = 0; i < 65536; i++) {
                    codeTable[i] = (char) reader.read();
                }
            } catch (IOException e) {
                throw new RuntimeException("Could not read code table: " + getCodeTableName(), e);
            } finally {
                StreamUtil.close(reader);
            }
        }

        return codeTable;
    }

    protected abstract String getCodeTableName();
}
