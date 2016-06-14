/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bus;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.ouer.solar.lang.ThreadPool;

/**
 * Runnable class that is used per channel to make the overall registry threaded
 * and increase throughput of messages
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class BusDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(BusDispatcher.class);
    
    private final List<BusMember> members;

    public BusDispatcher() {
        members = Lists.newArrayList();
    }

    public void bind(BusMember member) {
        members.add(member);
    }

    public void unbind(BusMember member) {
        members.remove(member);
    }

    public <T> void push(ActionData<T> data) {
        Object member = null;

        final int membersSize = members.size();
        for (int i = 0; i < membersSize; i++) {
            member = members.get(i);
            if (BusSubscriber.class.isInstance(member)) {
                try {
                    ((BusSubscriber) member).receive(data);
                } catch (final Exception e) {
                    LOG.error(
                            "caught exception dispatching data to member index "
                                    + i + "\n data := " + data + " - "
                                    + e.getMessage(), e);
                }
            }
        }
    }

    public <T> void push(ActionData<T> data, boolean concurrent) {
    	final ActionData<T> fData = data;
        if (concurrent) {
            ThreadPool.getInstance().execute("BusDispatcher.push", new Runnable() {
                @Override
                public void run() {
                    push(fData);
                }
            });
            return;
        }

        push(data);
    }

}