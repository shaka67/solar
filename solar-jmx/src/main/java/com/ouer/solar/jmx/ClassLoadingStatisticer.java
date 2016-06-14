/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jmx;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ClassLoadingStatisticer {

	private final ClassLoadingMXBean classLoadingMXBean = ManagementFactory
			.getClassLoadingMXBean();

	public int getLoadedClassCount() {
		return classLoadingMXBean.getLoadedClassCount();
	}

	public long getTotalLoadedClassCount() {
		return classLoadingMXBean.getTotalLoadedClassCount();
	}

	public long getUnloadedClassCount() {
		return classLoadingMXBean.getUnloadedClassCount();
	}

}
