package com.smokejumperit.guice.concurrent.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smokejumperit.guice.concurrent.threadFactory.LowPriority;

@Singleton
public class BackgroundExecutorService extends ThreadPoolExecutor implements ExecutorService {

	private static volatile int NEW_INSTANCE_SIZE = 1024;

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

	@Inject
	public BackgroundExecutorService(@LowPriority ThreadFactory threadFactory,
			RejectedExecutionHandler rejectHandler) {
		super(1, Integer.MAX_VALUE, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
				NEW_INSTANCE_SIZE), threadFactory, rejectHandler);
	}

}
