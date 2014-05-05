package com.smokejumperit.guice.properties;

public class PropertyToLong extends PropertyConversionProvider<Long> {

	public PropertyToLong(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<Long> valueClass() {
		return Long.TYPE;
	}

	@Override
	public Long convertValue() {
		return Long.parseLong(value);
	}

}
