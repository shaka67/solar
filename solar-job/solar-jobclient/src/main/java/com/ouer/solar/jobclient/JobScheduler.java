/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobclient;


/**
 * Submit job scheduling requests via remote service call.
 * All of the defined service methods would be processed in synchronous way.
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface JobScheduler {

    public JobStatus schedule(Job job);

    public JobStatus reschedule(Job job);

    public JobStatus delete(Job job);

    public JobStatus pause(Job job);

    public JobStatus resume(Job job);

}