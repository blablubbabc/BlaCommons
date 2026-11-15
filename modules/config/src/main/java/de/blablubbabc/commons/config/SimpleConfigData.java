package de.blablubbabc.commons.config;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.data.container.DataContainer;
import de.blablubbabc.commons.data.container.DelegateDataContainer;

/**
 * A basic {@link ConfigData} implementation.
 */
public class SimpleConfigData extends DelegateDataContainer implements ConfigData {

	private @Nullable DataContainer defaults = null;

	/**
	 * Creates a new {@link SimpleConfigData}.
	 * 
	 * @param dataContainer
	 *            the underlying data container that stores the config values, not <code>null</code>
	 */
	public SimpleConfigData(DataContainer dataContainer) {
		super(dataContainer);
	}

	@Override
	public @Nullable DataContainer getDefaults() {
		return defaults;
	}

	@Override
	public void setDefaults(@Nullable DataContainer defaults) {
		this.defaults = defaults;
	}
}
