package de.blablubbabc.sample;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import de.blablubbabc.commons.bootstrap.BlaCommons;
import de.blablubbabc.commons.commands.bootstrap.CommandsModule;
import de.blablubbabc.commons.config.bootstrap.ConfigModule;
import de.blablubbabc.commons.data.bootstrap.DataModule;
import de.blablubbabc.commons.debug.Debug;
import de.blablubbabc.commons.input.bootstrap.InputModule;
import de.blablubbabc.commons.logging.Log;
import de.blablubbabc.commons.text.Text;
import de.blablubbabc.commons.text.bootstrap.TextModule;
import de.blablubbabc.commons.text.util.TextHelpers;
import de.blablubbabc.commons.ui.bootstrap.UIModule;

public class SamplePlugin extends JavaPlugin {

	@Override
	public void onLoad() {
		BlaCommons.onLoad(
				this,
				new SampleCompatProvider(),
				TextModule.INSTANCE,
				DataModule.INSTANCE,
				ConfigModule.INSTANCE,
				CommandsModule.INSTANCE,
				UIModule.INSTANCE,
				InputModule.INSTANCE
		);

		this.saveDefaultConfig();
		Configuration config = this.getConfig();
		Debug.setDebugging(config.getBoolean("debug"));
		Debug.setDebugOptions(config.getStringList("debug-options"));
	}

	@Override
	public void onEnable() {
		BlaCommons.onEnable();
		Log.info("BlaCommons sample plugin enabled.");
		Log.debug("Debugging is enabled.");
	}

	@Override
	public void onDisable() {
		BlaCommons.onDisable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Text message = Text.parse("&aBlaCommons works: &6{plugin}");
		TextHelpers.sendMessage(sender, message, "plugin", this.getName());
		return true;
	}
}
