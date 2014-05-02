package com.smokejumperit.guice.concurrent.threadFactory;

/**
 * Thread factory that always generates threads with priority {@link ThreadPriority#MEDIUM}.
 */
public class MediumPriorityThreadFactory extends PriorizitedThreadFactory {

	public static final MediumPriorityThreadFactory INSTANCE = new MediumPriorityThreadFactory();

	@Override
	public ThreadPriority getPriority(Runnable r) {
		return ThreadPriority.MEDIUM;
	}

}
