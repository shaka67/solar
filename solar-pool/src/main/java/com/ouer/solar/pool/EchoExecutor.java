/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.pool;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public class EchoExecutor implements IBatchExecutor<String> {

	private static final Logger LOG = LoggerFactory.getLogger(EchoExecutor.class);
	
    @Override
    public void execute(List<String> records) {
        for (final String record : records) {
        	if (LOG.isInfoEnabled()) {
				LOG.info(record);
			}
        }
    }

}
