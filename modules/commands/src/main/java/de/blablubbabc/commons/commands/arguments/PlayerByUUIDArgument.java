package de.blablubbabc.commons.commands.arguments;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.CommandMessages;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.filter.ArgumentFilter;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.text.Text;

/**
 * Determines an online player by the given UUID input.
 */
public class PlayerByUUIDArgument extends ObjectByIdArgument<UUID, Player> {

	public PlayerByUUIDArgument(String name) {
		this(name, ArgumentFilter.acceptAny());
	}

	public PlayerByUUIDArgument(String name, ArgumentFilter<? super Player> filter) {
		this(name, filter, PlayerUUIDArgument.DEFAULT_MINIMUM_COMPLETION_INPUT);
	}

	public PlayerByUUIDArgument(
			String name,
			ArgumentFilter<? super Player> filter,
			int minimumCompletionInput
	) {
		super(name, filter, new IdArgumentArgs(minimumCompletionInput));
	}

	@Override
	protected ObjectIdArgument<UUID> createIdArgument(
			@UnknownInitialization PlayerByUUIDArgument this,
			String name,
			IdArgumentArgs args
	) {
		return new PlayerUUIDArgument(
				name,
				ArgumentFilter.acceptAny(),
				args.minimumCompletionInput
		) {
			@Override
			protected Iterable<? extends UUID> getCompletionSuggestions(
					CommandInput input,
					CommandContextView context,
					String idPrefix
			) {
				return PlayerByUUIDArgument.this.getCompletionSuggestions(
						input,
						context,
						minimumCompletionInput,
						idPrefix
				);
			}
		};
	}

	@Override
	protected Text getInvalidArgumentErrorMsgText() {
		return CommandMessages.commandPlayerArgumentInvalid;
	}

	@Override
	protected @Nullable Player getObject(
			CommandInput input,
			CommandContextView context,
			UUID uuid
	) throws ArgumentParseException {
		return Bukkit.getPlayer(uuid);
	}

	@Override
	protected Iterable<? extends UUID> getCompletionSuggestions(
			CommandInput input,
			CommandContextView context,
			int minimumCompletionInput,
			String idPrefix
	) {
		return PlayerUUIDArgument.getDefaultCompletionSuggestions(
				input,
				context,
				minimumCompletionInput,
				idPrefix,
				filter
		);
	}
}
