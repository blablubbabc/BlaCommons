package de.blablubbabc.commons.debug;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Registry of the available debug options.
 */
public final class DebugOptions {

	private static final Set<String> ALL_OPTIONS = new LinkedHashSet<>();
	private static final Set<? extends String> ALL_OPTIONS_VIEW = Collections.unmodifiableSet(ALL_OPTIONS);

	// Logs all events (spams!). Starts slightly delayed. Subsequent calls of the same event get
	// combined into a single logging entry to slightly reduce spam.
	public static final String LOG_ALL_EVENTS = add("log-all-events");
	// Prints the registered listeners for the first call of each event.
	public static final String PRINT_LISTENERS = add("print-listeners");
	// Enables additional commands related debugging output.
	public static final String COMMANDS = add("commands");
	// Logs additional debug output whenever component-based text is sent.
	public static final String TEXT_COMPONENTS = add("text-components");
	// Logs additional debug output whenever a teleport is considered unsafe.
	public static final String UNSAFE_TELEPORTS = add("unsafe-teleports");

	public static String add(String debugOption) {
		ALL_OPTIONS.add(debugOption);
		return debugOption;
	}

	public static Set<? extends String> getAll() {
		return ALL_OPTIONS_VIEW;
	}

	private DebugOptions() {
	}
}
