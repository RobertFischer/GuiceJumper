package com.smokejumperit.guice.concurrent.threadFactory;

import com.google.inject.Singleton;

/**
 * Thread factory that always generates threads with priority {@link ThreadPriority#HIGH}.
 */
@Singleton
public class HighPriorityThreadFactory extends PriorizitedThreadFactory {

	@Override
	public ThreadPriority getPriority(Runnable r) {
		return ThreadPriority.HIGH;
	}

}
