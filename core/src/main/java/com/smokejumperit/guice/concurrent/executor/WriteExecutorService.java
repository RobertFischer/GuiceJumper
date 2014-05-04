package com.smokejumperit.guice.concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smokejumperit.guice.concurrent.threadFactory.HighPriority;

@Singleton
public class WriteExecutorService extends ThreadPoolExecutor implements ExecutorService {

	@Inject
	public WriteExecutorService(@HighPriority ThreadFactory threadFactory,
			RejectedExecutionHandler rejectHandler) {
		super(0, Integer.MAX_VALUE, 1L, TimeUnit.MINUTES, new SynchronousQueue<Runnable>(),
				threadFactory, rejectHandler);
	}

}
