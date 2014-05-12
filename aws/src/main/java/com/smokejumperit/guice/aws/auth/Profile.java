package com.smokejumperit.guice.aws.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.amazonaws.auth.AWSCredentials;
import com.google.inject.BindingAnnotation;

/**
 * Denotes that the annotated {@link AWSCredentials} should use the given profile.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BindingAnnotation
public @interface Profile {
	String value();
}
