package de.blablubbabc.commons.commands.arguments;

import org.bukkit.entity.Entity;

import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.argument.fallback.FallbackArgument;
import de.blablubbabc.commons.commands.arguments.TargetEntityArgument.TargetEntityFilter;
import de.blablubbabc.commons.util.java.Validate;

/**
 * A {@link FallbackArgument} that returns the targeted entity without consuming any arguments.
 * <p>
 * If the sender is not a player, the parsing exception of the original argument is thrown (the
 * original argument might get reevaluated is some parsing context has changed).
 */
public class TargetEntityFallback extends TypedFallbackArgument<Entity> {

	public TargetEntityFallback(CommandArgument<Entity> argument) {
		this(argument, TargetEntityFilter.ANY);
	}

	public TargetEntityFallback(
			CommandArgument<Entity> argument,
			TargetEntityFilter filter
	) {
		super(
				Validate.notNull(argument, "argument is null"),
				new TargetEntityArgument(argument.getName(), filter)
		);
	}
}
