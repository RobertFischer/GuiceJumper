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

The module is available at the [JCenter](https://bintray.com/bintray/jcenter) Maven Repository, which is located at 
[http://jcenter.bintray.com/](http://jcenter.bintray.com/). The Maven coordinates for this module are: `com.smokejumperit.guice:core:$VERSION`.
Check out the versions that are available here: [http://jcenter.bintray.com/com/smokejumperit/guice/core/](http://jcenter.bintray.com/com/smokejumperit/guice/core/).

In particular, it currently provides the following modules:

* *InstallingModule* -- A module which simply installs other modules.
* *ThreadFactoryModule* -- Provides a default `ThreadFactory`, along with the `@LowPriority`, `@MediumPriority`, and `@HighPriority` annotations 
for specifying the priority of the injected `ThreadFactory`.
* *ExecutorModule* -- Presuming that the `ThreadFactoryModule` (or equivalent) is loaded, this provides implementations for `Executor`, `ExecutorService`, `ThreadPoolExecutor`, and `ScheduledExecutorService`. It also provides the `@Background`, `@Process`, `@Read`, and `@Write` annotations for acquiring a specially-tuned version of an executor for background processing, CPU-bound processing, reading I/O actions, and writing I/O actions.
* *ConcurrentModule* -- Convenience module for loading `ThreadFactoryModule` and `ExecutorModule`.
* *PropertiesModule* -- Makes system properties, environment variables, and configuration-time String values available via `@Named` parameters, including providing automatic conversion of these values to native types (including `int`, `double`, `BigDecimal`, `BigInteger`, `Charset`, `File`, and more). Examples of just how easy this makes configuration values are in [the JavaDoc](http://files.enfranchisedmind.com/guicejumper-api/com/smokejumperit/guice/properties/PropertiesModule.html).  
* *I18nModule* -- Provides access to `ResourceBundle` based internationalization, which is also useful for externalizing your user strings. Simply annotate your string fields with `@Localized('foo')`, and it will be injected with the resource bundle message with the key `foo`. Also exposes the `ResourceBundle` and `Locale` for injection.
* *CoreModule* -- Convenience module providing `PropertiesModule`, `ConcurrentModule`, and a default-configured `I18nModule`.



Subproject: AWS
-----------------
_*THE IMPLEMENTATION FOR THIS MODULE IS NOT YET RELEASED*_

This will be a standard wiring module for AWS. It will take an `AWSCredentials` instance (or an `AWSCredentialsProvider`).
Based on that information, it will wire up all the `Amazon*Client` instances so that
you can simply `@Inject` them. It will be responsible for knowing which ones are thread-safe and which aren't. Its configuration broken down
into overridable modules that will mirror the AWS Java SDK package structure.

License
--------

Released under [the Unlicense](http://unlicense.org/). See `LICENSE` for more information.
