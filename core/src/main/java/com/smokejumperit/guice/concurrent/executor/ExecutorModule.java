package com.smokejumperit.guice.concurrent.executor;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import com.google.inject.AbstractModule;
import com.google.inject.Key;

/**
 * Provides binding for {@link Executor}, {@link ExecutorService}, and {@link ThreadPoolExecutor}
 * instances, and for versions with {@link Background}, {@link Process}, and {@link Read}, and
 * {@link Write} annotations. Also, provides implementations for {@link ScheduledExecutorService}
 * and {@link ScheduledThreadPoolExecutor}.
 */
public class ExecutorModule extends AbstractModule {

	public static final int NUM_PROCESSORS = Runtime.getRuntime().availableProcessors();

	@Override
	protected void configure() {
		doBinding("read", getReadExecutorService(), Read.class, ReadExecutorService.class);
		doBinding("write", getWriteExecutorService(), Write.class, WriteExecutorService.class);
		doBinding("process", getProcessExecutorService(), Process.class, ProcessExecutorService.class);
		doBinding("background", getBackgroundExecutorService(), Background.class,
				BackgroundExecutorService.class);

		bind(RejectedExecutionHandler.class).to(getRejectedExecutionHandler());

		bind(Executor.class).to(getDefaultExecutor());
		bind(ExecutorService.class).to(getDefaultExecutorService());
		bind(ThreadPoolExecutor.class).to(getThreadPoolExecutor());
		bind(ScheduledExecutorService.class).to(getDefaultScheduledExecutorService());
		bind(ScheduledThreadPoolExecutor.class).to(getScheduledThreadPoolExecutor());
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private <V extends ExecutorService> void doBinding(String humanReadableType,
			Key<? extends ExecutorService> executorKey, Class<? extends Annotation> annotation,
			Class<V> type) {
		Objects.requireNonNull(executorKey, "key for " + humanReadableType + " executor service");
		Objects.requireNonNull(annotation, "annotation for " + humanReadableType + " executor service");
		Objects.requireNonNull(type, "type for " + humanReadableType + " executor service");
		bind(executorKey);
		Class<?> keyType = executorKey.getTypeLiteral().getRawType();
		if (type.isAssignableFrom(keyType) && !type.equals(keyType)) {
			bind(type).to((Key<? extends V>) executorKey);
		}
		final Class<? extends Executor>[] classes = new Class[]{Executor.class, ExecutorService.class,
				ThreadPoolExecutor.class};
		for (Class<? extends Executor> clazz : classes) {
			if (clazz.isAssignableFrom(keyType)) {
				bind(Key.get(clazz, annotation)).to((Key) executorKey);
			}
		}
	}

	/**
	 * Provide the {@link RejectedExecutionHandler} that classes should use. By default, this is
	 * {@link ThreadPoolExecutor.CallerRunsPolicy}.
	 *
	 * @return The key referring to the handler to use; never {@code null}
	 */
	protected Key<? extends RejectedExecutionHandler> getRejectedExecutionHandler() {
		return Key.get(ThreadPoolExecutor.CallerRunsPolicy.class);
	}

	/**
	 * Provides the instance to use for bare {@link ThreadPoolExecutor} instances. By default, this is
	 * an instance of {@link WriteExecutorService}.
	 *
	 * @return The key referring to the executor to use; never {@code null}
	 */
	protected Key<? extends ThreadPoolExecutor> getThreadPoolExecutor() {
		return Key.get(WriteExecutorService.class);
	}

	/**
	 * Provides the mapping to use for {@link Background}-annotated {@link Executor} and
	 * {@link ExecutorService} instances. By default, this is an instance of
	 * {@link BackgroundExecutorService}. If you return a type that is assignable to
	 * {@link BackgroundExecutorService}, that type is also bound to the
	 * {@link BackgroundExecutorService} class itself. If you return a key with a type that implements
	 * {@link ThreadPoolExecutor}, it is also bound to {@code @Background ThreadPoolExecutor}.
	 *
	 * @return The key referring to the executor to use; never {@code null}
	 */
	protected Key<? extends ExecutorService> getBackgroundExecutorService() {
		return Key.get(BackgroundExecutorService.class);
	}

	/**
	 * Provides the mapping to use for {@link Read}-annotated {@link Executor} and
	 * {@link ExecutorService} instances. By default, this is an instance of
	 * {@link ReadExecutorService}. If you return a type that is assignable to
	 * {@link ReadExecutorService}, that type is also bound to the {@link ReadExecutorService} class
	 * itself. If you return a key with a type that implements {@link ThreadPoolExecutor}, it is also
	 * bound to {@code @Read ThreadPoolExecutor}.
	 *
	 * @return The key referring to the executor to use; never {@code null}
	 */
	protected Key<? extends ExecutorService> getReadExecutorService() {
		return Key.get(ReadExecutorService.class);
	}

	/**
	 * Provides the mapping to use for {@link Write}-annotated {@link Executor} and
	 * {@link ExecutorService} instances. By default, this is an instance of
	 * {@link WriteExecutorService}. If you return a type that is assignable to
	 * {@link WriteExecutorService}, that type is also bound to the {@link WriteExecutorService} class
	 * itself. If you return a key with a type that implements {@link ThreadPoolExecutor}, it is also
	 * bound to {@code @Write ThreadPoolExecutor}.
	 *
	 * @return The key referring to the executor to use; never {@code null}
	 */
	protected Key<? extends ExecutorService> getWriteExecutorService() {
		return Key.get(WriteExecutorService.class);
	}

	/**
	 * Provides the mapping to use for {@link Process}-annotated {@link Executor} and
	 * {@link ExecutorService} instances. By default, this is an instance of
	 * {@link ProcessExecutorService}. If you return a type that is assignable to
	 * {@link ProcessExecutorService}, that type is also bound to the {@link ProcessExecutorService}
	 * class itself. If you return a key with a type that implements {@link ThreadPoolExecutor}, it is
	 * also bound to {@code @Process ThreadPoolExecutor}.
	 *
	 * @return The key referring to the executor to use; never {@code null}
	 */
	protected Key<? extends ExecutorService> getProcessExecutorService() {
		return Key.get(ProcessExecutorService.class);
	}

	/**
	 * Provide the key to the default implementation of the {@link Executor} interface. By default,
	 * this is {@code ExecutorService}.
	 *
	 * @return The key referring to the executor to use; never {@code null}
	 */
	protected Key<? extends Executor> getDefaultExecutor() {
		return Key.get(ExecutorService.class);
	}

	/**
	 * Provide the key to the default implementation of the {@link ExecutorService} interface. By
	 * default, this is {@code @Process ExecutorService}.
	 *
	 * @return The key referring to the executor to use; never {@code null}
	 */
	protected Key<? extends ExecutorService> getDefaultExecutorService() {
		return Key.get(ExecutorService.class, Process.class);
	}

	/**
	 * Provide the key to the default implementation of the {@link ScheduledExecutorService}
	 * interface. By default, this is {@code ScheduledThreadPoolExecutor}.
	 *
	 * @return The key referring to the executor to use; never {@code null}
	 */
	protected Key<? extends ScheduledExecutorService> getDefaultScheduledExecutorService() {
		return Key.get(ScheduledThreadPoolExecutor.class);
	}

	/**
	 * Provides the key to generate {@link ScheduledThreadPoolExecutor} instances. By default, this is
	 * {@link ProcessLimitedScheduledThreadPoolExecutor}.
	 *
	 * @return The key referring to the executor to use; never {@code null}
	 */
	protected Key<? extends ScheduledThreadPoolExecutor> getScheduledThreadPoolExecutor() {
		return Key.get(ProcessLimitedScheduledThreadPoolExecutor.class);
	}

}
