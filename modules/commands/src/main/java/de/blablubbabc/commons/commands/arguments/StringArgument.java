package de.blablubbabc.commons.commands.arguments;

import java.util.Collections;
import java.util.List;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.context.CommandContextView;

public class StringArgument extends CommandArgument<String> {

	protected final boolean joinRemainingArgs;

	public StringArgument(String name) {
		this(name, false);
	}

	public StringArgument(String name, boolean joinRemainingArgs) {
		super(name);
		this.joinRemainingArgs = joinRemainingArgs;
	}

	public final boolean isJoiningRemainingArgs() {
		return joinRemainingArgs;
	}

	@Override
	public String parseValue(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		if (!argsReader.hasNext()) {
			throw this.missingArgumentError();
		}
		if (joinRemainingArgs) {
			return getJoinedRemainingArgs(argsReader);
		} else {
			return argsReader.next();
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

	public static String getJoinedRemainingArgs(ArgumentsReader argsReader) {
		if (!argsReader.hasNext()) return "";
		StringBuilder value = new StringBuilder(argsReader.next());
		while (argsReader.hasNext()) {
			value.append(' ').append(argsReader.next());
		}
		return value.toString();
	}
}
