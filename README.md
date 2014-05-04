GuiceJumper
===========

The Smokejumper supplemental classes for Guice. These are generally-useful Guice modules based on common needs. Their API and implementation has 
been driven by the needs of real development projects. Everything has been tested through Spock specifications, which is one of the beautiful 
things of Guice compared to other dependency injection frameworks.

This project is broken into various subprojects. Each subproject provides multiple modules. All the modules are designed to be easily extensible 
and customizable: see their API and source for more information.

Subproject: Core
------------------
These are utility classes and useful modules which do not depend on anything more than the most recent version of Guice and its dependencies.

In particular, it currently provides the following modules:

* *ThreadFactoryModule* -- Provides a default `ThreadFactory`, along with the `@LowPriority`, `@MediumPriority`, and `@HighPriority` annotations 
for specifying the priority of the injected `ThreadFactory`.
* *ExecutorModule* -- Presuming that the `ThreadFactoryModule` (or equivalent) is loaded, this provides implementations for `Executor`, `ExecutorService`, `ThreadPoolExecutor`, and `ScheduledExecutorService`. It also provides the `@Background`, `@Process`, `@Read`, and `@Write` annotations for acquiring a specially-tuned version of an executor for background processing, CPU-bound processing, reading I/O actions, and writing I/O actions.
* *ConcurrentModule* -- Convenience module for loading `ThreadFactoryModule` and `ExecutorModule`.

It will eventually provide the following:

* *PropertiesModule* -- Makes system and environment variables available via `@Named` parameters, including providing the variables as the appropriate native types (including `BigDecimal` and `BigInteger`).


Subproject: AWS
-----------------
This is a standard wiring for AWS. It takes an `AWSCredentials` instance (or an `AWSCredentialsProvider`).
Based on that information, it will wire up all the `Amazon\*Client` instances so that
you can simply `@Inject` them. It is responsible for knowing which ones are thread-safe and which aren't. Its configuration is broken down
into overridable `protected` methods that mirror the AWS Java SDK package structure.

