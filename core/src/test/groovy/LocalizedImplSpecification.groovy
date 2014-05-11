package com.smokejumperit.guice.i18n

import spock.lang.*
import com.google.inject.*
import com.google.inject.name.*
import java.util.concurrent.*
import java.nio.charset.*
import groovy.transform.Canonical

class LocalizedImplSpecification extends Specification {

	@Canonical
	static class Holder {
		@Localized("hello") String value
	}

	void "check localized annotation is equal to localizedimpl"() {
		when:
		def impl = new LocalizedImpl("hello")
		def ann = Holder.getDeclaredField("value").getAnnotation(Localized)

		then:
		impl == ann
	}

}
