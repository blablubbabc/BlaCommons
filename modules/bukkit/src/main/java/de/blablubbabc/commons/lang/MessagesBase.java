package de.blablubbabc.commons.lang;

import java.util.List;

import de.blablubbabc.commons.util.bukkit.TextUtils;

/**
 * Base class for classes that provide localizable messages via static fields.
 * <p>
 * Provides helpers for {@code &}-based color codes.
 */
public abstract class MessagesBase {

	protected MessagesBase() {
	}

	// CONVENIENCE HELPERS

	/**
	 * Translates {@code &}-based color codes to Minecraft's {@code §}-based color codes.
	 * 
	 * @param text
	 *            the text with {@code &}-based color codes
	 * @return the text with Minecraft's color codes
	 */
	protected static String c(String text) {
		return TextUtils.colorize(text);
	}

	/**
	 * Translates {@code &}-based color codes to Minecraft's {@code §}-based color codes.
	 * 
	 * @param texts
	 *            the texts with {@code &}-based color codes, not <code>null</code>
	 * @return a new list containing the corresponding texts with Minecraft's color codes
	 */
	protected static List<String> c(List<? extends String> texts) {
		return TextUtils.colorize(texts);
	}
}
