package de.blablubbabc.commons.config.setting;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.config.Config;
import de.blablubbabc.commons.config.value.ValueLoadException;
import de.blablubbabc.commons.config.value.ValueType;

/**
 * Represents a config setting and its value in a particular config instance.
 *
 * @param <T>
 *            the value type
 */
public interface Setting<T> {

	/**
	 * Gets the {@link Config} this setting belongs to.
	 * 
	 * @return the config containing this setting
	 */
	public Config getConfig();

	/**
	 * Gets the setting's config key.
	 * 
	 * @return the config key
	 */
	public String getConfigKey();

	/**
	 * Gets the {@link ValueType} of this setting.
	 * 
	 * @return the value type
	 */
	public ValueType<T> getValueType();

	/**
	 * Gets the current value of this setting.
	 * 
	 * @return the current value
	 */
	public @Nullable T getValue();

	/**
	 * Sets the value of this setting.
	 * 
	 * @param value
	 *            the new value
	 * @throws ValueLoadException
	 *             if the value could not be set
	 */
	public void setValue(@Nullable T value) throws ValueLoadException;
}
