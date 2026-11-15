package de.blablubbabc.commons.config.value.types;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;
import de.blablubbabc.commons.config.value.ValueType;
import de.blablubbabc.commons.util.java.ConversionUtils;

public class LongValue extends ValueType<Long> {

	public static final LongValue INSTANCE = new LongValue();

	public LongValue() {
	}

	@Override
	public @Nullable Long load(@Nullable Object configValue) throws ValueLoadException {
		if (configValue == null) return null;
		Long value = ConversionUtils.toLong(configValue);
		if (value == null) {
			throw new ValueLoadException("Invalid long value: " + configValue);
		}
		return value;
	}

	@Override
	public @Nullable Object save(@Nullable Long value) {
		return value;
	}

	@Override
	public Long parse(String input) throws ValueParseException {
		Long value = ConversionUtils.parseLong(input);
		if (value == null) {
			throw new ValueParseException("Invalid long value: " + input);
		}
		return value;
	}
}
