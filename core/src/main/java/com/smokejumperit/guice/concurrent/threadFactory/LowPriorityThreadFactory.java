package com.smokejumperit.guice.concurrent.threadFactory;

import com.google.inject.Singleton;

/**
 * Thread factory that always generates threads with priority {@link ThreadPriority#LOW}.
 */
@Singleton
public class LowPriorityThreadFactory extends PriorizitedThreadFactory {

	@Override
	public ThreadPriority getPriority(Runnable r) {
		return ThreadPriority.LOW;
	}

}
