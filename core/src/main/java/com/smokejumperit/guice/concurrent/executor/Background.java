package com.smokejumperit.guice.concurrent.executor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutorService;

import com.google.inject.BindingAnnotation;

/**
 * Denotes that a {@link ExecutorService} will be used for background operations. Background
 * operations are not at all urgent, and may come in major bursts.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BindingAnnotation
public @interface Background {

}
