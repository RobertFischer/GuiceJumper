package com.smokejumperit.guice.properties;

public class PropertyToByte extends PropertyConversionProvider<Byte> {

	public PropertyToByte(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<Byte> valueClass() {
		return Byte.TYPE;
	}

	@Override
	public Byte convertValue() {
		return Byte.parseByte(value);
	}

}
