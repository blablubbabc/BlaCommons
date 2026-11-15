package de.blablubbabc.commons.debug;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.util.java.Validate;

/**
 * Access to the debugging state.
 * <p>
 * The debugging state is provided by the plugin early during startup via
 * {@link #setProvider(DebugProvider)}.
 */
public final class Debug {

	/**
	 * Provides the current debugging state.
	 */
	@FunctionalInterface
	public interface DebugProvider {

		/**
		 * Checks if debugging is enabled, optionally for a specific debug option.
		 * <p>
		 * This may be called from any thread.
		 * 
		 * @param option
		 *            the debug option, or <code>null</code> to only check if debugging is enabled
		 *            in general
		 * @return <code>true</code> if debugging is enabled (for the given option)
		 */
		public boolean isDebugging(@Nullable String option);
	}

	// Gets set early on plugin startup and then (ideally) never unset.
	// -> Volatile is therefore not expected to be required.
	private static DebugProvider provider = option -> false;

	/**
	 * Sets the {@link DebugProvider}.
	 * 
	 * @param provider
	 *            the provider, not <code>null</code>
	 */
	public static void setProvider(DebugProvider provider) {
		Validate.notNull(provider, "provider");
		Debug.provider = provider;
	}

	public static DebugProvider getProvider() {
		return Validate.State.notNull(provider, "No DebugProvider set!");
	}

	public static boolean isDebugging() {
		return isDebugging(null);
	}

	public static boolean isDebugging(@Nullable String option) {
		// Thread-safe:
		return getProvider().isDebugging(option);
	}

	private Debug() {
	}
}
