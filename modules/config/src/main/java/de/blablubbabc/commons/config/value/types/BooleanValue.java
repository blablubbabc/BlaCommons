package de.blablubbabc.commons.config.value.types;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;
import de.blablubbabc.commons.config.value.ValueType;
import de.blablubbabc.commons.util.java.ConversionUtils;

public class BooleanValue extends ValueType<Boolean> {

	public static final BooleanValue INSTANCE = new BooleanValue();

	public BooleanValue() {
	}

	@Override
	public @Nullable Boolean load(@Nullable Object configValue) throws ValueLoadException {
		if (configValue == null) return null;
		@Nullable Boolean value = ConversionUtils.toBoolean(configValue);
		if (value == null) {
			throw new ValueLoadException("Invalid boolean value: " + configValue);
		}
		return value;
	}

	@Override
	public @Nullable Object save(@Nullable Boolean value) {
		return value;
	}

	@Override
	public Boolean parse(String input) throws ValueParseException {
		@Nullable Boolean value = ConversionUtils.parseBoolean(input);
		if (value == null) {
			throw new ValueParseException("Invalid boolean value: " + input);
		}
		return value;
	}
}
