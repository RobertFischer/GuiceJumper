package com.smokejumperit.guice.concurrent.executor

import spock.lang.*
import com.google.inject.*
import java.util.concurrent.*
import com.smokejumperit.guice.concurrent.threadFactory.*

class ExecutorModuleSpecification extends Specification {

	ExecutorModule fixture = new ExecutorModule()
	Injector injector = Guice.createInjector(new ThreadFactoryModule(), fixture)

	void "test interface and base class implementations are available"() {
		expect:
		injector.getInstance(RejectedExecutionHandler)
		injector.getInstance(Executor)
		injector.getInstance(ExecutorService)
		injector.getInstance(ThreadPoolExecutor)
		injector.getInstance(ScheduledExecutorService)
		injector.getInstance(ScheduledThreadPoolExecutor)
	}

	void "test cartesian product of annotations interfaces"() {
		when:
		List<Class> annotations = [ Background, Process, Read, Write ]
		List<Class> interfaces = [ Executor, ExecutorService, ThreadPoolExecutor ]

		then:
		annotations.each { Class annotationClass ->
			interfaces.each { Class interfaceClass -> 
				Key key = Key.get(interfaceClass, annotationClass)
				assert injector.getInstance(key) : "Could not find implementation for key: $key"
			}
		}
	}

}
