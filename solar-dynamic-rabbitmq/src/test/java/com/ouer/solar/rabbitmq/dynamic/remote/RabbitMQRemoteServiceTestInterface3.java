/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import com.ouer.solar.remote.RemoteService;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@RemoteService(group="test5")
public interface RabbitMQRemoteServiceTestInterface3 {

    public String testRemoteMethodWithException(String parameter) throws RabbitMQRemoteServiceTestException;
}