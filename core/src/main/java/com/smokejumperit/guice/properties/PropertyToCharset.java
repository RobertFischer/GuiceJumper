package com.smokejumperit.guice.properties;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.SortedMap;
import java.util.TreeMap;

public class PropertyToCharset extends PropertyConversionProvider<Charset> {

	public PropertyToCharset(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<Charset> valueClass() {
		return Charset.class;
	}

	@Override
	public Charset convertValue() throws Exception {
		SortedMap<String, Charset> charsets = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		charsets.putAll(Charset.availableCharsets());
		if (charsets.containsKey(value)) return charsets.get(value);

		// Try the lookup through the class: this may return more positives if there are multiple
		// names for a charset.
		try {
			if (Charset.isSupported(value)) return Charset.forName(value);
		} catch (IllegalCharsetNameException ignored) {
		}
		try {
			if (Charset.isSupported(value.toUpperCase())) return Charset.forName(value.toUpperCase());
		} catch (IllegalCharsetNameException ignored) {
		}

		return Charset.forName(value); // Give up: trigger explosion!
	}

}
