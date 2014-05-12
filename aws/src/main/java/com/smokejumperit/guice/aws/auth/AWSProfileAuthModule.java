package com.smokejumperit.guice.aws.auth;

import java.util.Map;
import java.util.Objects;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfilesConfigFile;
import com.amazonaws.auth.profile.internal.Profile;
import com.google.inject.Key;
import com.google.inject.Provider;

/**
 * Provides {@link AWSCredentials}, {@link AWSCredentialsProvider}, and {@literal @}Profile support
 * for the Guice context. This class is an alternative to {@link AWSAuthModule} for use cases where
 * you need access to profiles.
 */
public class AWSProfileAuthModule extends AWSAuthModule {

	private final ProfilesConfigFile configFile;

	/**
	 * Uses the given config file with the default profile name.
	 */
	public AWSProfileAuthModule(ProfilesConfigFile configFile) {
		this(configFile, ProfilesConfigFile.DEFAULT_PROFILE_NAME);
	}

	/**
	 * Uses the given config file and the specified named profile as your default.
	 */
	public AWSProfileAuthModule(ProfilesConfigFile configFile, String defaultProfileName) {
		super(new ProfileCredentialsProvider(configFile, defaultProfileName));
		Objects.requireNonNull(configFile, "config file");
		this.configFile = configFile;
	}

	@Override
	protected void configure() {
		super.configure();
		Map<String, Profile> allProfiles = configFile.getAllProfiles();
		for (Map.Entry<String, Profile> entry : allProfiles.entrySet()) {
			String name = entry.getKey();
			Profile profile = entry.getValue();
			final AWSCredentials profileCredentials = profile.getCredentials();
			bind(Key.get(AWSCredentials.class, new ProfileImpl(name))).toProvider(
					new Provider<AWSCredentials>() {
						@Override
						public AWSCredentials get() {
							return profileCredentials;
						}
					});
		}
	}

}
