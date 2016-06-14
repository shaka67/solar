/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class JVMMemory {

	private final MemoryMXBean memoryMXBean;

	private final long mBytes = 1024 * 1024;

	public JVMMemory() {
		memoryMXBean = ManagementFactory.getMemoryMXBean();
	}

	public long getHeapMemoryUsed() {
		return memoryMXBean.getHeapMemoryUsage().getUsed();
	}

	public long getHeapMemoryMax() {
		return memoryMXBean.getHeapMemoryUsage().getMax();
	}

	public long getHeapMemoryCommitted() {
		return memoryMXBean.getHeapMemoryUsage().getCommitted();
	}

	public long getNonHeapMemoryInit() {
		return memoryMXBean.getNonHeapMemoryUsage().getInit();
	}

	public long getNonHeapMemoryUsed() {
		return memoryMXBean.getNonHeapMemoryUsage().getUsed();
	}

	public long getNonHeapMemoryMax() {
		return memoryMXBean.getNonHeapMemoryUsage().getMax();
	}

	public long getNonHeapMemoryCommitted() {
		return memoryMXBean.getNonHeapMemoryUsage().getCommitted();
	}

	public long getHeapMemoryInit() {
		return memoryMXBean.getHeapMemoryUsage().getInit();
	}

	public double getHeapMemoryUsedInMBytes() {
		return ((double) getHeapMemoryUsed() / mBytes);
	}

	public double getHeapMemoryMaxInMBytes() {
		return ((double) getHeapMemoryMax() / mBytes);
	}

	public double getHeapMemoryCommittedInMBytes() {
		return ((double) getHeapMemoryCommitted() / mBytes);
	}

	public double getHeapMemoryInitInMBytes() {
		return ((double) getHeapMemoryInit() / mBytes);
	}

	public double getNonHeapMemoryUsedInMBytes() {
		return ((double) getNonHeapMemoryUsed() / mBytes);
	}

	public double getNonHeapMemoryMaxInMBytes() {
		return ((double) getNonHeapMemoryMax() / mBytes);
	}

	public double getNonHeapMemoryCommittedInMBytes() {
		return ((double) getNonHeapMemoryCommitted() / mBytes);
	}

	public double getNonHeapMemoryInitInMBytes() {
		return ((double) getNonHeapMemoryInit() / mBytes);
	}

//	public String getHeapMemoryView() {
//		return null;
//	}
//
//	public String getNonHeapMemoryView() {
//		return null;
//	}
//
//	public String getHeapMemoryMBytesView() {
//		return null;
//	}
//
//	public String getNonHeapMemoryMBytesView() {
//		return null;
//	}

}
