package com.smokejumperit.guice.concurrent.threadFactory

import spock.lang.*
import com.google.inject.*
import java.util.concurrent.*

class ThreadFactoryModuleSpecification extends Specification {

	ThreadFactoryModule fixture = new ThreadFactoryModule()
	Injector injector = Guice.createInjector(fixture)

	void "test implementations are available"() {
		expect:
		injector.getInstance(ThreadFactory)
		injector.getInstance(Key.get(ThreadFactory, LowPriority))
		injector.getInstance(Key.get(ThreadFactory, MediumPriority))
		injector.getInstance(Key.get(ThreadFactory, HighPriority))
	}

}
