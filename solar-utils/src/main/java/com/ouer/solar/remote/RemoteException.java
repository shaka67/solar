/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.remote;

/**
 * Provide details explanation for problems encountered during the remote method invocation.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RemoteException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
    private final RemoteErrorCode error;
   
    public RemoteException(RemoteErrorCode error)
    {
        super();
        this.error = error;
    }

    public RemoteException(String message, RemoteErrorCode error)
    {
        super(message);
        this.error = error;
    }

    public RemoteException(String message, RemoteErrorCode error, Throwable cause)
    {
        super(message, cause);
        this.error = error;
    }

    public RemoteErrorCode getErrorCode()
    {
        return error;
    }

    @Override
    public String getMessage()
    {
        return error.name() + " - " + super.getMessage();
    }

}