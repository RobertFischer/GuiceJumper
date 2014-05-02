package com.smokejumperit.guice.concurrent.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * A default class for {@link Process}-annotated {@link BlockingQueue} instances, such as for the
 * {@link ProcessExecutorService}. New instances will be of {@link #getNewInstanceSize()} size,
 * which can be changed by calling {@link #setNewInstanceSize(int)}.
 */
public class ProcessBlockingQueue extends ArrayBlockingQueue<Runnable> {

	private static final long serialVersionUID = 2579651412756567979L;
	private static volatile int NEW_INSTANCE_SIZE = 1024;

	public ProcessBlockingQueue() {
		super(NEW_INSTANCE_SIZE);
	}

	public static int getNewInstanceSize() {
		return NEW_INSTANCE_SIZE;
	}

	public static void setNewInstanceSize(int newInstanceSize) {
		if (newInstanceSize <= 0) {
			throw new IllegalArgumentException("Size of new instances must be at least 1, but was "
					+ newInstanceSize);
		}
		NEW_INSTANCE_SIZE = newInstanceSize;
	}

}
