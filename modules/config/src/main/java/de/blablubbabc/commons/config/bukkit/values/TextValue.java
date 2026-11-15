package de.blablubbabc.commons.config.bukkit.values;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;
import de.blablubbabc.commons.config.value.ValueType;
import de.blablubbabc.commons.text.Text;
import de.blablubbabc.commons.util.java.Validate;

public class TextValue extends ValueType<Text> {

	public static final TextValue INSTANCE = new TextValue();

	public TextValue() {
	}

	@Override
	public @Nullable Text load(@Nullable Object configValue) throws ValueLoadException {
		if (configValue == null) return null;
		String stringValue = configValue.toString();
		return Text.parse(stringValue);
	}

	@Override
	public @Nullable Object save(@Nullable Text value) {
		if (value == null) return null;
		return value.toFormat();
	}

	@Override
	public String format(@Nullable Text value) {
		if (value == null) return "null";
		return value.toFormat();
	}

	@Override
	public Text parse(String input) throws ValueParseException {
		Validate.notNull(input, "input is null");
		return Text.parse(input);
	}
}
