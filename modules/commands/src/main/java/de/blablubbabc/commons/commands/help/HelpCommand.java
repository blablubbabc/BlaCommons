package de.blablubbabc.commons.commands.help;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

import de.blablubbabc.commons.commands.Command;
import de.blablubbabc.commons.commands.CommandException;
import de.blablubbabc.commons.commands.CommandInput;
import de.blablubbabc.commons.commands.context.CommandContextView;
import de.blablubbabc.commons.util.java.Validate;

/**
 * A {@link Command} which simply calls {@link Command#sendHelp(CommandSender)} of the given source
 * {@link Command}.
 */
public class HelpCommand extends Command {

	private final Command helpSource;

	public HelpCommand(String name, Command helpSource) {
		this(name, Collections.emptyList(), helpSource);
	}

	public HelpCommand(String name, List<String> aliases, Command helpSource) {
		super(name, aliases);
		Validate.notNull(helpSource, "helpSource is null");
		this.helpSource = helpSource;
	}

	@Override
	protected void execute(CommandInput input, CommandContextView context) throws CommandException {
		helpSource.sendHelp(input.getSender());
	}
}
