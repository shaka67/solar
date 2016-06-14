/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class OperatingSystem {

	private final OperatingSystemMXBean OS = ManagementFactory
			.getOperatingSystemMXBean();

	public int getAvailableProcessors() {
		return OS.getAvailableProcessors();
	}

	public String getArch() {
		return OS.getArch();
	}

	public String getName() {
		return OS.getName();
	}

	public double getSystemLoadAverage() {
		return OS.getSystemLoadAverage();
	}

	public String getVersion() {
		return OS.getVersion();
	}

}
