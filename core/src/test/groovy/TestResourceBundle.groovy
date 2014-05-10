package com.smokejumperit.guice.i18n

import spock.lang.*
import com.google.inject.*
import com.google.inject.name.*
import java.util.concurrent.*
import java.nio.charset.*
import groovy.transform.Canonical

@Canonical
class TestResourceBundle extends ListResourceBundle {

	Map<String,String> messages

	protected Object[][] getContents() {
		messages.inject([]) { list,k,v ->
			list << [k,v]
		} as Object[][]
	}


}
