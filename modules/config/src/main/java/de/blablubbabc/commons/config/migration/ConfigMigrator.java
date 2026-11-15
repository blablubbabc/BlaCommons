package de.blablubbabc.commons.config.migration;

import java.util.ArrayList;
import java.util.List;

import de.blablubbabc.commons.config.ConfigLoadException;
import de.blablubbabc.commons.data.container.DataContainer;
import de.blablubbabc.commons.logging.Log;
import de.blablubbabc.commons.util.java.ConversionUtils;
import de.blablubbabc.commons.util.java.Validate;

/**
 * Applies a sequence of {@link ConfigMigration}s to config data, based on the config version stored
 * in the config data.
 */
public class ConfigMigrator {

	private static final String DEFAULT_CONFIG_VERSION_KEY = "config-version";
	private static final int FIRST_VERSION = 0; // Also applies if the config version is missing

	private final String configVersionKey;
	// Each index corresponds to a source config version and its migration to the next version:
	private final List<? extends ConfigMigration> migrations;

	/**
	 * Creates a new {@link ConfigMigrator}.
	 * 
	 * @param migrations
	 *            the migrations, ordered by the config version they migrate from, not
	 *            <code>null</code>
	 */
	public ConfigMigrator(List<? extends ConfigMigration> migrations) {
		this(DEFAULT_CONFIG_VERSION_KEY, migrations);
	}

	/**
	 * Creates a new {@link ConfigMigrator}.
	 * 
	 * @param configVersionKey
	 *            the config key under which the config version is stored, not <code>null</code>
	 * @param migrations
	 *            the migrations, ordered by the config version they migrate from, not
	 *            <code>null</code>
	 */
	public ConfigMigrator(String configVersionKey, List<? extends ConfigMigration> migrations) {
		Validate.notNull(configVersionKey, "configVersionKey is null");
		Validate.noNullElements(migrations, "migrations contains null");
		this.configVersionKey = configVersionKey;
		this.migrations = new ArrayList<>(migrations);
	}

	public int getLatestVersion() {
		return migrations.size();
	}

	// Returns true if any migrations were applied (if the config data might have changed)
	public boolean applyMigrations(DataContainer configData) throws ConfigLoadException {
		// No migrations are required if the config is empty (missing entries will get generated
		// from defaults):
		if (configData.isEmpty()) {
			return false;
		}

		// Parse config version:
		int configVersion;
		if (!configData.contains(configVersionKey)) {
			Log.info("Missing config version. Assuming version '" + FIRST_VERSION + "'.");
			configVersion = FIRST_VERSION;
		} else {
			Object rawConfigVersion = configData.get(configVersionKey);
			Integer configVersionI = ConversionUtils.toInteger(rawConfigVersion);
			if (configVersionI == null) { // Parsing failed
				throw new ConfigLoadException("Could not parse config version: "
						+ rawConfigVersion);
			} else {
				configVersion = configVersionI.intValue();
			}
		}

		// Check bounds:
		if (configVersion < FIRST_VERSION) {
			throw new ConfigLoadException("Invalid config version: " + configVersion);
		}
		if (configVersion > getLatestVersion()) {
			throw new ConfigLoadException("Invalid config version: " + configVersion
					+ " (the latest version is " + getLatestVersion() + ")");
		}

		// Apply required migrations:
		boolean migrated = false;
		for (int version = configVersion; version < migrations.size(); ++version) {
			int nextVersion = (version + 1);
			ConfigMigration migration = migrations.get(version);
			assert migration != null;

			Log.info("Migrating config from version " + version + " to version " + nextVersion
					+ " ...");
			try {
				migration.apply(configData);
			} catch (Exception e) {
				throw new ConfigLoadException("Config migration failed with an error!", e);
			}

			// Update config version:
			configData.set(configVersionKey, nextVersion);
			migrated = true;
			Log.info("Config migrated to version " + nextVersion + ".");
		}

		return migrated;
	}
}
