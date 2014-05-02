package com.smokejumperit.guice.concurrent.threadFactory;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Key;

/**
 * Provides binding for {@link ThreadFactory} instances, and for versions with {@link LowPriority},
 * {@link MediumPriority}, and {@link HighPriority} set.
 */
public class ThreadFactoryModule extends AbstractModule {

	@Override
	protected void configure() {
		doBinding("low priority", getLowPriorityThreadFactory(), LowPriority.class,
				LowPriorityThreadFactory.class, LowPriorityThreadFactory.INSTANCE);
		doBinding("medium priority", getMediumPriorityThreadFactory(), MediumPriority.class,
				MediumPriorityThreadFactory.class, MediumPriorityThreadFactory.INSTANCE);
		doBinding("high priority", getHighPriorityThreadFactory(), HighPriority.class,
				HighPriorityThreadFactory.class, HighPriorityThreadFactory.INSTANCE);

		Key<? extends ThreadFactory> defaultThreadFactoryKey = getDefaultThreadFactoryKey();
		Objects.requireNonNull(defaultThreadFactoryKey, "default thread factory key");
		bind(ThreadFactory.class).to(defaultThreadFactoryKey);
	}

	private <V extends ThreadFactory> void doBinding(String humanReadableType, ThreadFactory factory,
			Class<? extends Annotation> annotation, Class<V> type, V defaultOfType) {
		Objects.requireNonNull(factory, humanReadableType + " thread factory");
		Objects.requireNonNull(annotation, "annotation for " + humanReadableType + " thread factory");
		Objects.requireNonNull(type, "type for " + humanReadableType + " thread factory");
		Objects.requireNonNull(defaultOfType, "default for " + type);
		bind(Key.get(ThreadFactory.class, annotation)).toInstance(factory);
		bind(type).toInstance(
				type.isAssignableFrom(factory.getClass()) ? type.cast(factory) : defaultOfType);
	}

	/**
	 * Provides the thread factory to use for {@link LowPriority}-annotated {@link ThreadFactory}
	 * instances. By default, this is {@link LowPriorityThreadFactory#INSTANCE}. If you return an
	 * instance of {@link LowPriorityThreadFactory}, it is also bound to the
	 * {@link LowPriorityThreadFactory} class itself.
	 */
	protected ThreadFactory getLowPriorityThreadFactory() {
		return LowPriorityThreadFactory.INSTANCE;
	}

	/**
	 * Provides the thread factory to use for {@link MediumPriority}-annotated {@link ThreadFactory}
	 * instances. By default, this is {@link MediumPriorityThreadFactory#INSTANCE}. If you return an
	 * instance of {@link MediumPriorityThreadFactory}, it is also bound to the
	 * {@link MediumPriorityThreadFactory} class itself.
	 */
	protected ThreadFactory getMediumPriorityThreadFactory() {
		return MediumPriorityThreadFactory.INSTANCE;
	}

	/**
	 * Provides the thread factory to use for {@link HighPriority}-annotated {@link ThreadFactory}
	 * instances. By default, this is {@link HighPriorityThreadFactory#INSTANCE}. If you return an
	 * instance of {@link HighPriorityThreadFactory}, it is also bound to the
	 * {@link HighPriorityThreadFactory} class itself.
	 */
	protected ThreadFactory getHighPriorityThreadFactory() {
		return HighPriorityThreadFactory.INSTANCE;
	}

	/**
	 * Provides the key to use to look up the default implementation for {@link ThreadFactory}
	 * instances. By default, this {@code @MediumPriority ThreadFactory}.
	 */
	protected Key<? extends ThreadFactory> getDefaultThreadFactoryKey() {
		return Key.get(ThreadFactory.class, MediumPriority.class);
	}

}
