package com.smokejumperit.guice.concurrent.executor;

import static com.smokejumperit.guice.concurrent.executor.ExecutorModule.NUM_PROCESSORS;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smokejumperit.guice.concurrent.threadFactory.LowPriority;

@Singleton
public class ProcessLimitedScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

	@Inject
	public ProcessLimitedScheduledThreadPoolExecutor(@LowPriority ThreadFactory threadFactory,
			RejectedExecutionHandler rejectHandler) {
		super(NUM_PROCESSORS, threadFactory, rejectHandler);
	}

}
