package com.smokejumperit.guice.concurrent.executor;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.smokejumperit.guice.concurrent.threadFactory.HighPriority;
import com.smokejumperit.guice.concurrent.threadFactory.LowPriority;
import com.smokejumperit.guice.concurrent.threadFactory.MediumPriority;

/**
 * Provides binding for {@link ThreadFactory} instances, and for versions with {@link LowPriority},
 * {@link MediumPriority}, and {@link HighPriority} set.
 */
public class ExecutorModule extends AbstractModule {

	@Override
	protected void configure() {
		doBinding("read", getReadExecutorService(), Read.class, ReadExecutorService.class);
		doBinding("write", getWriteExecutorService(), Write.class, WriteExecutorService.class);
		doBinding("process", getProcessExecutorService(), Process.class, ProcessExecutorService.class);

		bind(RejectedExecutionHandler.class).toInstance(getRejectedExecutionHandler());
		bind(Key.get(BlockingQueue.class, Process.class)).to(getProcessBlockingQueueClass());

		bind(Executor.class).to(getDefaultExecutorKey());
		bind(ExecutorService.class).to(getDefaultExecutorServiceKey());
		bind(ScheduledExecutorService.class).to(getDefaultScheduledExecutorServiceKey());
		bind(ScheduledThreadPoolExecutor.class).toProvider(getScheduledThreadPoolExecutor());
	}

	private <V extends ExecutorService> void doBinding(String humanReadableType,
			ExecutorService executor,
			Class<? extends Annotation> annotation, Class<V> type) {
		Objects.requireNonNull(executor, humanReadableType + " executor service");
		Objects.requireNonNull(annotation, "annotation for " + humanReadableType + " executor service");
		Objects.requireNonNull(type, "type for " + humanReadableType + " executor service");
		bind(Key.get(ExecutorService.class, annotation)).toInstance(executor);
		if (type.isAssignableFrom(executor.getClass())) {
			V castExecutor = type.cast(executor);
			bind(type).toInstance(castExecutor);
		} else {
			bind(type).in(Singleton.class);
		}
	}

	/**
	 * Provides the key to generate {@link Process}-annotated {@link BlockingQueue} arguments. By
	 * default, this is {@link ProcessBlockingQueue}.
	 */
	protected Key<? extends BlockingQueue<Runnable>> getProcessBlockingQueueClass() {
		return Key.get(ProcessBlockingQueue.class);
	}

	/**
	 * Provide the {@link RejectedExecutionHandler} that classes should use. By default, this is
	 * {@link ThreadPoolExecutor.CallerRunsPolicy}.
	 */
	protected RejectedExecutionHandler getRejectedExecutionHandler() {
		return new ThreadPoolExecutor.CallerRunsPolicy();
	}

	/**
	 * Provides the instance to use for {@link Read}-annotated {@link Executor} and
	 * {@link ExecutorService} instances. By default, this is an instance of
	 * {@link ReadExecutorService}. If you return an instance of {@link ReadExecutorService}, it is
	 * also bound to the {@link ReadExecutorService} class itself.
	 */
	protected ExecutorService getReadExecutorService() {
		return new ReadExecutorService();
	}

	/**
	 * Provides the instance to use for {@link Write}-annotated {@link Executor} and
	 * {@link ExecutorService} instances. By default, this is an instance of
	 * {@link WriteExecutorService}. If you return an instance of {@link WriteExecutorService}, it is
	 * also bound to the {@link WriteExecutorService} class itself.
	 */
	protected ExecutorService getWriteExecutorService() {
		return new WriteExecutorService();
	}

	/**
	 * Provides the instance to use for {@link Process}-annotated {@link Executor} and
	 * {@link ExecutorService} instances. By default, this is an instance of
	 * {@link ProcessExecutorService}. If you return an instance of {@link ProcessExecutorService}, it
	 * is also bound to the {@link ProcessExecutorService} class itself.
	 */
	protected ExecutorService getProcessExecutorService() {
		return new ProcessExecutorService();
	}

	/**
	 * Provide the key to the default implementation of the {@link Executor} interface. By default,
	 * this is {@code ExecutorService}.
	 */
	protected Key<? extends Executor> getDefaultExecutorKey() {
		return Key.get(ExecutorService.class);
	}

	/**
	 * Provide the key to the default implementation of the {@link ExecutorService} interface. By
	 * default, this is {@code @Process ExecutorService}.
	 */
	protected Key<? extends ExecutorService> getDefaultExecutorServiceKey() {
		return Key.get(ExecutorService.class, Process.class);
	}

	/**
	 * Provide the key to the default implementation of the {@link ScheduledExecutorService}
	 * interface. By default, this is {@code ScheduledThreadPoolExecutor}.
	 */
	protected Key<? extends ScheduledExecutorService> getDefaultScheduledExecutorServiceKey() {
		return Key.get(ScheduledThreadPoolExecutor.class);
	}

	/**
	 * Provides the provider to generate {@link ScheduledThreadPoolExecutor} instances. By default,
	 * this generates a new {@link ProcessLimitedScheduledThreadPoolExecutor} for each invocation.
	 */
	protected <V extends ScheduledThreadPoolExecutor> Provider<V> getScheduledThreadPoolExecutor() {
		return new Provider<V>() {
			@Override
			public V get() {
				return new ProcessLimitedScheduledThreadPoolExecutor();
			}
		};
	}

}
