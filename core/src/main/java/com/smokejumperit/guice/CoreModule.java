package com.smokejumperit.guice;

import com.smokejumperit.guice.concurrent.ConcurrentModule;
import com.smokejumperit.guice.properties.PropertiesModule;

public class CoreModule extends InstallingModule {

	/**
	 * Provides the default set of core modules.
	 */
	public CoreModule() {
		this(new ConcurrentModule(), new PropertiesModule());
	}

	/**
	 * Provides those core modules that are passed into the constructor.
	 * 
	 * @param concurrent
	 *          The {@link ConcurrentModule} to use; may be {@code null} to not install this module.
	 * @param props
	 *          The {@link PropertiesModule} to use; may be {@code null} to not install this module.
	 */
	public CoreModule(ConcurrentModule concurrent, PropertiesModule props) {
		super(concurrent, props);
	}

}
