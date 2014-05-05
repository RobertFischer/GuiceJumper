package com.smokejumperit.guice.concurrent

import spock.lang.*
import com.google.inject.*
import java.util.concurrent.*

class ConcurrentModuleSpecification extends Specification {

	ConcurrentModule fixture = new ConcurrentModule()
	Injector injector = Guice.createInjector(fixture)

	void "test sample classes are available"() {
		expect:
		injector.getInstance(ThreadFactory)
		injector.getInstance(ExecutorService)
	}

}
