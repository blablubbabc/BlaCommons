package de.blablubbabc.commons.commands.arguments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.ConversionUtils;
import de.blablubbabc.commons.util.java.Trilean;

public class TrileanArgument extends CommandArgument<Trilean> {

	public TrileanArgument(String name) {
		super(name);
	}

	@Override
	public Trilean parseValue(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		if (!argsReader.hasNext()) {
			throw this.missingArgumentError();
		}
		String argument = argsReader.next();
		@Nullable Trilean value = ConversionUtils.parseTrilean(argument);
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
		String partialArg = argsReader.next().toLowerCase(Locale.ROOT);
		for (String value : ConversionUtils.TRILEAN_VALUES.keySet()) {
			if (suggestions.size() >= MAX_SUGGESTIONS) break;
			// The keys are already lowercase:
			if (value.startsWith(partialArg)) {
				suggestions.add(value);
			}
		}
		return Collections.unmodifiableList(suggestions);
	}
}
