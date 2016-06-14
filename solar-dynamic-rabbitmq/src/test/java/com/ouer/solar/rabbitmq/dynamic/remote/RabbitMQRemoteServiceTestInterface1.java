/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import com.ouer.solar.remote.RemoteService;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@RemoteService(group="test1")
public interface RabbitMQRemoteServiceTestInterface1 {

    public String testRemoteMethod1();

    public String testRemoteMethod2(String parameter);

    public void testRemoteMethod3(int parameter);

}