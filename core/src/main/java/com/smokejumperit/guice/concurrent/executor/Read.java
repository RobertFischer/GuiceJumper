package com.smokejumperit.guice.concurrent.executor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutorService;

import com.google.inject.BindingAnnotation;

/**
 * Denotes that a {@link ExecutorService} will be used for read operations. Read operations are low
 * priority since they are presumed to be slow generators of work, yet since they are intolerant of
 * lag, we use a thread caching implementation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BindingAnnotation
public @interface Read {

}
