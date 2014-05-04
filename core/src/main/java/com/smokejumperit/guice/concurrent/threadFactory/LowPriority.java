package com.smokejumperit.guice.concurrent.threadFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ThreadFactory;

import com.google.inject.BindingAnnotation;

/**
 * Denotes that a {@link ThreadFactory} should generate low-priority threads.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BindingAnnotation
public @interface LowPriority {

}
