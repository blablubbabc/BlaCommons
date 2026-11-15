package de.blablubbabc.commons.config.value.types;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;
import de.blablubbabc.commons.config.value.ValueType;
import de.blablubbabc.commons.util.java.ConversionUtils;

public class DoubleValue extends ValueType<Double> {

	public static final DoubleValue INSTANCE = new DoubleValue();

	public DoubleValue() {
	}

	@Override
	public @Nullable Double load(@Nullable Object configValue) throws ValueLoadException {
		if (configValue == null) return null;
		Double value = ConversionUtils.toDouble(configValue);
		if (value == null) {
			throw new ValueLoadException("Invalid double value: " + configValue);
		}
		return value;
	}

	@Override
	public @Nullable Object save(@Nullable Double value) {
		return value;
	}

	@Override
	public Double parse(String input) throws ValueParseException {
		Double value = ConversionUtils.parseDouble(input);
		if (value == null) {
			throw new ValueParseException("Invalid double value: " + input);
		}
		return value;
	}
}
