package de.blablubbabc.commons.config.bukkit.values;

import org.checkerframework.checker.nullness.qual.NonNull;

import de.blablubbabc.commons.config.value.types.EnumValue;
import de.blablubbabc.commons.util.bukkit.MinecraftEnumUtils;

/**
 * Extends {@link EnumValue}, but normalizes inputs via
 * {@link MinecraftEnumUtils#normalizeEnumName(String)} when parsing the enum value from a given
 * input String.
 *
 * @param <E>
 *            the enum type
 */
public class MinecraftEnumValue<E extends Enum<E>> extends EnumValue<E> {

	public MinecraftEnumValue(Class<@NonNull E> enumClass) {
		super(enumClass);
	}

	@Override
	protected String normalize(String input) {
		return MinecraftEnumUtils.normalizeEnumName(input);
	}
}
