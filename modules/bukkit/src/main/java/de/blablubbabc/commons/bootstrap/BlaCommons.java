package de.blablubbabc.commons.bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.logging.Log;
import de.blablubbabc.commons.util.java.Validate;

/**
 * Entry point for plugins that use this library.
 * <p>
 * The host plugin is expected to call {@link #onLoad(Plugin, BlaCommonsModule...)} early during its
 * <code>onLoad</code>, {@link #onEnable()} at the start of its <code>onEnable</code>, and
 * {@link #onDisable()} at the end of its <code>onDisable</code>. This way, the library modules are
 * available to the host plugin's own components during their setup and shutdown.
 * <p>
 * The host plugin passes the {@link BlaCommonsModule}s of the used library modules to
 * {@link #onLoad(Plugin, BlaCommonsModule...)}. Their lifecycle methods are then invoked together
 * with the corresponding lifecycle methods of this class.
 * <p>
 * Library components that require a {@link Plugin}, for example to register event listeners or to
 * schedule tasks, use {@link #getPlugin()}.
 */
public final class BlaCommons {

	// Not expected to require volatile: Set during plugin loading and then (ideally) never unset.
	private static @Nullable Plugin plugin = null;
	private static boolean enabled = false;
	private static List<? extends BlaCommonsModule> modules = Collections.emptyList();

	/**
	 * Sets up the library for the given host plugin.
	 * <p>
	 * This also sets the {@link Log#setLogger(Logger) logger} to the logger of the plugin.
	 * <p>
	 * The given modules are loaded in the given order. Modules that depend on other modules have to
	 * be specified after these other modules.
	 * 
	 * @param plugin
	 *            the host plugin, not <code>null</code>
	 * @param modules
	 *            the used library modules, not <code>null</code>
	 */
	public static void onLoad(Plugin plugin, @NonNull BlaCommonsModule... modules) {
		Validate.notNull(plugin, "plugin is null");
		Validate.notNull(modules, "modules is null");

		var moduleList = new ArrayList<>(Arrays.asList(modules));
		Validate.noNullElements(moduleList, "modules contains null");

		BlaCommons.plugin = plugin;
		BlaCommons.modules = moduleList;
		Log.setLogger(plugin.getLogger());

		BlaCommons.modules.forEach(module -> module.onLoad(plugin));
	}

	/**
	 * Marks the library as enabled.
	 * 
	 * @throws IllegalStateException
	 *             if {@link #onLoad(Plugin, BlaCommonsModule...)} has not been called yet
	 */
	public static void onEnable() {
		Validate.State.notNull(plugin, "onLoad has not been called yet!");

		modules.forEach(BlaCommonsModule::onEnable);
		enabled = true;
	}

	/**
	 * Marks the library as disabled.
	 */
	public static void onDisable() {
		enabled = false;

		// Reverse order:
		for (int i = modules.size() - 1; i >= 0; i--) {
			modules.get(i).onDisable();
		}
	}

	/**
	 * Gets the host plugin.
	 * 
	 * @return the host plugin, not <code>null</code>
	 * @throws IllegalStateException
	 *             if {@link #onLoad(Plugin, BlaCommonsModule...)} has not been called yet
	 */
	public static Plugin getPlugin() {
		return Validate.State.notNull(plugin, "onLoad has not been called yet!");
	}

	/**
	 * Checks if the library is currently enabled, i.e. {@link #onEnable()} has been called and
	 * {@link #onDisable()} has not been called since.
	 * 
	 * @return <code>true</code> if enabled
	 */
	public static boolean isEnabled() {
		return enabled;
	}

	private BlaCommons() {
	}
}
