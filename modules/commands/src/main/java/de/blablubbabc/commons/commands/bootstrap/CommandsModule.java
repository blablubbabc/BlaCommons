package de.blablubbabc.commons.commands.bootstrap;

import de.blablubbabc.commons.bootstrap.BlaCommonsModule;

/**
 * The {@link BlaCommonsModule} of the commands module.
 */
public final class CommandsModule implements BlaCommonsModule {

	public static final CommandsModule INSTANCE = new CommandsModule();

	private CommandsModule() {
	}
}
