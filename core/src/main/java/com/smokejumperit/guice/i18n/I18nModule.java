package com.smokejumperit.guice.i18n;

import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import com.google.inject.AbstractModule;
import com.google.inject.Key;

/**
 * This module provides internationalization/localization support leveraging Java's
 * {@link ResourceBundle} and {@link Locale} structure. Any {@link String} can be provided by
 * annotating it with the {@literal @Localized} annotation.
 * 
 * @see Localized
 */
public class I18nModule extends AbstractModule {

	/**
	 * The resource bundle that will be used.
	 */
	protected final ResourceBundle bundle;

	/**
	 * Constructor given a resource bundle to use.
	 * 
	 * @param bundle
	 *          The bundle to use; never {@code null}.
	 */
	public I18nModule(ResourceBundle bundle) {
		Objects.requireNonNull(bundle, "resource bundle");
		this.bundle = bundle;
	}

	/**
	 * Constructor that retrieves the bundle given the basename.
	 * 
	 * @param baseName
	 *          The base name for resources; never {@code null}.
	 * @see ResourceBundle.getBundle(String)
	 */
	public I18nModule(String baseName) {
		this(ResourceBundle.getBundle(baseName));
	}

	/**
	 * Constructor that retrieves the bundle given the basename and locale.
	 * 
	 * @param baseName
	 *          The base name for resources; never {@code null}.
	 * @param locale
	 *          The locale for resources; never {@code null}.
	 * @see ResourceBundle.getBundle(String,Locale)
	 */
	public I18nModule(String baseName, Locale locale) {
		this(ResourceBundle.getBundle(baseName, locale));
	}

	/**
	 * Constructor that retrieves the bundle given the basename, locale, and classloader.
	 * 
	 * @param baseName
	 *          The base name for resources; never {@code null}.
	 * @param locale
	 *          The locale for resources; never {@code null}.
	 * @param classLoader
	 *          The classLoader for resources; never {@code null}.
	 * @see ResourceBundle.getBundle(String,Locale,ClassLoader)
	 */
	public I18nModule(String baseName, Locale locale, ClassLoader classLoader) {
		this(ResourceBundle.getBundle(baseName, locale, classLoader));
	}

	/**
	 * Creates a purely-default configured values. The {@code baseName} used by the @{link
	 * ResourceBundle} is "i18n".
	 * 
	 * @see ResourceBundle.getBundle(String)
	 */
	public I18nModule() {
		this(ResourceBundle.getBundle("i18n"));
	}

	@Override
	protected void configure() {
		for (String key : Collections.list(this.bundle.getKeys())) {
			String value = bundle.getString(key);
			Key<String> guiceKey = Key.get(String.class, getKey(key));
			bind(guiceKey).toInstance(value);
		}

		bind(ResourceBundle.class).toInstance(this.bundle);
		bind(Locale.class).toInstance(this.bundle.getLocale());
	}

	/**
	 * Generates an instance of the {@link Localized} annotation for the given key.
	 * 
	 * @param resourceKey
	 *          The given resource key; never {@code null}.
	 * @return An instance of the annotation; never {@code null}.
	 */
	public static Localized getKey(String resourceKey) {
		Objects.requireNonNull(resourceKey, "resource key");
		return new LocalizedImpl(resourceKey);
	}

}
