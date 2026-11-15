package de.blablubbabc.commons.commands;

import de.blablubbabc.commons.lang.MessagesBase;
import de.blablubbabc.commons.text.Text;

/**
 * The messages used by the commands library.
 * <p>
 * The fields are intentionally not final: Plugins that use this library may assign their own
 * (e.g. translated) messages, for example after loading their language files.
 */
public final class CommandMessages extends MessagesBase {

	public static Text noPermission = Text.parse("&cYou don't have the permission to do that.");
	public static Text mustTargetEntity = Text.parse("&7You have to target an entity.");

	public static Text commandUnknown = Text.parse("&cUnknown command '&e{command}&c'!");
	public static Text commandArgumentUnexpected = Text.parse("&cUnexpected argument '&e{argument}&c'.");
	public static Text commandArgumentRequiresPlayer = Text.parse("&cArgument '&e{argumentFormat}&c' requires a player to execute the command.");
	public static Text commandArgumentMissing = Text.parse("&cMissing argument '&e{argumentFormat}&c'.");
	public static Text commandArgumentInvalid = Text.parse("&cInvalid argument '&e{argument}&c'.");
	public static Text commandPlayerArgumentMissing = Text.parse("&cNo player specified for '&e{argumentFormat}&c'.");
	public static Text commandPlayerArgumentInvalid = Text.parse("&cNo player found for '&e{argument}&c'.");

	public static Text ambiguousPlayerName = Text.parse("&cThere are multiple players for the name '&e{name}&c'!");
	public static Text ambiguousPlayerNameEntry = Text.parse("&c  - '&e{name}&r&c' (&6{uuid}&c)");
	public static Text ambiguousPlayerNameMore = Text.parse("&c  - ....");

	private CommandMessages() {
	}
}
