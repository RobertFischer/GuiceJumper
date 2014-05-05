package com.smokejumperit.guice.properties;

public class PropertyToChar extends PropertyConversionProvider<Character> {

	public PropertyToChar(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<Character> valueClass() {
		return Character.TYPE;
	}

	@Override
	public Character convertValue() {
		if (value.length() == 1) {
			return value.charAt(0);
		} else if (value.length() > 1) {
			throw new IllegalArgumentException("String is longer chan one character: " + value + " ("
					+ value.length() + ")");
		} else {
			if (value.length() != 0) {
				throw new IllegalStateException("Thought the length of a string was 0, but it's not: "
						+ value.length());
			}
			throw new IllegalArgumentException("String is empty");
		}
	}

}
