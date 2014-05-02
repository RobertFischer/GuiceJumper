package com.smokejumperit.guice.concurrent.threadFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ThreadFactory;

/**
 * Denotes that a {@link ThreadFactory} should generate high priority threads.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HighPriority {

}
