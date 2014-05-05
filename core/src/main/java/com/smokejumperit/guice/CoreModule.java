package com.smokejumperit.guice;

import com.google.inject.AbstractModule;
import com.smokejumperit.guice.concurrent.ConcurrentModule;

public class CoreModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new ConcurrentModule());
	}

}
