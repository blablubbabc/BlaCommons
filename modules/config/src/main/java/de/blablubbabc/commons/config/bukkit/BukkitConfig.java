package de.blablubbabc.commons.config.bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.Config;
import de.blablubbabc.commons.config.bukkit.annotation.Colored;
import de.blablubbabc.commons.config.bukkit.annotation.Uncolored;
import de.blablubbabc.commons.config.bukkit.values.ColoredStringListValue;
import de.blablubbabc.commons.config.bukkit.values.ColoredStringValue;
import de.blablubbabc.commons.config.value.ValueType;
import de.blablubbabc.commons.config.value.types.StringListValue;
import de.blablubbabc.commons.config.value.types.StringValue;
import de.blablubbabc.commons.util.bukkit.TextUtils;

/**
 * A {@link Config} that additionally supports the {@link Colored} and {@link Uncolored}
 * annotations.
 */
public abstract class BukkitConfig extends Config {

	protected BukkitConfig() {
	}

	@Override
	protected <T> ValueType<T> setupValueType(Field field) {
		ValueType<T> valueType = this.getValueTypeByColoredAnnotation(field);
		if (valueType != null) return valueType;

		valueType = this.getValueTypeByUncoloredAnnotation(field);
		if (valueType != null) return valueType;

		return super.setupValueType(field);
	}

	@SuppressWarnings("unchecked")
	protected final <T> @Nullable ValueType<T> getValueTypeByColoredAnnotation(Field field) {
		Colored coloredAnnotation = field.getAnnotation(Colored.class);
		if (coloredAnnotation != null) {
			Type fieldType = field.getGenericType();
			if (fieldType == String.class) {
				return (ValueType<T>) ColoredStringValue.INSTANCE;
			} else if (ColoredStringListValue.TYPE_PATTERN.matches(fieldType)) {
				return (ValueType<T>) ColoredStringListValue.INSTANCE;
			} else {
				throw new IllegalArgumentException(
						"The Colored annotation is not supported for settings of type "
								+ fieldType.getTypeName()
				);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected final <T> @Nullable ValueType<T> getValueTypeByUncoloredAnnotation(Field field) {
		Uncolored uncoloredAnnotation = field.getAnnotation(Uncolored.class);
		if (uncoloredAnnotation != null) {
			Type fieldType = field.getGenericType();
			if (fieldType == String.class) {
				return (ValueType<T>) StringValue.INSTANCE;
			} else if (ColoredStringListValue.TYPE_PATTERN.matches(fieldType)) {
				return (ValueType<T>) StringListValue.INSTANCE;
			} else {
				throw new IllegalArgumentException(
						"The Uncolored annotation is not supported for settings of type "
								+ fieldType.getTypeName()
				);
			}
		}
		return null;
	}

	// CONVENIENCE HELPERS

	/**
	 * Translates {@code &}-based color codes to Minecraft's {@code §}-based color codes.
	 * 
	 * @param text
	 *            the text with {@code &}-based color codes
	 * @return the text with Minecraft's color codes
	 */
	protected static String c(String text) {
		return TextUtils.colorize(text);
	}

	/**
	 * Translates {@code &}-based color codes to Minecraft's {@code §}-based color codes.
	 * 
	 * @param texts
	 *            the texts with {@code &}-based color codes, not <code>null</code>
	 * @return a new list containing the corresponding texts with Minecraft's color codes
	 */
	protected static List<String> c(List<? extends String> texts) {
		return TextUtils.colorize(texts);
	}
}
