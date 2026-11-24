package de.blablubbabc.commons.commands.bootstrap;

import org.bukkit.plugin.Plugin;

import de.blablubbabc.commons.bootstrap.BlaCommonsModule;
import de.blablubbabc.commons.commands.debug.CommandsDebugOptions;
import de.blablubbabc.commons.debug.DebugOptions;

/**
 * The {@link BlaCommonsModule} of the commands module.
 */
public final class CommandsModule implements BlaCommonsModule {

	public static final CommandsModule INSTANCE = new CommandsModule();

	private CommandsModule() {
	}

	@Override
	public void onLoad(Plugin plugin) {
		DebugOptions.addAll(CommandsDebugOptions.getAll());
	}
}
