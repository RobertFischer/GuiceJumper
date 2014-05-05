package com.smokejumperit.guice.properties;

import java.util.Objects;

import com.google.inject.Provider;
import com.google.inject.ProvisionException;

public abstract class PropertyConversionProvider<V> implements Provider<V> {

	protected final String key;
	protected final String value;

	public PropertyConversionProvider(String key, String value) {
		Objects.requireNonNull(key, "key");
		this.key = key;
		Objects.requireNonNull(value, "value");
		this.value = value;
	}

	@Override
	public V get() {
		try {
			V value = convertValue();
			Objects.requireNonNull(value, "conversion returned null");
			return value;
		} catch (Exception e) {
			throw new ProvisionException("Could not convert value for key '" + key + "': " + value, e);
		}
	}

	public abstract V convertValue() throws Exception;

	public abstract Class<V> valueClass();

}
