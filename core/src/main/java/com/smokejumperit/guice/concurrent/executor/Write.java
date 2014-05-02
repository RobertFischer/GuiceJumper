package com.smokejumperit.guice.concurrent.executor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutorService;

/**
 * Denotes that a {@link ExecutorService} will be used for write operations. Write operations are
 * high priority since they consume work, and we use a small initial pool of threads with a small
 * backlog which can grow without bound when the backlog is filled.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Write {

}
