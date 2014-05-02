GuiceJumper
===========

The Smokejumper supplemental classes for Guice.  This is broken into various subprojects.

Subproject: Core
------------------
These are utility classes and useful modules which do not depend on anything more than the most recent version of Guice and its dependencies.

In particular, it provides the following injections:

* `ExecutorService` (various flavors)
* `ThreadPoolExecutor`
* `ThreadFactory` (various flavors)
* A way to inject all the System properties into their `@Named` primitive types

Subproject: AWS
-----------------
This is a standard wiring for AWS. It takes an `AWSCredentials` instance (or an `AWSCredentialsProvider`), along with (optionally) an 
`ExecutorService` and `ThreadPoolExecutor`. Based on those pieces of information, it will wire up all the `Amazon\*Client` instances so that
you can simply `@Inject` them. It is responsible for knowing which ones are thread-safe and which aren't. Its configuration is broken down
into overridable `protected` methods that mirror the AWS Java SDK package structure.
