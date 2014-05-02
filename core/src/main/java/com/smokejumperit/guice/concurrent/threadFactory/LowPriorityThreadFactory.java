package com.smokejumperit.guice.concurrent.threadFactory;

/**
 * Thread factory that always generates threads with priority {@link ThreadPriority#LOW}.
 */
public class LowPriorityThreadFactory extends PriorizitedThreadFactory {

	public static final LowPriorityThreadFactory INSTANCE = new LowPriorityThreadFactory();

	@Override
	public ThreadPriority getPriority(Runnable r) {
		return ThreadPriority.LOW;
	}

}
