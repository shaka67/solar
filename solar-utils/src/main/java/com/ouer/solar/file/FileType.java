/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.file;

import com.ouer.solar.able.Valuable;
import com.ouer.solar.file.csv.CsvProcessor;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年7月27日 上午7:39:28
 */
public enum FileType implements Valuable<String> {
    CSV {
        @Override
		public FileProcessor createProcessor() {
            return new CsvProcessor();
        }
    },
    XML, PDF, TXT, EXCEL, WORD;

    @Override
    public String value() {
        return this.name().toLowerCase();
    }

    public FileProcessor createProcessor() {
        return null;
    }

}
