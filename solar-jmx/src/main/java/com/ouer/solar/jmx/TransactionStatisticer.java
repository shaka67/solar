/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.jmx;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class TransactionStatisticer {

	private static class Metric {

		public AtomicInteger handledTransaction = new AtomicInteger(0);
		public AtomicInteger finishedTransaction = new AtomicInteger(0);

		public AtomicInteger handledThroughput = new AtomicInteger(0);
		public AtomicInteger finishedThroughput = new AtomicInteger(0);

		private int lastHandledTransaction = 0;
		private int lastFinishedTransaction = 0;

		public void calculatePerformance(long interval) {
			final int handledTransactionNow = handledTransaction.get();
			final int finishedTransactionNow = finishedTransaction.get();

			handledThroughput
					.set((int) ((handledTransactionNow - lastHandledTransaction) * 1000 / interval));
			lastHandledTransaction = handledTransactionNow;

			finishedThroughput
					.set((int) ((finishedTransactionNow - lastFinishedTransaction) * 1000 / interval));
			lastFinishedTransaction = finishedTransactionNow;
		}
	}

	private final Metric metric = new Metric();

	private final Timer timer = new Timer();

	private long caculateInterval = 1000;
	private long lastTimestamp = 0;

	private void calculatePerformance() {
		final long now = System.currentTimeMillis();
		final long interval = now - lastTimestamp;

		if (interval > 0) {// 避免除0
			metric.calculatePerformance(interval);
		}
		lastTimestamp = now;
	}

	public void start() {
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				calculatePerformance();
			}
		}, caculateInterval, caculateInterval);
	}

	public void stop() {
		timer.cancel();
	}

	public long getCaculateInterval() {
		return caculateInterval;
	}

	public void setCaculateInterval(long caculateInterval) {
		this.caculateInterval = caculateInterval * 1000;
	}

	public long getLastTimestamp() {
		return lastTimestamp;
	}

	public AtomicInteger getHandledTransaction() {
		return metric.handledTransaction;
	}

	public AtomicInteger getFinishedTransaction() {
		return metric.finishedTransaction;
	}

	public AtomicInteger getHandledThroughput() {
		return metric.handledThroughput;
	}

	public AtomicInteger getFinishedThroughput() {
		return metric.finishedThroughput;
	}

	public void incHandledTransactionEnd() {
		getFinishedTransaction().incrementAndGet();
	}

	public void incHandledTransactionStart() {
		getHandledTransaction().incrementAndGet();
	}

}
