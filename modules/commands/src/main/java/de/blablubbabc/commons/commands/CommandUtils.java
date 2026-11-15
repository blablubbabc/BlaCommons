package de.blablubbabc.commons.commands;

import java.util.Locale;

import de.blablubbabc.commons.util.java.Validate;

public final class CommandUtils {

	public static String normalize(String label) {
		Validate.notNull(label, "label is null");
		return label.toLowerCase(Locale.ROOT);
	}

	private CommandUtils() {
	}
}
