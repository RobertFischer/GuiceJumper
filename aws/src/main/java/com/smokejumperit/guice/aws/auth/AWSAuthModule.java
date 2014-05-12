package com.smokejumperit.guice.aws.auth;

import java.util.Objects;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;

/**
 * Provides {@link AWSCredentials} and {@link AWSCredentialsProvider} in the Guice context.
 */
public class AWSAuthModule extends AbstractModule {

	private final AWSCredentialsProvider credentialsProvider;

	/**
	 * Constructs the {@link AWSAuthModule} using the {@link DefaultAWSCredentialsProviderChain}.
	 */
	public AWSAuthModule() {
		this(new DefaultAWSCredentialsProviderChain());
	}

	/**
	 * Use the given credentials.
	 * 
	 * @param credentials
	 *          The credentials to use; never {@code null}
	 */
	public AWSAuthModule(final AWSCredentials credentials) {
		this(new AWSCredentialsProvider() {
			@Override
			public AWSCredentials getCredentials() {
				return credentials;
			}

			@Override
			public void refresh() {
				// Do nothing
			}
		});
		Objects.requireNonNull(credentials, "AWS credentials");
	}

	/**
	 * Uses the given {@link AWSCredentialsProvider} to generate credentials.
	 * 
	 * @param credentialsProvider
	 *          The credentials provider; never {@code null}
	 */
	public AWSAuthModule(AWSCredentialsProvider credentialsProvider) {
		Objects.requireNonNull(credentialsProvider, "AWS credentials provider");
		this.credentialsProvider = credentialsProvider;
	}

	@Override
	protected void configure() {
		bind(AWSCredentialsProvider.class).toInstance(credentialsProvider);
		bind(AWSCredentials.class).toProvider(new Provider<AWSCredentials>() {
			@Override
			public AWSCredentials get() {
				return credentialsProvider.getCredentials();
			}
		});
	}

}
