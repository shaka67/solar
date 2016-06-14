/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;

/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class DefinitionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DefinitionException() {
        super();
    }

    public DefinitionException(String msg) {
        super(msg);
    }

    public DefinitionException(Throwable t) {
        super(t);
    }

    public DefinitionException(String msg, Throwable t) {
        super(msg, t);
    }

}
