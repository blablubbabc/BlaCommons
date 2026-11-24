package de.blablubbabc.commons.debug;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.util.java.Validate;

/**
 * Access to the debugging state.
 * <p>
 * Debugging is disabled by default. The host plugin can enable debugging and individual
 * {@link DebugOptions debug options} at any time, for example when it loads its config or via a
 * command.
 * <p>
 * The debugging state can be checked from any thread.
 */
public final class Debug {

	private record DebugSettings(boolean debugging, Set<? extends String> debugOptions) {
	}

	// Replaced as a whole, so that it can be read consistently from any thread:
	private static volatile DebugSettings settings = new DebugSettings(
			false,
			Collections.emptySet()
	);

	/**
	 * Checks if debugging is enabled.
	 * 
	 * @return <code>true</code> if debugging is enabled
	 */
	public static boolean isDebugging() {
		return settings.debugging();
	}

	/**
	 * Checks if debugging is enabled, optionally for a specific debug option.
	 * <p>
	 * Debug options are only considered enabled while debugging is enabled.
	 * 
	 * @param option
	 *            the debug option, or <code>null</code> to only check if debugging is enabled in
	 *            general
	 * @return <code>true</code> if debugging is enabled (for the given option)
	 */
	public static boolean isDebugging(@Nullable String option) {
		DebugSettings settings = Debug.settings;
		return settings.debugging()
				&& (option == null || settings.debugOptions().contains(option));
	}

	/**
	 * Enables or disables debugging.
	 * 
	 * @param debugging
	 *            <code>true</code> to enable debugging
	 */
	public static synchronized void setDebugging(boolean debugging) {
		settings = new DebugSettings(debugging, settings.debugOptions());
	}

	/**
	 * Gets the enabled debug options.
	 * <p>
	 * This also returns the enabled debug options while debugging is disabled.
	 * 
	 * @return an unmodifiable snapshot of the enabled debug options, not <code>null</code>
	 */
	public static Set<? extends String> getDebugOptions() {
		return settings.debugOptions();
	}

	/**
	 * Sets the enabled debug options.
	 * 
	 * @param debugOptions
	 *            the debug options, not <code>null</code>
	 */
	public static synchronized void setDebugOptions(Collection<? extends String> debugOptions) {
		Validate.notNull(debugOptions, "debugOptions is null");
		Validate.noNullElements(debugOptions, "debugOptions contains null");
		setDebugOptionsUnchecked(new LinkedHashSet<>(debugOptions));
	}

	/**
	 * Enables or disables the given debug option.
	 * 
	 * @param debugOption
	 *            the debug option, not <code>null</code>
	 * @param enabled
	 *            <code>true</code> to enable the debug option
	 */
	public static synchronized void setDebugOption(String debugOption, boolean enabled) {
		Validate.notNull(debugOption, "debugOption is null");
		Set<String> debugOptions = new LinkedHashSet<>(settings.debugOptions());
		if (enabled) {
			debugOptions.add(debugOption);
		} else {
			debugOptions.remove(debugOption);
		}
		setDebugOptionsUnchecked(debugOptions);
	}

	private static void setDebugOptionsUnchecked(Set<? extends String> debugOptions) {
		settings = new DebugSettings(
				settings.debugging(),
				Collections.unmodifiableSet(debugOptions)
		);
	}

	private Debug() {
	}
}
