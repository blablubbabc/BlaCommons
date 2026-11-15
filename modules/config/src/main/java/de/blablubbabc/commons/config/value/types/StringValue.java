package de.blablubbabc.commons.config.value.types;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;
import de.blablubbabc.commons.config.value.ValueType;
import de.blablubbabc.commons.util.java.Validate;

public class StringValue extends ValueType<String> {

	public static final StringValue INSTANCE = new StringValue();

	public StringValue() {
	}

	@Override
	public @Nullable String load(@Nullable Object configValue) throws ValueLoadException {
		if (configValue == null) return null;
		return configValue.toString();
	}

	@Override
	public @Nullable Object save(@Nullable String value) {
		return value;
	}

	@Override
	public String parse(String input) throws ValueParseException {
		Validate.notNull(input, "input is null");
		return input;
	}
}
