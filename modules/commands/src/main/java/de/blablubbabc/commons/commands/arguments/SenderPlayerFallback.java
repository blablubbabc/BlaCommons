package de.blablubbabc.commons.commands.arguments;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.argument.RequiresPlayerArgumentException;
import de.blablubbabc.commons.commands.argument.fallback.FallbackArgument;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.ObjectUtils;
import de.blablubbabc.commons.util.java.Validate;

/**
 * A {@link FallbackArgument} that returns the sender if it is a player, without consuming any
 * arguments.
 * <p>
 * If the sender is not a player, a {@link RequiresPlayerArgumentException} is thrown.
 */
public class SenderPlayerFallback extends TypedFallbackArgument<Player> {

	public static class SenderPlayerArgument extends CommandArgument<Player> {

		public SenderPlayerArgument(String name) {
			super(name);
		}

		@Override
		public boolean isOptional() {
			return true; // Does not require user input
		}

		@Override
		public Player parseValue(
				CommandInput input,
				CommandContextView context,
				ArgumentsReader argsReader
		) throws ArgumentParseException {
			Player player = ObjectUtils.castOrNull(input.getSender(), Player.class);
			if (player == null) {
				throw this.requiresPlayerError();
			} else {
				return player;
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

	public SenderPlayerFallback(CommandArgument<Player> argument) {
		super(
				Validate.notNull(argument, "argument is null"),
				new SenderPlayerArgument(argument.getName())
		);
	}
}
