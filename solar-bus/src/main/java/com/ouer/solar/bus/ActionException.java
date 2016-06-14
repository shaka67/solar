/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

/**
 * Exception thrown when there is a problem processing the internals of Action.execute()
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ActionException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public ActionException(String message) {
        super(message);
    }

    public ActionException(Throwable cause) {
        super(cause);
    }

    public ActionException(String msg, Throwable root) {
    	super(msg, root);
    }

}
