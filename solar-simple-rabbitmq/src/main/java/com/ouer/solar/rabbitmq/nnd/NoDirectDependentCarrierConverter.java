/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */

package com.ouer.solar.rabbitmq.nnd;

import java.io.IOException;

/**
 * A converter concentrating on convert a {@link NoDirectDependentCarrier} or reverse a NDDM 
 * in {@link NoDirectDependentCarrier}, please refer to {@link NoDirectDependentCarrier} for more information
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface NoDirectDependentCarrierConverter 
{
	public NoDirectDependentCarrier convert(NoDirectDependentCarrier source) throws IOException;
	
	public Object[] reverse(Object[] source) throws Exception;
}
