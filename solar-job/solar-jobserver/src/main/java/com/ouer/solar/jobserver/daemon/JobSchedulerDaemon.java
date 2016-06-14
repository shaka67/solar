/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jobserver.daemon;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.daemon.AbstractDaemon;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JobSchedulerDaemon extends AbstractDaemon {

	private static final String ME = "[JobScheduler Daemon]";
	private static final Logger LOG = LoggerFactory.getLogger(JobSchedulerDaemon.class);

    private final Scheduler scheduler;

    public JobSchedulerDaemon(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void start() {
    	if (LOG.isInfoEnabled()) {
            LOG.info(ME + " is going to start...");
        }

        try {
            scheduler.start();
        } catch (final SchedulerException e) {
            final String error = "[start] FATAL SCHEDULER ERROR: " + e.getMessage();
            LOG.error(error, e);
            throw new RuntimeException(error, e);
        } catch (final Exception e) {
            final String error = "[start] FATAL UNKNOWN ERROR: " + e.getMessage();
            LOG.error(error, e);
            throw new RuntimeException(error, e);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " started now.");
        }
    }

    @Override
    public void shutdown() {
    	if (LOG.isInfoEnabled()) {
            LOG.info(ME + " is going to shutdown...");
        }

        try {
            scheduler.shutdown(true);
        } catch (final SchedulerException e) {
            final String error = "[shutdown] FATAL SCHEDULER ERROR: " + e.getMessage();
            LOG.error(error);
            throw new RuntimeException(error, e);
        } catch (final Exception e) {
            final String error = "[shutdown] FATAL UNKNOWN ERROR: " + e.getMessage();
            LOG.error(error);
            throw new RuntimeException(error, e);
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(ME + " has been shutdown now.");
        }
    }

    @Override
    public boolean isAlive() {
        try {
            return !scheduler.isShutdown();
        } catch (final SchedulerException e) {
            final String error = "[isAlive] FATAL SCHEDULE ERROR: " + e.getMessage();
            LOG.error(error);
            return false;
        } catch (final Exception e) {
            final String error = "[isAlive] UNKNOWN ERROR: " + e.getMessage();
            LOG.error(error);
            return false;
        }
    }

}