package com.smokejumperit.guice.concurrent.threadFactory;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A thread factory that generates a new thread using a given priority. The priority is given by
 * {@link #getPriority(Runnable)}. The daemon status, classloader, uncaught exception handler, etc.,
 * are all left to their default values.
 */
public abstract class PriorizitedThreadFactory implements ThreadFactory {

	private static final AtomicLong counter = new AtomicLong(0);

	/**
	 * Generates a new thread using the given name and priority.
	 * 
	 * @throws NullPointerException
	 *           If the argument is {@code null}.
	 */
	@Override
	public Thread newThread(Runnable r) {
		Objects.requireNonNull(r, "Runnable to execute");
		ThreadPriority priority = getPriority(r);
		Objects.requireNonNull(priority, "thread priority");
		String name = getName(r, priority);
		Objects.requireNonNull(name, "thread name");

		final Thread t = new Thread();
		t.setPriority(priority.asInt());
		t.setName(name);
		return t;
	}

	/**
	 * Provides a name for the thread that will execute the given runnable at the given priority. In
	 * the base implementation, it ignores the runnable and names the value
	 * "{priority} Priority Thread #{nextCounterValueInHex}".
	 * 
	 * @param r
	 *          The runnable to execute.
	 * @param priority
	 *          The priority to execute at.
	 * @return A non-{@code null} name for that thread.
	 * @throws NullPointerException
	 *           If either argument is {@code null}.
	 */
	public String getName(Runnable r, ThreadPriority priority) {
		return priority.getHumanString() + " Priority Thread #"
				+ Long.toHexString(counter.incrementAndGet());
	}

	public abstract ThreadPriority getPriority(Runnable r);

}
