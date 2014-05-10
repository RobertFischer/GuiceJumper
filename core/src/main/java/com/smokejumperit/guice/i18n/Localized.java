package com.smokejumperit.guice.i18n;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * Denotes that the annotated value is localized given the specified key.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BindingAnnotation
public @interface Localized {
	String value();
}
