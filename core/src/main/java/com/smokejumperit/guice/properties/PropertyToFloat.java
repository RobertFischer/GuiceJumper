package com.smokejumperit.guice.properties;

public class PropertyToFloat extends PropertyConversionProvider<Float> {

	public PropertyToFloat(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<Float> valueClass() {
		return Float.TYPE;
	}

	@Override
	public Float convertValue() {
		return Float.parseFloat(value);
	}

}
