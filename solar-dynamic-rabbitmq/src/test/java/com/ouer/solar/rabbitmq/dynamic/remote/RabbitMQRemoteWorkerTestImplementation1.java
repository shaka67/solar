/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq.dynamic.remote;

import com.ouer.solar.remote.RemoteMethod;
import com.ouer.solar.remote.RemoteWorker;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

@RemoteWorker(group="test1")
public class RabbitMQRemoteWorkerTestImplementation1 {

    private final RabbitMQRemoteServiceTestInterface1 interface1;
//    private final RabbitMQRemoteServiceTestInterface2 interface2;
//    private final RabbitMQRemoteServiceTestInterface3 interface3;

    public RabbitMQRemoteWorkerTestImplementation1(RabbitMQRemoteServiceTestInterface1 interface1
                                                   /*RabbitMQRemoteServiceTestInterface2 interface2,
                                                   RabbitMQRemoteServiceTestInterface3 interface3*/) {
        this.interface1 = interface1;
//        this.interface2 = interface2;
//        this.interface3 = interface3;
    }

    @RemoteMethod
    public String testRemoteMethod1() {
        System.out.println("being called: testRemoteMethod1");
        interface1.testRemoteMethod1();
//        interface2.testRemoteMethod1();
        return "result returned from remote method 1";
    }

    @RemoteMethod
    public String testRemoteMethod2(String parameter) {
        System.out.println("being called: testRemoteMethod2 " + parameter);
        interface1.testRemoteMethod2(parameter);
//        interface2.testRemoteMethod2(parameter);
        return "result returned from remote method 2 implementation for: " + parameter;
    }

//    @RemoteMethod
//    public String testRemoteMethodWithException(String parameter) throws Exception {
//        try {
//            String result = interface3.testRemoteMethodWithException(parameter);
//            return result;
//        } catch (Exception e) {
//            System.err.println("FAILED - " + e.getMessage());
//            throw e;
//        }
//    }

    public String testLocalMethod() {
        return "this is not a remote method";
    }

    protected void testProtectedMethod() {

    }

}