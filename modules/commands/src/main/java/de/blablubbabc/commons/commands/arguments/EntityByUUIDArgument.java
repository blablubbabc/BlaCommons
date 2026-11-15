package de.blablubbabc.commons.commands.arguments;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.filter.ArgumentFilter;
import de.blablubbabc.commons.commands.context.CommandContextView;

/**
 * Determines an existing entity by the given UUID input.
 */
public class EntityByUUIDArgument extends ObjectByIdArgument<UUID, Entity> {

	public EntityByUUIDArgument(String name) {
		this(name, ArgumentFilter.acceptAny());
	}

	public EntityByUUIDArgument(String name, ArgumentFilter<? super Entity> filter) {
		this(name, filter, EntityUUIDArgument.DEFAULT_MINIMUM_COMPLETION_INPUT);
	}

	public EntityByUUIDArgument(
			String name,
			ArgumentFilter<? super Entity> filter,
			int minimumCompletionInput
	) {
		super(name, filter, new IdArgumentArgs(minimumCompletionInput));
	}

	@Override
	protected ObjectIdArgument<UUID> createIdArgument(
			@UnknownInitialization EntityByUUIDArgument this,
			String name,
			IdArgumentArgs args
	) {
		return new EntityUUIDArgument(
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
				return EntityByUUIDArgument.this.getCompletionSuggestions(
						input,
						context,
						minimumCompletionInput,
						idPrefix
				);
			}
		};
	}

	@Override
	protected @Nullable Entity getObject(
			CommandInput input,
			CommandContextView context,
			UUID uuid
	) throws ArgumentParseException {
		return Bukkit.getEntity(uuid);
	}

	@Override
	protected Iterable<? extends UUID> getCompletionSuggestions(
			CommandInput input,
			CommandContextView context,
			int minimumCompletionInput,
			String idPrefix
	) {
		return EntityUUIDArgument.getDefaultCompletionSuggestions(
				input,
				context,
				minimumCompletionInput,
				idPrefix,
				filter
		);
	}
}
