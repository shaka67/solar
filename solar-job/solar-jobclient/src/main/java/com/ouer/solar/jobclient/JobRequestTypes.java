/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobclient;

/**
 * Enumerate a list of supported job queue for various of job operations.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public enum JobRequestTypes {

    JOB_ENQUEUE_REQUEST("enqueue"),
    JOB_DEQUEUE_REQUEST("dequeue"),
    JOB_REQUEUE_REQUEST("requeue"),
    JOB_PAUSE_REQUEST("pause"),
    JOB_RESUME_REQUEST("resume")
    ;

    private static final String JOB_ROUTING_PREFIX = "solar.routing.job";
    public static final String DEFAULT_JOB_EXCHANGE_NAME = "solar.exchange.job";
    public static final String DEFAULT_JOB_QUEUE_NAME = "solar.queue.job";

    private final String routing;

    JobRequestTypes(String key) {
        routing = JOB_ROUTING_PREFIX + key;
    }

    public String exchange() {
        return DEFAULT_JOB_EXCHANGE_NAME;
    }

    public String queue() {
        return DEFAULT_JOB_QUEUE_NAME;
    }

    public String routing() {
        return routing;
    }

    public static JobRequestTypes fromRoutingKey(String routingKey) {
        for (final JobRequestTypes type : JobRequestTypes.values()) {
            if (type.routing().equals(routingKey)) {
                return type;
            }
        }
        return null;
    }

}