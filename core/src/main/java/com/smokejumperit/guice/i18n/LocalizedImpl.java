package com.smokejumperit.guice.i18n;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * The implementation of {@link Localized} for keying.
 */
public class LocalizedImpl implements Localized {

	private final String key;

	public LocalizedImpl(String key) {
		Objects.requireNonNull(key, "localization key");
		this.key = key;
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return Localized.class;
	}

	/**
	 * The key for localization look-ups.
	 * 
	 * @returns The key to use; never {@code null}.
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
		if (!(obj instanceof Localized))
			return false;
		Localized other = (Localized) obj;
		return this.key.equals(other.value());
	}

	@Override
	public String toString() {
		return "@" + this.getClass().getSimpleName() + "(" + key + ")";
	}

}
