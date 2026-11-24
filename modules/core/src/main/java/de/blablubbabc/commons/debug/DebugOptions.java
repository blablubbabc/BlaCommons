package de.blablubbabc.commons.debug;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Registry of the available debug options.
 * <p>
 * The library modules register their debug options when they are loaded. Host plugins can register
 * their own debug options as well.
 */
public final class DebugOptions {

	private static final Set<String> ALL_OPTIONS = new LinkedHashSet<>();
	private static final Set<? extends String> ALL_OPTIONS_VIEW
			= Collections.unmodifiableSet(ALL_OPTIONS);

	public static String add(String debugOption) {
		ALL_OPTIONS.add(debugOption);
		return debugOption;
	}

	public static void addAll(Collection<? extends String> debugOptions) {
		ALL_OPTIONS.addAll(debugOptions);
	}

	public static Set<? extends String> getAll() {
		return ALL_OPTIONS_VIEW;
	}

	private DebugOptions() {
	}
}
