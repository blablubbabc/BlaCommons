package de.blablubbabc.commons.config.bukkit.values;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;
import de.blablubbabc.commons.config.value.types.StringValue;
import de.blablubbabc.commons.util.bukkit.TextUtils;
import de.blablubbabc.commons.util.java.Validate;

public class ColoredStringValue extends StringValue {

	public static final ColoredStringValue INSTANCE = new ColoredStringValue();

	public ColoredStringValue() {
	}

	@Override
	public @Nullable String load(@Nullable Object configValue) throws ValueLoadException {
		String string = super.load(configValue);
		if (string == null) return null;
		return TextUtils.colorize(string);
	}

	@Override
	public @Nullable Object save(@Nullable String value) {
		if (value == null) return null;
		return TextUtils.decolorize(value);
	}

	@Override
	public String format(@Nullable String value) {
		if (value == null) return "null";
		return TextUtils.decolorize(value);
	}

	@Override
	public String parse(String input) throws ValueParseException {
		Validate.notNull(input, "input is null");
		return TextUtils.colorize(input);
	}
}
