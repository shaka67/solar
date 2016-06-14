/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class ThreadStatisticer {

	private final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

	private int stackDepth = 20;

	public void setContentionTracing(boolean val) {
		threadBean.setThreadContentionMonitoringEnabled(val);
	}

	public void setStackDepth(int stackDepth) {
		this.stackDepth = stackDepth;
	}

	/**
	 * 获取所有的线程数
	 *
	 * @return 所有的线程数
	 */
	public int getAllThreadsCount() {
		return threadBean.getThreadCount();
	}

	/**
	 * 获取峰值线程数
	 *
	 * @return 峰值线程数
	 */
	public int getPeakThreadCount() {
		return threadBean.getPeakThreadCount();
	}

	/**
	 * 获取守护线程数
	 *
	 * @return 守护线程数
	 */
	public int getDaemonThreadCount() {
		return threadBean.getDaemonThreadCount();
	}

	/**
	 * 获取启动以来创建的线程数
	 *
	 * @return 启动以来创建的线程数
	 */
	public long getTotalStartedThreadCount() {
		return threadBean.getTotalStartedThreadCount();
	}

	/**
	 * 获取死锁数
	 *
	 * @return 死锁数
	 */
	public static int getDeadLockCount() {
		final ThreadMXBean th = ManagementFactory.getThreadMXBean();
		final long[] deadLockIds = th.findMonitorDeadlockedThreads();
		if (deadLockIds == null) {
			return 0;
		} else {
			return deadLockIds.length;
		}

	}

	public String getThreadInfoByNum(int i) {
		final long[] threadIds = threadBean.getAllThreadIds();
		if (i >= threadIds.length) {
			return null;
		}
		final long tid = threadIds[i];

		final ThreadInfo info = threadBean.getThreadInfo(tid, stackDepth);
		final StringBuilder builder = new StringBuilder();
		final boolean contention = threadBean.isThreadContentionMonitoringEnabled();
		this.threadDump(builder, info, contention);
		return builder.toString();
	}

	public String getThreadInfoByContain(String contain) {
		final boolean contention = threadBean.isThreadContentionMonitoringEnabled();
		final long[] threadIds = threadBean.getAllThreadIds();
		final StringBuilder builder = new StringBuilder();
		for (final long tid : threadIds) {
			final ThreadInfo info = threadBean.getThreadInfo(tid, stackDepth);
			if (info.getThreadName().contains(contain)) {
				this.threadDump(builder, info, contention);
			}
		}

		return builder.toString();
	}

	private void threadDump(StringBuilder builder, ThreadInfo info,
			boolean contention) {
		if (info == null) {
			builder.append("  Inactive");
			builder.append("\n");
			return;
		}
		builder.append("Thread "
				+ getTaskName(info.getThreadId(), info.getThreadName()) + ":");
		builder.append("\n");
		final Thread.State state = info.getThreadState();
		builder.append("  State: " + state);
		builder.append("\n");
		builder.append("  Blocked count: " + info.getBlockedCount());
		builder.append("\n");
		builder.append("  Waited count: " + info.getWaitedCount());
		builder.append("\n");
		if (contention) {
			builder.append("  Blocked time: " + info.getBlockedTime());
			builder.append("\n");
			builder.append("  Waited time: " + info.getWaitedTime());
			builder.append("\n");
		}
		if (state == Thread.State.WAITING) {
			builder.append("  Waiting on " + info.getLockName());
			builder.append("\n");
		} else if (state == Thread.State.BLOCKED) {
			builder.append("  Blocked on " + info.getLockName());
			builder.append("\n");
			builder.append("  Blocked by "
					+ getTaskName(info.getLockOwnerId(),
							info.getLockOwnerName()));
			builder.append("\n");
		}
		builder.append("  Stack:");
		builder.append("\n");
		for (final StackTraceElement frame : info.getStackTrace()) {
			builder.append("    " + frame.toString());
			builder.append("\n");
		}
	}

	/**
	 * Print all of the thread's information and stack traces.
	 */
	public String getThreadInfo() {

		final boolean contention = threadBean.isThreadContentionMonitoringEnabled();
		final long[] threadIds = threadBean.getAllThreadIds();

		final StringBuilder builder = new StringBuilder("Process Thread Dump: ");
		builder.append("\n");
		builder.append(threadIds.length + " active threads");
		builder.append("\n");
		for (final long tid : threadIds) {
			final ThreadInfo info = threadBean.getThreadInfo(tid, stackDepth);
			this.threadDump(builder, info, contention);
		}
		return builder.toString();
	}

	private String getTaskName(long id, String name) {
		if (name == null) {
			return Long.toString(id);
		}
		return id + " (" + name + ")";
	}

}
