/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.cache.signal;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public interface CacheSignalBroadcaster {

	public void broadcastEvictSignal(String namespace, Object key);

	public void broadcastClearSignal(String namespace);

}
