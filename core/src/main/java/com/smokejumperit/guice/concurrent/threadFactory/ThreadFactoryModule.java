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
		doBinding("low priority", getLowPriorityThreadFactory(), LowPriority.class);
		doBinding("medium priority", getMediumPriorityThreadFactory(), MediumPriority.class);
		doBinding("high priority", getHighPriorityThreadFactory(), HighPriority.class);

		Key<? extends ThreadFactory> defaultThreadFactoryKey = getDefaultThreadFactory();
		Objects.requireNonNull(defaultThreadFactoryKey, "default thread factory key");
		bind(ThreadFactory.class).to(defaultThreadFactoryKey);
	}

	private void doBinding(String humanReadableType, Key<? extends ThreadFactory> factoryKey,
			Class<? extends Annotation> annotation) {
		Objects.requireNonNull(factoryKey, "key for " + humanReadableType + " thread factory");
		Objects.requireNonNull(annotation, "annotation for " + humanReadableType + " thread factory");
		bind(Key.get(ThreadFactory.class, annotation)).to(factoryKey);
	}

	/**
	 * Provides the thread factory to use for {@link LowPriority}-annotated {@link ThreadFactory}
	 * instances. By default, this is {@link LowPriorityThreadFactory}. If you return an
	 * instance of {@link LowPriorityThreadFactory}, it is also bound to the
	 * {@link LowPriorityThreadFactory} class itself.
	 *
	 * @return The key to use to look up the thread factory; never {@code null}.
	 */
	protected Key<? extends ThreadFactory> getLowPriorityThreadFactory() {
		return Key.get(LowPriorityThreadFactory.class);
	}

	/**
	 * Provides the thread factory to use for {@link MediumPriority}-annotated {@link ThreadFactory}
	 * instances. By default, this is {@link MediumPriorityThreadFactory}. If you return an
	 * instance of {@link MediumPriorityThreadFactory}, it is also bound to the
	 * {@link MediumPriorityThreadFactory} class itself.
	 *
	 * @return The key to use to look up the thread factory; never {@code null}.
	 */
	protected Key<? extends ThreadFactory> getMediumPriorityThreadFactory() {
		return Key.get(MediumPriorityThreadFactory.class);
	}

	/**
	 * Provides the thread factory to use for {@link HighPriority}-annotated {@link ThreadFactory}
	 * instances. By default, this is {@link HighPriorityThreadFactory}. If you return an
	 * instance of {@link HighPriorityThreadFactory}, it is also bound to the
	 * {@link HighPriorityThreadFactory} class itself.
	 *
	 * @return The key to use to look up the thread factory; never {@code null}.
	 */
	protected Key<? extends ThreadFactory> getHighPriorityThreadFactory() {
		return Key.get(HighPriorityThreadFactory.class);
	}

	/**
	 * Provides the key to use to look up the default implementation for {@link ThreadFactory}
	 * instances. By default, this {@code @MediumPriority ThreadFactory}.
	 *
	 * @return The key to use to look up the thread factory; never {@code null}.
	 */
	protected Key<? extends ThreadFactory> getDefaultThreadFactory() {
		return Key.get(ThreadFactory.class, MediumPriority.class);
	}

}
