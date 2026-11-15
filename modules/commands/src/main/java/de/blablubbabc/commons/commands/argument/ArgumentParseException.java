package de.blablubbabc.commons.commands.argument;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.commands.Command;
import de.blablubbabc.commons.commands.CommandException;
import de.blablubbabc.commons.text.Text;

/**
 * This exception is thrown when an error occurs during command argument parsing, for example if an
 * argument is of invalid format.
 */
public class ArgumentParseException extends CommandException {

	private static final long serialVersionUID = -4968777515685479426L;

	// Can be null if not caused by any CommandArgument:
	private final @Nullable CommandArgument<?> argument;

	public ArgumentParseException(@Nullable CommandArgument<?> argument, Text message) {
		this(argument, message, null);
	}

	public ArgumentParseException(
			@Nullable CommandArgument<?> argument,
			Text message,
			@Nullable Throwable cause
	) {
		super(message, cause);
		this.argument = argument;
	}

	/**
	 * Gets the {@link CommandArgument} that created this exception.
	 * 
	 * @return the command argument, or <code>null</code> if this exception was not created by any
	 *         command argument (but for example by the parsing {@link Command} itself)
	 */
	public @Nullable CommandArgument<?> getArgument() {
		return argument;
	}
}
