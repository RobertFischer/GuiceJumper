package com.smokejumperit.guice.concurrent;

import java.util.Arrays;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.smokejumperit.guice.concurrent.executor.ExecutorModule;
import com.smokejumperit.guice.concurrent.threadFactory.ThreadFactoryModule;

/**
 * Installs the modules used for concurrency. The modules that are returned are the ones provided by
 * the {@code getXxxModule} methods.
 */
public class ConcurrentModule extends AbstractModule {

	/**
	 * Installs the modules to use. By default, this is the non-null elements of {@link #getModules()}
	 */
	@Override
	protected void configure() {
		Iterable<Module> modules = getModules();
		if (modules != null) {
			for (Module module : modules) {
				if (module != null)
					this.install(module);
			}
		}
	}

	/**
	 * Provides the modules to use. By default, this is the return values for the
	 * {@code getXxxModule()} defined on {@code ConcurrentModule}, any of which may be {@code null}.
	 */
	protected Iterable<Module> getModules() {
		return Arrays.asList(getExecutorModule(), getThreadFactoryModule());
	}

	/**
	 * Provides the {@link ExecutorModule} (or replacement) to use: return {@code null} to signify
	 * that no module should be used.
	 */
	protected Module getExecutorModule() {
		return new ExecutorModule();
	}

	/**
	 * Provides the {@link ThreadFactoryModule} (or replacement) to use: return {@code null} to
	 * signify that no module should be used.
	 */
	protected Module getThreadFactoryModule() {
		return new ThreadFactoryModule();
	}

}
