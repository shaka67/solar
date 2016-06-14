/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.concurrent;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import com.ouer.solar.logger.CachedLogger;

/**
 * CompositeLock Abortable Lock
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年8月1日 下午2:03:54
 */
public class CompositeLock extends CachedLogger implements Lock {

    private static final int SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MIN_BACKOFF = 1024;
    private static final int MAX_BACKOFF = 256 * MIN_BACKOFF;
    AtomicStampedReference<QNode> tail;
    QNode[] waiting;
    Random random;

    ThreadLocal<QNode> myNode = new ThreadLocal<QNode>() {
        @Override
		protected QNode initialValue() {
            return null;
        };
    };

    public CompositeLock() {
        this(SIZE);
    }

    public CompositeLock(int watingSize) {
        tail = new AtomicStampedReference<QNode>(null, 0);
        random = new Random();
        waiting = new QNode[watingSize];
        for (int i = 0; i < waiting.length; i++) {
            waiting[i] = new QNode();
        }
    }

    @Override
	public void lock() {
        try {
            tryLock(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            logger.error("InterruptedException error", ex);
            Thread.currentThread().interrupt();
        }
    }

    @Override
	public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
	public boolean tryLock() {
        try {
            return tryLock(0, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            logger.error("InterruptedException error", ex);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long patience = TimeUnit.MILLISECONDS.convert(time, unit);
        long startTime = System.currentTimeMillis();
        Backoff backoff = new Backoff(MIN_BACKOFF, MAX_BACKOFF);
        try {
            QNode node = acquireQNode(backoff, startTime, patience);
            QNode pred = spliceQNode(node, startTime, patience);
            waitForPredecessor(pred, node, startTime, patience);
            return true;
        } catch (TimeoutException e) {
            logger.error("TimeoutException error", e);
            return false;
        }
    }

    @Override
	public void unlock() {
        QNode acqNode = myNode.get();
        acqNode.state.set(State.RELEASED);
    }

    @Override
	public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    private boolean timeout(long startTime, long patience) {
        return System.currentTimeMillis() - startTime > patience;
    }

    private QNode acquireQNode(Backoff backoff, long startTime, long patience) throws TimeoutException,
            InterruptedException {
        QNode node = waiting[random.nextInt(SIZE)];
        QNode currTail;
        int[] currStamp = { 0 };
        while (true) {
            if (node.state.compareAndSet(State.FREE, State.WAITING)) {
                return node;
            }
            currTail = tail.get(currStamp);
            State state = node.state.get();
            if (state == State.ABORTED || state == State.RELEASED) {
                if (node == currTail) {
                    QNode myPred = null;
                    if (state == State.ABORTED) {
                        myPred = node.pred;
                    }
                    if (tail.compareAndSet(currTail, myPred, currStamp[0], currStamp[0] + 1)) {
                        node.state.set(State.WAITING);
                        return node;
                    }
                }
            }
            backoff.backoff();
            if (timeout(patience, startTime)) {
                throw new TimeoutException();
            }
        }
    }

    private QNode spliceQNode(QNode node, long startTime, long patience) throws TimeoutException {
        QNode currTail;
        int[] currStamp = { 0 };
        // splice node into queue
        do {
            currTail = tail.get(currStamp);
            if (timeout(startTime, patience)) {
                node.state.set(State.FREE);
                throw new TimeoutException();
            }
        } while (!tail.compareAndSet(currTail, node, currStamp[0], currStamp[0] + 1));
        return currTail;
    }

    private void waitForPredecessor(QNode pred, QNode node, long startTime, long patience) throws TimeoutException {
        // wait for predecessor to release lock
        // int[] stamp = { 0 };
        if (pred == null) {
            myNode.set(node);
            return;
        }
        State predState = pred.state.get();
        while (predState != State.RELEASED) {
            if (predState == State.ABORTED) {
                QNode temp = pred;
                pred = pred.pred;
                temp.state.set(State.FREE);
            }
            if (timeout(patience, startTime)) {
                node.pred = pred;
                node.state.set(State.ABORTED);
                throw new TimeoutException();
            }
            predState = pred.state.get();
        }
        pred.state.set(State.FREE);
        myNode.set(node);
        return;
    }

    /*
     * Internal classes
     */
    enum State {
        FREE, WAITING, RELEASED, ABORTED
    };

    class QNode {
        AtomicReference<State> state;
        QNode pred;

        public QNode() {
            state = new AtomicReference<State>(State.FREE);
        }
    }
}
