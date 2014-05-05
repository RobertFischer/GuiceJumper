package com.smokejumperit.guice.properties;

public class PropertyToShort extends PropertyConversionProvider<Short> {

	public PropertyToShort(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<Short> valueClass() {
		return Short.TYPE;
	}

	@Override
	public Short convertValue() {
		return Short.parseShort(value);
	}

}
