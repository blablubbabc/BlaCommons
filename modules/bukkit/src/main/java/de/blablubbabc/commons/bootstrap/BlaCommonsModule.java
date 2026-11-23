package de.blablubbabc.commons.bootstrap;

import org.bukkit.plugin.Plugin;

import de.blablubbabc.commons.compat.CompatProvider;

/**
 * A library module that requires setup during the lifecycle of the host plugin.
 * <p>
 * Modules are passed to {@link BlaCommons#onLoad(Plugin, CompatProvider, BlaCommonsModule...)},
 * which then invokes their lifecycle methods.
 */
public interface BlaCommonsModule {

	/**
	 * This is called during {@link BlaCommons#onLoad(Plugin, CompatProvider, BlaCommonsModule...)}.
	 * 
	 * @param plugin
	 *            the host plugin, not <code>null</code>
	 */
	public default void onLoad(Plugin plugin) {
	}

	/**
	 * This is called during {@link BlaCommons#onEnable()}.
	 */
	public default void onEnable() {
	}

	/**
	 * This is called during {@link BlaCommons#onDisable()}.
	 */
	public default void onDisable() {
	}
}
