package de.blablubbabc.commons.commands.arguments;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.ConversionUtils;

/**
 * Parses an UUID.
 * <p>
 * Provides no completions.
 */
public class UUIDArgument extends CommandArgument<UUID> {

	public UUIDArgument(String name) {
		super(name);
	}

	// TODO 'invalid uuid' message

	@Override
	public UUID parseValue(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		String uuidArg = argsReader.nextIfPresent();
		if (uuidArg == null) {
			throw this.missingArgumentError();
		} else {
			UUID uuid = ConversionUtils.parseUUID(uuidArg);
			if (uuid == null) {
				throw this.invalidArgumentError(uuidArg);
			} else {
				return uuid;
			}
		}
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
