package de.blablubbabc.commons.text.bootstrap;

import org.bukkit.plugin.Plugin;

import de.blablubbabc.commons.bootstrap.BlaCommonsModule;
import de.blablubbabc.commons.text.Text;
import de.blablubbabc.commons.util.text.ArgumentsReplacer;

/**
 * The {@link BlaCommonsModule} of the text module.
 */
public final class TextModule implements BlaCommonsModule {

	public static final TextModule INSTANCE = new TextModule();

	private TextModule() {
	}

	@Override
	public void onLoad(Plugin plugin) {
		// String replacement only supports plain strings:
		ArgumentsReplacer.registerConverter(Text.class, Text::toPlainText);
	}
}
