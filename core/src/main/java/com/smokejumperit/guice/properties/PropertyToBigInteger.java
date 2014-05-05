package com.smokejumperit.guice.properties;

import java.math.BigInteger;

public class PropertyToBigInteger extends PropertyConversionProvider<BigInteger> {

	public PropertyToBigInteger(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<BigInteger> valueClass() {
		return BigInteger.class;
	}

	@Override
	public BigInteger convertValue() {
		return new BigInteger(value);
	}

}
