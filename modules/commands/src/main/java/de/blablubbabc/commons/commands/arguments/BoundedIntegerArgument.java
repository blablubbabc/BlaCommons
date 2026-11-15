package de.blablubbabc.commons.commands.arguments;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.Validate;

public class BoundedIntegerArgument extends IntegerArgument {

	private final int min;
	private final int max;

	public BoundedIntegerArgument(String name, int min, int max) {
		super(name);
		Validate.isTrue(min <= max, "min > max");
		this.min = min;
		this.max = max;
	}

	// TODO More descriptive error messages

	@Override
	public Integer parseValue(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		Integer value = super.parseValue(input, context, argsReader);
		assert value != null;
		if (value < min || value > max) {
			throw this.invalidArgumentError(argsReader.current());
		}
		return value;
	}
}
