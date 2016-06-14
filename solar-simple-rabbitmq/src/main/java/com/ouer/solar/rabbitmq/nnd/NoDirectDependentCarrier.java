/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.rabbitmq.nnd;

/**
 * The name may confuse, This interface is used for a message contains some arguments (let us call it NDDM) which a rabbitmq producer need to send to a transfer station first,
 * after which the transfer station then will send the argument(s) to a real rabbitmq subscriber.
 * In the case above, the rabbitmq producer and rabbitmq subscriber are have dependency on this NDDM but the transfer station is not. 
 * 
 * A classic use case is the job scheduler:
 * the producer (which use jobclient jar) is dependent on the NDDM
 * this subscriber is dependent on the NDDM too
 * but the jobserver is not dependent on the NDDM
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface NoDirectDependentCarrier 
{
	public Object[] getNoDirectDependentMsgs();
	
	public void setNoDirectDependentMsgs(Object[] msgs);
	
	public NoDirectDependentCarrier clone();
}
