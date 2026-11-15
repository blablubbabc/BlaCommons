package de.blablubbabc.commons.compat;

import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.util.java.Validate;

/**
 * Provides access to the {@link CompatProvider} that implements the server version and server
 * implementation specific functionality.
 * <p>
 * The host plugin has to {@link #setProvider(CompatProvider) set} the provider early during its
 * startup.
 */
public final class Compat {

	private static @Nullable CompatProvider provider = null;

	/**
	 * Sets the {@link CompatProvider}.
	 *
	 * @param provider
	 *            the provider, or <code>null</code> to unset the current provider
	 */
	public static void setProvider(@Nullable CompatProvider provider) {
		Compat.provider = provider;
	}

	/**
	 * Gets the {@link CompatProvider}.
	 *
	 * @return the provider, not <code>null</code>
	 * @throws IllegalStateException
	 *             if no provider is set
	 */
	public static CompatProvider getProvider() {
		return Validate.State.notNull(provider, "No compat provider set!");
	}

	private Compat() {
	}
}
