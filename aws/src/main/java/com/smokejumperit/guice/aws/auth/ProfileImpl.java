package com.smokejumperit.guice.aws.auth;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * The implementation of {@link Profile} for keying.
 */
public class ProfileImpl implements Profile {

	private final String key;

	public ProfileImpl(String key) {
		Objects.requireNonNull(key, "localization key");
		this.key = key;
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return Profile.class;
	}

	/**
	 * The key for localization look-ups.
	 * 
	 * @return The key to use; never {@code null}.
	 */
	@Override
	public String value() {
		return key;
	}

	@Override
	public int hashCode() {
		// This is specified in java.lang.Annotation
		return (127 * "value".hashCode()) ^ key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Profile))
			return false;
		Profile other = (Profile) obj;
		return this.key.equals(other.value());
	}

	@Override
	public String toString() {
		return "@" + this.getClass().getSimpleName() + "(" + key + ")";
	}

}
