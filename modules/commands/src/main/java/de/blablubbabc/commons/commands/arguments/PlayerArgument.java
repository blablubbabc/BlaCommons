package de.blablubbabc.commons.commands.arguments;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.argument.filter.ArgumentFilter;
import de.blablubbabc.commons.commands.argument.filter.ArgumentRejectedException;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.Unsafe;
import de.blablubbabc.commons.util.java.Validate;

/**
 * Accepts a player specified by either UUID or name (might not have to be exact, depending on the
 * used matching function).
 */
public class PlayerArgument extends CommandArgument<Player> {

	protected final ArgumentFilter<? super Player> filter; // Not null
	private final PlayerByUUIDArgument playerUUIDArgument;
	private final PlayerByNameArgument playerNameArgument;
	private final TypedFirstOfArgument<Player> firstOfArgument;

	public PlayerArgument(String name) {
		this(name, ArgumentFilter.acceptAny());
	}

	public PlayerArgument(String name, ArgumentFilter<? super Player> filter) {
		this(
				name,
				filter,
				PlayerNameArgument.DEFAULT_MINIMUM_COMPLETION_INPUT,
				PlayerUUIDArgument.DEFAULT_MINIMUM_COMPLETION_INPUT
		);
	}

	public PlayerArgument(
			String name,
			ArgumentFilter<? super Player> filter,
			int minimalNameCompletionInput,
			int minimumUUIDCompletionInput
	) {
		super(name);
		Validate.notNull(filter, "filter is null");
		this.filter = filter;
		this.playerUUIDArgument = new PlayerByUUIDArgument(
				name + ":uuid",
				filter,
				minimumUUIDCompletionInput
		) {
			@Override
			protected Iterable<? extends UUID> getCompletionSuggestions(
					CommandInput input,
					CommandContextView context,
					int minimumCompletionInput,
					String idPrefix
			) {
				return Unsafe.initialized(PlayerArgument.this).getUUIDCompletionSuggestions(
						input,
						context,
						minimumCompletionInput,
						idPrefix
				);
			}
		};
		this.playerNameArgument = new PlayerByNameArgument(
				name + ":name",
				filter,
				minimalNameCompletionInput
		) {
			@Override
			public @Nullable Player getObject(
					CommandInput input,
					CommandContextView context,
					String nameInput
			) throws ArgumentParseException {
				return Unsafe.initialized(PlayerArgument.this).getPlayerByName(nameInput);
			}

			@Override
			protected Iterable<? extends String> getCompletionSuggestions(
					CommandInput input,
					CommandContextView context,
					int minimumCompletionInput,
					String idPrefix
			) {
				return Unsafe.initialized(PlayerArgument.this).getNameCompletionSuggestions(
						input,
						context,
						minimumCompletionInput,
						idPrefix
				);
			}
		};
		this.firstOfArgument = new TypedFirstOfArgument<>(
				name + ":firstOf",
				Arrays.asList(playerUUIDArgument, playerNameArgument),
				false,
				false
		);
		firstOfArgument.setParent(this);
	}

	@Override
	public Player parseValue(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		// Also handles argument exceptions:
		return firstOfArgument.parseValue(input, context, argsReader);
	}

	@Override
	public List<? extends String> complete(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) {
		return firstOfArgument.complete(input, context, argsReader);
	}

	/**
	 * Gets a {@link Player} that matches the given name input.
	 * <p>
	 * This can be overridden if a different behavior is required. You may also want to override
	 * {@link #getNameCompletionSuggestions(CommandInput, CommandContextView, int, String)} and
	 * {@link #getUUIDCompletionSuggestions(CommandInput, CommandContextView, int, String)} then.
	 * 
	 * @param nameInput
	 *            the name input
	 * @return the matched player, or <code>null</code>
	 * @throws ArgumentRejectedException
	 *             if the name is ambiguous
	 */
	public @Nullable Player getPlayerByName(String nameInput) throws ArgumentRejectedException {
		return playerNameArgument.getDefaultPlayerByName(nameInput);
	}

	/**
	 * Gets the name completion suggestions for the given name prefix.
	 * <p>
	 * This should take this argument's player filter into account.
	 * 
	 * @param input
	 *            the command input, not <code>null</code>
	 * @param context
	 *            the command context, not <code>null</code>
	 * @param minimumCompletionInput
	 *            the minimum input length before completion suggestions are provided
	 * @param idPrefix
	 *            the id prefix, may be empty, not <code>null</code>
	 * @return the suggestions
	 */
	protected Iterable<? extends String> getNameCompletionSuggestions(
			CommandInput input,
			CommandContextView context,
			int minimumCompletionInput,
			String idPrefix
	) {
		// Note: Whether to include display name suggestions usually depends on whether the used
		// matching function considers display names.
		return PlayerNameArgument.getDefaultCompletionSuggestions(
				input,
				context,
				minimumCompletionInput,
				idPrefix,
				filter,
				true
		);
	}

	/**
	 * Gets the uuid completion suggestions for the given name prefix.
	 * <p>
	 * This should take this argument's player filter into account.
	 * 
	 * @param input
	 *            the command input, not <code>null</code>
	 * @param context
	 *            the command context, not <code>null</code>
	 * @param minimumCompletionInput
	 *            the minimum input length before completion suggestions are provided
	 * @param idPrefix
	 *            the id prefix, may be empty, not <code>null</code>
	 * @return the suggestions
	 */
	protected Iterable<? extends UUID> getUUIDCompletionSuggestions(
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
