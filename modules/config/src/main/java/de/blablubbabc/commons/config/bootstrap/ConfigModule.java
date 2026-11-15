package de.blablubbabc.commons.config.bootstrap;

import de.blablubbabc.commons.bootstrap.BlaCommonsModule;

/**
 * The {@link BlaCommonsModule} of the config module.
 */
public final class ConfigModule implements BlaCommonsModule {

	public static final ConfigModule INSTANCE = new ConfigModule();

	private ConfigModule() {
	}
}
