package de.blablubbabc.commons.commands.arguments;

import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.argument.fallback.FallbackArgument;
import de.blablubbabc.commons.util.java.Validate;

/**
 * A {@link FallbackArgument} that accepts any String as fallback.
 * 
 * @see StringArgument
 */
public class AnyStringFallback extends TypedFallbackArgument<String> {

	public AnyStringFallback(CommandArgument<String> argument) {
		this(argument, false);
	}

	public AnyStringFallback(CommandArgument<String> argument, boolean joinRemainingArgs) {
		super(
				Validate.notNull(argument, "argument is null"),
				new StringArgument(argument.getName(), joinRemainingArgs)
		);
	}
}
