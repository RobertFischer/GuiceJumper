package com.smokejumperit.guice.properties;

import java.math.BigDecimal;

public class PropertyToBigDecimal extends PropertyConversionProvider<BigDecimal> {

	public PropertyToBigDecimal(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<BigDecimal> valueClass() {
		return BigDecimal.class;
	}

	@Override
	public BigDecimal convertValue() {
		return new BigDecimal(value);
	}

}
