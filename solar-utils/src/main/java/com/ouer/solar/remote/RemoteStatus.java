/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.remote;

import java.io.Serializable;

/**
 * Status code used to indicate if a worker request is succeed or failed to be processed.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RemoteStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private StatusCode code;
    private String message;
    private RemoteErrorCode error;
    private Throwable cause;
    
    public enum StatusCode {

        SUCCEED(10, "Succeed"),
        FAILED(11, "Failed")
        ;

        private final int key;
        private final String description;
        
        StatusCode(int key, String description) {
            this.key = key;
            this.description = description;
        }

        public int key() {
            return key;
        }

        public String description() {
            return description;
        }

    }

    RemoteStatus() {}

    RemoteStatus withStatusCode(StatusCode code) {
    	this.code = code;
        return this;
    }

    public RemoteStatus withErrorMessage(String message) {
    	this.message = message;
        return this;
    }

    public RemoteStatus withErrorCode(RemoteErrorCode error) {
    	this.error = error;
        return this;
    }
    
    public RemoteStatus withErrorCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public static RemoteStatus withSucceedStatusCode() {
        final RemoteStatus status = new RemoteStatus();
        return status.withStatusCode(StatusCode.SUCCEED);
    }

    public static RemoteStatus withFailedStatusCode() {
        final RemoteStatus status = new RemoteStatus();
        return status.withStatusCode(StatusCode.FAILED);
    }

    public StatusCode getStatusCode() {
        return code;
    }

    public String getErrorMessage() {
        return message;
    }

    public RemoteErrorCode getErrorCode() {
        return error;
    }
    
    public Throwable getErrorCause() {
        return cause;
    }

    public boolean isSucceed() {
        return StatusCode.SUCCEED.equals(code);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[");
        if (code != null) {
            builder.append("status code: ").append(code.description());
        }
        if (error != null) {
            builder.append(", ").append("error code: ").append(error);
        }
        if (message != null) {
            builder.append(", ").append("status text: ").append(message);
        }
        builder.append("]");
        return builder.toString();
    }

}