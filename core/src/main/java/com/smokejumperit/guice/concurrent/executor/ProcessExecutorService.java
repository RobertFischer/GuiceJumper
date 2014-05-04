package com.smokejumperit.guice.concurrent.executor;

import static com.smokejumperit.guice.concurrent.executor.ExecutorModule.NUM_PROCESSORS;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smokejumperit.guice.concurrent.threadFactory.MediumPriority;

/**
 * An {@link ExecutorService} which is used for processing operations. Processing operations are
 * presumed to be CPU bound, and we therefore use a thread pool which is sized to the number of
 * processors. Since they are CPU bound, they are default priority: {@link ReadExecutorService}
 * threads should defer to this class' processing, and {@link WriteExecutorService} threads should
 * be able to preempt this class' processing to perform their write operations.
 * 
 * @author Robert Fischer
 */
@Singleton
public class ProcessExecutorService extends ThreadPoolExecutor implements ExecutorService {

	private static volatile int NEW_INSTANCE_SIZE = NUM_PROCESSORS * 1024;

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
	public ProcessExecutorService(@MediumPriority ThreadFactory threadFactory,
			RejectedExecutionHandler rejectHandler) {
		super(NUM_PROCESSORS, NUM_PROCESSORS * 2, 1L, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(NEW_INSTANCE_SIZE), threadFactory, rejectHandler);
	}

}
