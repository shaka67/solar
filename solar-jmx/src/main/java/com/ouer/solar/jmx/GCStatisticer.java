/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jmx;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class GCStatisticer {

	private final List<GarbageCollectorMXBean> gcCollectorMXBean = ManagementFactory
			.getGarbageCollectorMXBeans();

	public String getCollectionCount() {
		final StringBuilder builder = new StringBuilder();
		for (final GarbageCollectorMXBean collector : gcCollectorMXBean) {
			builder.append(collector.getName()).append(":")
					.append(collector.getCollectionCount()).append("\n");
		}
		return builder.toString();
	}

	public String getCollectionTime() {
		final StringBuilder builder = new StringBuilder();
		for (final GarbageCollectorMXBean collector : gcCollectorMXBean) {
			builder.append(collector.getName()).append(":")
					.append(collector.getCollectionTime()).append("\n");
		}
		return builder.toString();
	}

}
