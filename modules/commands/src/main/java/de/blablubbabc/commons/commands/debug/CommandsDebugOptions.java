package de.blablubbabc.commons.commands.debug;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The debug options of the commands module.
 */
public final class CommandsDebugOptions {

	private static final Set<String> ALL_OPTIONS = new LinkedHashSet<>();
	private static final Set<? extends String> ALL_OPTIONS_VIEW
			= Collections.unmodifiableSet(ALL_OPTIONS);

	/**
	 * Enables additional commands related debugging output.
	 */
	public static final String COMMANDS = add("commands");

	private static String add(String debugOption) {
		ALL_OPTIONS.add(debugOption);
		return debugOption;
	}

	public static Set<? extends String> getAll() {
		return ALL_OPTIONS_VIEW;
	}

	private CommandsDebugOptions() {
	}
}
