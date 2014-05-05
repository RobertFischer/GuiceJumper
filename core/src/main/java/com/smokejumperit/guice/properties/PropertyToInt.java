package com.smokejumperit.guice.properties;

public class PropertyToInt extends PropertyConversionProvider<Integer> {

	public PropertyToInt(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<Integer> valueClass() {
		return Integer.TYPE;
	}

	@Override
	public Integer convertValue() {
		return Integer.parseInt(value);
	}

}
