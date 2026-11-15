package de.blablubbabc.commons.commands.arguments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.ConversionUtils;
import de.blablubbabc.commons.util.java.Unsafe;
import de.blablubbabc.commons.util.java.Validate;

public class EnumArgument<T extends Enum<T>> extends CommandArgument<@NonNull T> {

	private final Class<@NonNull T> clazz;

	public EnumArgument(String name, Class<@NonNull T> clazz) {
		super(name);
		Validate.notNull(clazz, "clazz is null");
		this.clazz = clazz;
	}

	@Override
	public T parseValue(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		if (!argsReader.hasNext()) {
			throw this.missingArgumentError();
		}
		String argument = argsReader.next();
		@Nullable T value = ConversionUtils.parseEnum(clazz, argument);
		if (value == null) {
			throw this.invalidArgumentError(argument);
		}
		return value;
	}

	@Override
	public List<? extends String> complete(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) {
		if (argsReader.getRemainingSize() != 1) {
			return Collections.emptyList();
		}

		List<String> suggestions = new ArrayList<>();
		String partialArg = argsReader.next().toUpperCase();
		@NonNull T[] enumValues = Unsafe.assertNonNull(clazz.getEnumConstants());
		for (T value : enumValues) {
			if (suggestions.size() >= MAX_SUGGESTIONS) break;
			if (value.name().toUpperCase().startsWith(partialArg)) {
				suggestions.add(value.name());
			}
		}
		return Collections.unmodifiableList(suggestions);
	}
}
