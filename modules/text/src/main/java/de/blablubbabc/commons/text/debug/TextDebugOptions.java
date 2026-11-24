package de.blablubbabc.commons.text.debug;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The debug options of the text module.
 */
public final class TextDebugOptions {

	private static final Set<String> ALL_OPTIONS = new LinkedHashSet<>();
	private static final Set<? extends String> ALL_OPTIONS_VIEW
			= Collections.unmodifiableSet(ALL_OPTIONS);

	/**
	 * Logs additional debug output whenever component-based text is sent.
	 */
	public static final String TEXT_COMPONENTS = add("text-components");

	private static String add(String debugOption) {
		ALL_OPTIONS.add(debugOption);
		return debugOption;
	}

	public static Set<? extends String> getAll() {
		return ALL_OPTIONS_VIEW;
	}

	private TextDebugOptions() {
	}
}
