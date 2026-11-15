package de.blablubbabc.commons.data.persistence.bukkit;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.blablubbabc.commons.data.container.ConfigBasedDataContainer;
import de.blablubbabc.commons.data.persistence.DataStore;
import de.blablubbabc.commons.data.persistence.DataStoreBase;
import de.blablubbabc.commons.data.persistence.InvalidDataFormatException;
import de.blablubbabc.commons.util.bukkit.ConfigUtils;

/**
 * A {@link DataStore} that uses a Bukkit {@link FileConfiguration} to store, save, and load the
 * data.
 */
public class BukkitConfigDataStore extends ConfigBasedDataContainer implements DataStoreBase {

	/**
	 * Creates a new {@link BukkitConfigDataStore} that uses the given Bukkit
	 * {@link FileConfiguration} to store, save, and load the data.
	 * 
	 * @param bukkitConfig
	 *            the Bukkit configuration, not <code>null</code>
	 * @return the data store, not <code>null</code>
	 */
	public static BukkitConfigDataStore of(FileConfiguration bukkitConfig) {
		return new BukkitConfigDataStore(bukkitConfig);
	}

	/**
	 * Creates a new empty {@link BukkitConfigDataStore} based on a new {@link YamlConfiguration} as
	 * described by {@link #of(FileConfiguration)}.
	 * 
	 * @return the data store, not <code>null</code>
	 */
	public static BukkitConfigDataStore ofNewYamlConfig() {
		return of(ConfigUtils.newYamlConfig());
	}

	/////

	/**
	 * Creates a new {@link BukkitConfigDataStore}.
	 * 
	 * @param config
	 *            the Bukkit configuration, not <code>null</code>
	 */
	protected BukkitConfigDataStore(FileConfiguration config) {
		super(config);
	}

	@Override
	public FileConfiguration getConfig() {
		return (FileConfiguration) super.getConfig();
	}

	@Override
	public void loadFromString(String data) throws InvalidDataFormatException {
		try {
			ConfigUtils.loadConfigSafely(this.getConfig(), data);
		} catch (InvalidConfigurationException e) {
			throw new InvalidDataFormatException("Failed to load data as Bukkit config!", e);
		}
	}

	@Override
	public String saveToString() {
		return this.getConfig().saveToString();
	}
}
