package com.smokejumperit.guice.properties;

public class PropertyToDouble extends PropertyConversionProvider<Double> {

	public PropertyToDouble(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<Double> valueClass() {
		return Double.TYPE;
	}

	@Override
	public Double convertValue() {
		return Double.parseDouble(value);
	}

}
