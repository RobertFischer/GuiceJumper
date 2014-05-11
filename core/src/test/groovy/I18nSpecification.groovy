package com.smokejumperit.guice.i18n

import spock.lang.*
import com.google.inject.*
import com.google.inject.name.*
import java.util.concurrent.*
import java.nio.charset.*
import groovy.transform.Canonical

class I18nSpecification extends Specification {

	static final String KEY = "hello"
	static final String VALUE = "world"
	I18nModule fixture = new I18nModule(new TestResourceBundle([
		"$KEY": "$VALUE"
	]))
	Injector injector = Guice.createInjector(fixture)

	@Canonical
	static class Holder {
		@Inject @Localized("hello") String value
	}

	void "check key"() {
		when:
		String value = injector.getInstance(Key.get(String, new LocalizedImpl(KEY)))

		then:
		VALUE == value
	}

	void "check injection"() {
		when:
		def holder = injector.getInstance(Holder)

		then:
		VALUE == holder.value
	}

}
