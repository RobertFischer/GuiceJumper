package com.smokejumperit.guice.concurrent;

import java.util.concurrent.ThreadFactory;

import com.google.inject.AbstractModule;
import com.smokejumperit.guice.concurrent.threadFactory.HighPriorityThreadFactory;
import com.smokejumperit.guice.concurrent.threadFactory.LowPriorityThreadFactory;
import com.smokejumperit.guice.concurrent.threadFactory.MediumPriorityThreadFactory;
import com.smokejumperit.guice.concurrent.threadFactory.ThreadFactoryModule;

/**
 * Installs the modules used for concurrency. The modules that are returned are the ones provided by
 * the {@code getXxxModule} methods.
 */
public class ConcurrentModule extends AbstractModule {

	@Override
	protected void configure() {
		this.install(getThreadFactoryModule());
	}

	/**
	 * Provides the {@link ThreadFactoryModule} (or replacement) to use.
	 */
	protected AbstractModule getThreadFactoryModule() {
		return new ThreadFactoryModule();
	}

}
