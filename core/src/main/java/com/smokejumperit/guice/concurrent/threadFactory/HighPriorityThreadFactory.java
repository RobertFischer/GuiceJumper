package com.smokejumperit.guice.concurrent.threadFactory;

/**
 * Thread factory that always generates threads with priority {@link ThreadPriority#HIGH}.
 */
public class HighPriorityThreadFactory extends PriorizitedThreadFactory {

	public static final HighPriorityThreadFactory INSTANCE = new HighPriorityThreadFactory();

	@Override
	public ThreadPriority getPriority(Runnable r) {
		return ThreadPriority.HIGH;
	}

}
