package de.blablubbabc.commons.commands.arguments;

import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.argument.fallback.FallbackArgument;
import de.blablubbabc.commons.util.java.Validate;

/**
 * A {@link FallbackArgument} that returns a fixed value as fallback.
 * 
 * @param <T>
 *            the type of the parsed argument
 * @see FixedValueArgument
 */
public class DefaultValueFallback<T> extends TypedFallbackArgument<T> {

	public DefaultValueFallback(CommandArgument<T> argument, T defaultValue) {
		super(
				Validate.notNull(argument, "argument is null"),
				new FixedValueArgument<>(argument.getName(), defaultValue)
		);
	}
}
