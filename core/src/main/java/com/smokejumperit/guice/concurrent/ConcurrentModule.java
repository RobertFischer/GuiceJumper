package com.smokejumperit.guice.concurrent;

import com.smokejumperit.guice.InstallingModule;
import com.smokejumperit.guice.concurrent.executor.ExecutorModule;
import com.smokejumperit.guice.concurrent.threadFactory.ThreadFactoryModule;

/**
 * Installs the modules used for concurrency.
 */
public class ConcurrentModule extends InstallingModule {

	/**
	 * Installs a default implementation of the modules.
	 */
	public ConcurrentModule() {
		this(new ThreadFactoryModule(), new ExecutorModule());
	}

	/**
	 * Installs the given modules.
	 * 
	 * @param threadFactoryModule
	 *          The {@link ThreadFactoryModule} to install; may be {@code null} to not install the
	 *          module.
	 * @param executorModule
	 *          The {@link ExecutorModule} to install; may be {@code null} to install the module.
	 */
	public ConcurrentModule(ThreadFactoryModule threadFactoryModule, ExecutorModule executorModule) {
		super(threadFactoryModule, executorModule);
	}
}
