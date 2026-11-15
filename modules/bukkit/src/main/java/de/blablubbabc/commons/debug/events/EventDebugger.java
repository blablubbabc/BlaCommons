package de.blablubbabc.commons.debug.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.blablubbabc.commons.debug.Debug;
import de.blablubbabc.commons.debug.DebugOptions;
import de.blablubbabc.commons.util.java.Validate;

/**
 * Tool to debug event handlers.
 */
public class EventDebugger {

	private final Plugin plugin;

	public EventDebugger(Plugin plugin) {
		Validate.notNull(plugin, "plugin is null");
		this.plugin = plugin;
	}

	public void onEnable() {
		if (Debug.isDebugging()) {
			// Register debug listener if enabled:
			// Run delayed to also catch events / event listeners of other plugins.
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				boolean logAllEvent = Debug.isDebugging(DebugOptions.LOG_ALL_EVENTS);
				boolean printListeners = Debug.isDebugging(DebugOptions.PRINT_LISTENERS);
				if (logAllEvent || printListeners) {
					DebugListener.register(logAllEvent, printListeners);
				}
			}, 10L);
		}
	}

	public void onDisable() {
	}
}
