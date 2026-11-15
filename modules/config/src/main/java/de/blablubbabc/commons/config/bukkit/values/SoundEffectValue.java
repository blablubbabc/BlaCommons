package de.blablubbabc.commons.config.bukkit.values;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueParseException;
import de.blablubbabc.commons.config.value.ValueType;
import de.blablubbabc.commons.data.serialization.InvalidDataException;
import de.blablubbabc.commons.data.serialization.bukkit.SoundEffectSerializers;
import de.blablubbabc.commons.util.bukkit.SoundEffect;
import de.blablubbabc.commons.util.java.Validate;

public class SoundEffectValue extends ValueType<SoundEffect> {

	public static final SoundEffectValue INSTANCE = new SoundEffectValue();

	public SoundEffectValue() {
	}

	@Override
	public @Nullable SoundEffect load(@Nullable Object configValue) throws ValueLoadException {
		if (configValue == null) return null;
		try {
			return SoundEffectSerializers.DEFAULT.deserialize(configValue);
		} catch (InvalidDataException e) {
			throw new ValueLoadException(e.getMessage(), e);
		}
	}

	@Override
	public @Nullable Object save(@Nullable SoundEffect value) {
		if (value == null) return null;
		return SoundEffectSerializers.DEFAULT.serialize(value);
	}

	@Override
	public SoundEffect parse(String input) throws ValueParseException {
		Validate.notNull(input, "input is null");
		try {
			// Note: This only supports the parsing from the compact representation currently (sound
			// name only).
			// TODO Print a warning if it does not match a known Sound?
			return SoundEffectSerializers.DEFAULT.deserialize(input);
		} catch (InvalidDataException e) {
			throw new ValueParseException(e.getMessage(), e);
		}
	}
}
