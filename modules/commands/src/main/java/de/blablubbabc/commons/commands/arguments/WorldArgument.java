package de.blablubbabc.commons.commands.arguments;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.ConversionUtils;

public class WorldArgument extends CommandArgument<World> {

	public WorldArgument(String name) {
		super(name);
	}

	@Override
	public World parseValue(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		if (!argsReader.hasNext()) {
			throw this.missingArgumentError();
		}
		String argument = argsReader.next();
		World value = Bukkit.getWorld(argument);
		if (value == null) {
			// Interpret input as world id:
			UUID uuid = ConversionUtils.parseUUID(argument);
			if (uuid != null) {
				value = Bukkit.getWorld(uuid);
			}
			if (value == null) {
				throw this.invalidArgumentError(argument);
			}
		}
		return value;
	}

	@Override
	public List<? extends String> complete(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) {
		return Collections.emptyList();
	}
}
