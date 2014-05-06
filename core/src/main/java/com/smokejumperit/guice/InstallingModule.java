package com.smokejumperit.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

/**
 * A module which simply installs other modules.
 */
public class InstallingModule extends AbstractModule {

	/**
	 * The modules to install: never {@code null}, although possibly empty and possibly containing
	 * {@code null} elements.
	 */
	protected final Module[] modules;

	/**
	 * Constructs an instance provided the modules to install.
	 * 
	 * @param modules
	 *          The modules to install; may be {@code null} and may have {@code null} elements.
	 */
	public InstallingModule(Module... modules) {
		if (modules == null) {
			this.modules = new Module[0];
		} else {
			this.modules = modules;
		}
	}

	@Override
	protected void configure() {
		for (Module module : modules) {
			if (module != null) install(module);
		}
	}

}
