package de.blablubbabc.commons.commands.arguments;

import java.util.List;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.argument.ArgumentParseException;
import de.blablubbabc.commons.commands.argument.ArgumentsReader;
import de.blablubbabc.commons.commands.argument.CommandArgument;
import de.blablubbabc.commons.commands.argument.fallback.FallbackArgument;
import de.blablubbabc.commons.commands.argument.fallback.FallbackArgumentException;
import de.blablubbabc.commons.commands.context.CommandContext;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.Pair;
import de.blablubbabc.commons.util.java.Unsafe;

/**
 * 
 * A {@link CommandArgument} similar to {@link FirstOfArgument}, but which preserves the result type
 * of its child arguments. This requires all child arguments to parse a value of the same type.
 * 
 * @param <T>
 *            the type of the parsed argument
 */
public class TypedFirstOfArgument<T> extends FallbackArgument<T> {

	// Reusing the implementation of FirstOfArgument:
	private final FirstOfArgument firstOfArgument;

	public TypedFirstOfArgument(
			String name,
			List<? extends CommandArgument<T>> arguments
	) {
		this(name, arguments, true, false);
	}

	public TypedFirstOfArgument(
			String name,
			List<? extends CommandArgument<T>> arguments,
			boolean joinFormats
	) {
		this(name, arguments, joinFormats, false);
	}

	public TypedFirstOfArgument(
			String name,
			List<? extends CommandArgument<T>> arguments,
			boolean joinFormats,
			boolean reverseFormat
	) {
		super(name);
		this.firstOfArgument = new FirstOfArgument(
				name + ":firstOf",
				arguments,
				joinFormats,
				reverseFormat
		);
		this.firstOfArgument.setParent(this);
	}

	@Override
	public boolean isOptional() {
		return firstOfArgument.isOptional();
	}

	@Override
	public String getReducedFormat() {
		return firstOfArgument.getReducedFormat();
	}

	// This is expected to only return null if T is nullable.
	@SuppressWarnings("unchecked")
	private T getValue(@Nullable Pair<? extends CommandArgument<?>, ?> result) {
		return (result != null) ? (T) result.getSecond() : Unsafe.uncheckedNull();
	}

	@Override
	public T parse(
			CommandInput input,
			CommandContext context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		T value = this.getValue(firstOfArgument.parse(input, context, argsReader));
		// TODO Separate variable is required due to a Checker Framework false positive, caused by
		// the if condition
		T result = value;
		if (value != null) {
			context.put(this.getName(), value);
		}
		return result;
	}

	@Override
	public T parseValue(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) throws ArgumentParseException {
		return this.getValue(firstOfArgument.parseValue(input, context, argsReader));
	}

	@Override
	public List<? extends String> complete(
			CommandInput input,
			CommandContextView context,
			ArgumentsReader argsReader
	) {
		return firstOfArgument.complete(input, context, argsReader);
	}

	@Override
	public T parseFallback(
			CommandInput input,
			CommandContext context,
			ArgumentsReader argsReader,
			FallbackArgumentException fallbackException,
			boolean parsingFailed
	) throws ArgumentParseException {
		return this.getValue(firstOfArgument.parseFallback(
				input,
				context,
				argsReader,
				fallbackException,
				parsingFailed
		));
	}
}
