/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.date.format;

import com.ouer.solar.date.DateTimeStamp;
import com.ouer.solar.date.DatetimeObject;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月22日 下午5:14:34
 */
public interface Formatter {

    String convert(DatetimeObject datetime, String format);

    DateTimeStamp parse(String value, String format);
}
