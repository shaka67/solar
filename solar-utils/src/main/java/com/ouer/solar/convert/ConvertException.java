/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.convert;

import com.ouer.solar.exception.UncheckedException;

/**
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 上午3:34:57
 */
public class ConvertException extends UncheckedException {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4896121228312641852L;

    public ConvertException(Throwable t) {
        super(t);
    }

    public ConvertException() {
        super();
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String message, Throwable t) {
        super(message, t);
    }

    public ConvertException(Object value) {
        this("Unable to convert value: " + value);
    }

    public ConvertException(Object value, Throwable t) {
        this("Unable to convert value: " + value, t);
    }
}