package com.smokejumperit.guice.properties;

import java.io.File;

public class PropertyToFile extends PropertyConversionProvider<File> {

	public PropertyToFile(String key, String value) {
		super(key, value);
	}

	@Override
	public Class<File> valueClass() {
		return File.class;
	}

	@Override
	public File convertValue() {
		return new File(value);
	}

}
