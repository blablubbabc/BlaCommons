package de.blablubbabc.commons.config.value.types;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;
import de.blablubbabc.commons.config.value.ValueType;
import de.blablubbabc.commons.util.java.ConversionUtils;

public class FloatValue extends ValueType<Float> {

	public static final FloatValue INSTANCE = new FloatValue();

	public FloatValue() {
	}

	@Override
	public @Nullable Float load(@Nullable Object configValue) throws ValueLoadException {
		if (configValue == null) return null;
		Float value = ConversionUtils.toFloat(configValue);
		if (value == null) {
			throw new ValueLoadException("Invalid float value: " + configValue);
		}
		return value;
	}

	@Override
	public @Nullable Object save(@Nullable Float value) {
		return value;
	}

	@Override
	public Float parse(String input) throws ValueParseException {
		Float value = ConversionUtils.parseFloat(input);
		if (value == null) {
			throw new ValueParseException("Invalid float value: " + input);
		}
		return value;
	}
}
