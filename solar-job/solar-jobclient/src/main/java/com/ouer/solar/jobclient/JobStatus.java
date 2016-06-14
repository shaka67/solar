/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobclient;

import java.io.Serializable;

/**
 * Status code used to indicate if a job request is succeed or failed to be processed.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JobStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private StatusCode code;
    private String text;

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

    JobStatus() {}

    public JobStatus withStatusText(String text) {
        this.text = text;
        return this;
    }

    public JobStatus withStatusCode(StatusCode code) {
    	this.code = code;
        return this;
    }

    public static JobStatus withSucceedStatusCode() {
        final JobStatus status = new JobStatus();
        return status.withStatusCode(StatusCode.SUCCEED);
    }

    public static JobStatus withFailedStatusCode() {
        final JobStatus status = new JobStatus();
        return status.withStatusCode(StatusCode.FAILED);
    }

    public StatusCode getStatusCode() {
        return code;
    }

    public String getStatusText() {
        return text;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[");
        if (code != null) {
            builder.append("status code: ").append(code.description());
        }
        if (text != null) {
            builder.append(", ").append("status text: ").append(text);
        }
        builder.append("]");
        return builder.toString();
    }

}