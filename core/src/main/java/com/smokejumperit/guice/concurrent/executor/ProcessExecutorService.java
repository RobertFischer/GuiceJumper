package com.smokejumperit.guice.concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.smokejumperit.guice.concurrent.threadFactory.HighPriority;

/**
 * An {@link ExecutorService} which is used for processing operations. Processing operations are
 * presumed to be CPU bound, and we therefore use a thread pool which is sized to the number of
 * processors. Since they are CPU bound, they are default priority: {@link ReadExecutorService}
 * threads should defer to this class' processing, and {@link WriteExecutorService} threads should
 * be able to preempt this class' processing to perform their write operations.
 * 
 * @author Robert Fischer
 */
public class ProcessExecutorService extends ThreadPoolExecutor implements ExecutorService {

	private static final int PROCESSOR_COUNT = Math
			.max(1, Runtime.getRuntime().availableProcessors());

	@Inject
	public ProcessExecutorService(@Process ThreadFactory threadFactory,
			RejectedExecutionHandler rejectHandler) {
		super(PROCESSOR_COUNT, PROCESSOR_COUNT * 2, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(Integer.MAX_VALUE), threadFactory, 
	}

}
