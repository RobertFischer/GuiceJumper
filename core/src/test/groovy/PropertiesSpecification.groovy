package com.smokejumperit.guice.properties

import spock.lang.*
import com.google.inject.*
import com.google.inject.name.*
import java.util.concurrent.*
import java.nio.charset.*

class PropertiesSpecification extends Specification {

	static final String STRING_KEY = "value.string"
	static final String STRING_VALUE = "This is a string!"
	static final String INT_KEY = "value.int"
	static final String INT_VALUE = "${Integer.MAX_VALUE}"
	static final String LONG_KEY = "value.long"
	static final String LONG_VALUE = "${Long.MAX_VALUE}"
	static final String CHARSET_KEY = "value.charset"
	static final String CHARSET_VALUE = "US-ASCII"

	void setupSpec() {
		[
			"${STRING_KEY}": STRING_VALUE,
			"${INT_KEY}": INT_VALUE,
			"${LONG_KEY}": LONG_VALUE,
			"${CHARSET_KEY}": CHARSET_VALUE 
		].each { String key, String value ->
			System.properties[key] = value
		}
	}

	PropertiesModule fixture = new PropertiesModule()
	Injector injector = Guice.createInjector(fixture)

	void "sanity check"() {
		expect:
		System.properties[STRING_KEY] == STRING_VALUE
		System.properties[INT_KEY] == INT_VALUE
		System.properties[LONG_KEY] == LONG_VALUE
		System.properties[CHARSET_KEY] == CHARSET_VALUE
	}

	void "check string property"() {
		when:
		String value = injector.getInstance(Key.get(String, Names.named(STRING_KEY)))

		then:
		STRING_VALUE == value
	}

	void "check int property"() {
		when:
		int value = injector.getInstance(Key.get(Integer.TYPE, Names.named(INT_KEY)))
		int expected = Integer.parseInt(INT_VALUE)

		then:
		expected == value
	}

	void "check long property"() {
		when:
		long value = injector.getInstance(Key.get(Long.TYPE, Names.named(LONG_KEY)))
		long expected = Long.parseLong(LONG_VALUE)

		then:
		expected == value
	}

	void "check charset property"() {
		when:
		Charset value = injector.getInstance(Key.get(Charset, Names.named(CHARSET_KEY)))
		Charset expected = Charset.forName(CHARSET_VALUE)

		then:
		expected == value
	}

	void "check error when int is too long"() {
		when:
		injector.getInstance(Key.get(Integer.TYPE, Names.named(LONG_KEY)))

		then:
		ProvisionException e = thrown()
		"$e".contains(LONG_KEY)
		"$e".contains(LONG_VALUE)
	}

}
