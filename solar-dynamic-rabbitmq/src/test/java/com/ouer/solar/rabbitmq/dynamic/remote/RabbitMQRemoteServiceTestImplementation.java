/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQRemoteServiceTestImplementation implements RabbitMQRemoteServiceTestInterface1,
                                                                RabbitMQRemoteServiceTestInterface2,
                                                                RabbitMQRemoteServiceTestInterface3 {

    @Override
    public String testRemoteMethod1() {
        try {
            Thread.sleep(1000); // let it sleep 1 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "result returned from remote method 1";
    }

    @Override
    public String testRemoteMethod2(String parameter) {
        try {
            Thread.sleep(500); // let it sleep half second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "result returned from remote method 2 implementation for: " + parameter;
    }

    @Override
    public void testRemoteMethod3(int parameter) {
        System.out.println("received parameter from remote method 3 implementation for: " + parameter);
    }

    @Override
    public String testRemoteMethodWithException(String parameter) throws RabbitMQRemoteServiceTestException {
        throw new RabbitMQRemoteServiceTestException("This method is supposed to throw a exception");
    }

}