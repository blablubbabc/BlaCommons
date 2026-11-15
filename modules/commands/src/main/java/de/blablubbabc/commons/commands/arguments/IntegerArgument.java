package de.blablubbabc.commons.commands.arguments;

import java.util.Collections;
import java.util.List;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.ConversionUtils;

public class IntegerArgument extends CommandArgument<Integer> {

	public IntegerArgument(String name) {
		super(name);
	}

	@Override
	public Integer parseValue(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		if (!argsReader.hasNext()) {
			throw this.missingArgumentError();
		}
		String argument = argsReader.next();
		Integer value = ConversionUtils.parseInt(argument);
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
		return Collections.emptyList();
	}
}
