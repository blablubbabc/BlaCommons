package de.blablubbabc.commons.util.bukkit;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import de.blablubbabc.commons.util.java.StringUtils;
import de.blablubbabc.commons.util.java.Unsafe;
import de.blablubbabc.commons.util.java.Validate;

/**
 * Text and messaging utilities.
 * <p>
 * In contrast to {@link StringUtils}, this contains utilities that are more Minecraft and messaging
 * specific.
 */
public final class TextUtils {

	/**
	 * A recommended maximum length limit (defined by us) for item lore lines to avoid the item
	 * hover to become to large in width.
	 */
	public static final int LORE_MAX_LENGTH = 32;

	/*
	 * PLAIN TEXT
	 */

	// FORMATTING AND CONVERSION

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(
			"0.##",
			new DecimalFormatSymbols(Locale.US)
	);
	private static final DecimalFormat DECIMAL_FORMAT_PRECISE = new DecimalFormat(
			"0.####",
			new DecimalFormatSymbols(Locale.US)
	);
	static {
		DECIMAL_FORMAT.setGroupingUsed(false);
		DECIMAL_FORMAT_PRECISE.setGroupingUsed(false);
	}

	private static final String UNKNOWN_PLAYER = "[unknown]";

	public static String format(double number) {
		return Unsafe.assertNonNull(DECIMAL_FORMAT.format(number));
	}

	public static String formatPrecise(double number) {
		return Unsafe.assertNonNull(DECIMAL_FORMAT_PRECISE.format(number));
	}

	public static String getLocationString(Location location) {
		return getLocationString(
				LocationUtils.getWorld(location).getName(),
				location.getX(),
				location.getY(),
				location.getZ()
		);
	}

	public static String getLocationString(Block block) {
		Validate.notNull(block, "block is null");
		return getLocationString(
				block.getWorld().getName(),
				block.getX(),
				block.getY(),
				block.getZ()
		);
	}

	public static String getLocationString(BlockLocation blockLocation) {
		Validate.notNull(blockLocation, "blockLocation is null");
		return getLocationString(
				blockLocation.getWorldName(),
				blockLocation.getX(),
				blockLocation.getY(),
				blockLocation.getZ()
		);
	}

	public static String getLocationString(BlockLocation blockLocation, double yaw) {
		Validate.notNull(blockLocation, "blockLocation is null");
		return getLocationString(
				blockLocation.getWorldName(),
				blockLocation.getX(),
				blockLocation.getY(),
				blockLocation.getZ(),
				yaw
		);
	}

	// More performant variant if coordinates are integers:
	public static String getLocationString(@Nullable String worldName, int x, int y, int z) {
		return worldName + "," + x + "," + y + "," + z;
	}

	public static String getLocationString(
			@Nullable String worldName,
			int x,
			int y,
			int z,
			double yaw
	) {
		return worldName + "," + x + "," + y + "," + z + "," + DECIMAL_FORMAT.format(yaw);
	}

	public static String getLocationString(
			@Nullable String worldName,
			double x,
			double y,
			double z
	) {
		return worldName
				+ "," + DECIMAL_FORMAT.format(x)
				+ "," + DECIMAL_FORMAT.format(y)
				+ "," + DECIMAL_FORMAT.format(z);
	}

	public static String getChunkString(ChunkCoords chunk) {
		Validate.notNull(chunk, "chunk is null");
		return getChunkString(chunk.getWorldName(), chunk.getChunkX(), chunk.getChunkZ());
	}

	public static String getChunkString(Chunk chunk) {
		Validate.notNull(chunk, "chunk is null");
		return getChunkString(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
	}

	public static String getChunkString(String worldName, int cx, int cz) {
		return worldName + "," + cx + "," + cz;
	}

	public static String getPlayerString(Player player) {
		Validate.notNull(player, "player is null");
		return getPlayerString(player.getName(), player.getUniqueId());
	}

	public static String getPlayerString(@Nullable String playerName, @Nullable UUID playerUUID) {
		// Either of them might be null.
		if (playerName != null) {
			return playerName + (playerUUID == null ? "" : " (" + playerUUID + ")");
		} else if (playerUUID != null) {
			return playerUUID.toString();
		} else {
			return UNKNOWN_PLAYER;
		}
	}

	public static String getPlayerNameOrUUID(
			@Nullable String playerName,
			@Nullable UUID playerUUID
	) {
		// Either of them might be null.
		// Prefer name, else use uuid.
		if (playerName != null) {
			return playerName;
		} else if (playerUUID != null) {
			return playerUUID.toString();
		} else {
			return UNKNOWN_PLAYER;
		}
	}

	public static String getPlayerNameOrUnknown(@Nullable String playerName) {
		return getPlayerNameOrUUID(playerName, null);
	}

	public static final char COLOR_CHAR_ALTERNATIVE = '&';
	private static final Pattern STRIP_COLOR_ALTERNATIVE_PATTERN = Pattern.compile(
			"(?i)" + COLOR_CHAR_ALTERNATIVE + "[0-9A-FK-ORX]"
	);
	private static final String COLOR_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";

	public static boolean isAnyColorChar(char c) {
		return c == ChatColor.COLOR_CHAR || c == COLOR_CHAR_ALTERNATIVE;
	}

	// Only checks for the Minecraft color code character, not the alternative color code character.
	public static boolean containsColorChar(String text) {
		return StringUtils.contains(text, ChatColor.COLOR_CHAR);
	}

	// Reverse of ChatColor#translateAlternateColorCodes:
	public static String translateColorCodesToAlternative(char altColorChar, String text) {
		Validate.notNull(text, "text is null");
		char[] c = text.toCharArray();
		for (int i = 0; i < c.length - 1; i++) {
			if (c[i] == ChatColor.COLOR_CHAR && COLOR_CODES.indexOf(c[i + 1]) > -1) {
				c[i] = altColorChar;
				c[i + 1] = Character.toLowerCase(c[i + 1]);
			}
		}
		return new String(c);
	}

	public static String stripColor(String text) {
		Validate.notNull(text, "text is null");
		if (text.isEmpty()) return text;
		String uncolored = Unsafe.assertNonNull(ChatColor.stripColor(text));
		uncolored = STRIP_COLOR_ALTERNATIVE_PATTERN.matcher(uncolored).replaceAll("");
		return uncolored;
	}

	public static String decolorize(String text) {
		Validate.notNull(text, "text is null");
		if (text.isEmpty()) return text;
		return translateColorCodesToAlternative(COLOR_CHAR_ALTERNATIVE, text);
	}

	public static List<String> decolorize(List<? extends String> list) {
		Validate.notNull(list, "list is null");
		List<String> decolored = new ArrayList<>(list.size());
		for (String string : list) {
			decolored.add(translateColorCodesToAlternative(COLOR_CHAR_ALTERNATIVE, string));
		}
		return decolored;
	}

	// Decolorizes String entries, otherwise adopts them as they are:
	public static List<?> decolorizeUnknown(List<?> list) {
		Validate.notNull(list, "list is null");
		List<@Nullable Object> decolored = new ArrayList<>(list.size());
		for (Object entry : list) {
			Object decolorizedEntry = entry;
			if (entry instanceof String) {
				decolorizedEntry = translateColorCodesToAlternative(
						COLOR_CHAR_ALTERNATIVE,
						(String) entry
				);
			}
			decolored.add(decolorizedEntry);
		}
		return decolored;
	}

	/**
	 * Translates {@code &}-based color codes to Minecraft's {@code §}-based color codes.
	 * 
	 * @param text
	 *            the text with {@code &}-based color codes, not <code>null</code>
	 * @return the text with Minecraft's color codes
	 */
	public static String colorize(String text) {
		Validate.notNull(text, "text is null");
		if (text.isEmpty()) return text;
		return ChatColor.translateAlternateColorCodes(COLOR_CHAR_ALTERNATIVE, text);
	}

	/**
	 * Translates {@code &}-based color codes to Minecraft's {@code §}-based color codes.
	 * 
	 * @param list
	 *            the texts with {@code &}-based color codes, not <code>null</code>
	 * @return a new list containing the corresponding texts with Minecraft's color codes
	 */
	public static List<String> colorize(List<? extends String> list) {
		Validate.notNull(list, "list is null");
		List<String> colored = new ArrayList<>(list.size());
		for (String text : list) {
			colored.add(colorize(text));
		}
		return colored;
	}

	// Colorizes String entries, otherwise adopts them as they are:
	public static List<?> colorizeUnknown(List<?> list) {
		Validate.notNull(list, "list is null");
		List<@Nullable Object> colored = new ArrayList<>(list.size());
		for (Object entry : list) {
			Object colorizedEntry = entry;
			if (entry instanceof String) {
				colorizedEntry = colorize((String) entry);
			}
			colored.add(colorizedEntry);
		}
		return colored;
	}

	// HEX COLORS

	public static final char HEX_CHAR = '#';
	private static final String HEX_DIGITS = "0123456789AaBbCcDdEeFf";

	private static boolean isHexDigit(char c) {
		return (HEX_DIGITS.indexOf(c) != -1);
	}

	// "#aabbcc"
	public static boolean isHexCode(String string) {
		if (string.length() != 7) return false;
		if (string.charAt(0) != '#') return false;
		for (int i = 1; i < 7; i++) {
			char c = string.charAt(i);
			if (!isHexDigit(c)) return false;
		}
		return true;
	}

	// "&x&a&a&b&b&c&c"
	public static boolean isBukkitHexCode(String string) {
		if (string.length() != 14) return false;

		if (!isAnyColorChar(string.charAt(0))) return false;
		char c1 = string.charAt(1);
		if (c1 != 'x' && c1 != 'X') return false;

		for (int i = 2; i < 14; i += 2) {
			if (!isAnyColorChar(string.charAt(i))) return false;
			if (!isHexDigit(string.charAt(i + 1))) return false;
		}
		return true;
	}

	// Converts from "#aabbcc" to "&x&a&a&b&b&c&c" (using the given formatting code)
	public static String toBukkitHexCode(String hexCode, char formattingCode) {
		Validate.notEmpty(hexCode, "hexCode is null or empty");
		Validate.isTrue(hexCode.length() == 7, () -> "Invalid hexCode: " + hexCode);
		StringBuilder bukkitHexCode = new StringBuilder(14);
		bukkitHexCode.append(formattingCode).append('x');
		for (int i = 1; i < 7; i++) {
			char c = hexCode.charAt(i);
			bukkitHexCode.append(formattingCode).append(c);
		}
		return bukkitHexCode.toString();
	}

	// Converts all "&#aabbcc" to "&x&a&a&b&b&c&c" (uses the found color code character)
	public static String convertHexColorsToBukkit(String text) {
		Validate.notNull(text, "text is null");
		StringBuilder result = new StringBuilder(text);
		for (int i = 0; i < result.length() - 1; i++) {
			char colorChar = result.charAt(i);
			if (!isAnyColorChar(colorChar)) continue;

			if (result.charAt(i + 1) != '#') continue;
			if (i + 7 >= result.length()) continue;
			if (!isHexCode(result.substring(i + 1, i + 8))) continue;

			result.setCharAt(i, colorChar);
			result.setCharAt(i + 1, 'x');
			for (int j = 2; j < 14; j += 2) {
				result.insert(i + j, colorChar);
			}
			i += 13;
		}
		return result.toString();
	}

	// Converts from "&x&a&a&b&b&c&c" or "§x§a§a§b§b§c§c" to "#aabbcc"
	public static String fromBukkitHexCode(String bukkitHexCode) {
		Validate.notEmpty(bukkitHexCode, "bukkitHexCode is null or empty");
		Validate.isTrue(bukkitHexCode.length() == 14,
				() -> "Invalid bukkitHexCode: " + bukkitHexCode);
		StringBuilder hexCode = new StringBuilder(7);
		hexCode.append('#');
		for (int i = 3; i < 14; i += 2) {
			char c = bukkitHexCode.charAt(i);
			hexCode.append(c);
		}
		return hexCode.toString();
	}

	// Wraps the individual texts in the list.
	// Tries to wrap at whitespace, but falls back to wrapping at the length limit.
	// Color and formatting codes do not count towards the limit and are preserved in the next line.
	public static void wrap(List<String> source, int maxVisibleLength) {
		var iterator = source.listIterator();

		var activeFormat = new StringBuilder();
		var currentLine = new StringBuilder();

		while (iterator.hasNext()) {
			String line = Unsafe.assertNonNull(iterator.next());
			if (getVisibleLength(line) <= maxVisibleLength) {
				continue;
			}

			iterator.remove();

			activeFormat.setLength(0);
			currentLine.setLength(0);

			int visibleLength = 0;
			int lastWhitespaceIndex = -1;
			int lastWhitespaceVisibleLength = -1;

			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);

				// Formatting code:
				if (c == ChatColor.COLOR_CHAR) {
					currentLine.append(c);
					activeFormat.append(c);

					i++;
					if (i < line.length()) {
						char code = line.charAt(i);
						var chatColor = ChatColor.getByChar(code);
						if (chatColor != null
								&& (chatColor.isColor() || chatColor == ChatColor.RESET)) {
							// Reset the active format at colors and reset:
							activeFormat.setLength(0);
							activeFormat.append(c);
						}
						currentLine.append(code);
						activeFormat.append(code);
					}
					continue;
				}

				// Track whitespace for word wrap:
				if (Character.isWhitespace(c)) {
					lastWhitespaceIndex = currentLine.length();
					lastWhitespaceVisibleLength = visibleLength;
				}

				currentLine.append(c);
				visibleLength++;

				if (visibleLength >= maxVisibleLength) {
					if (lastWhitespaceIndex != -1) {
						// Word wrap at last whitespace:
						iterator.add(currentLine.substring(0, lastWhitespaceIndex));

						// Remainder without the whitespace:
						String remainder = currentLine.substring(lastWhitespaceIndex + 1);
						currentLine.setLength(0);
						currentLine.append(activeFormat).append(remainder);

						visibleLength -= (lastWhitespaceVisibleLength + 1);
						lastWhitespaceIndex = -1;
						lastWhitespaceVisibleLength = -1;
					} else {
						// Hard wrap:
						iterator.add(currentLine.toString());
						currentLine.setLength(0);
						currentLine.append(activeFormat);
						visibleLength = 0;
					}
				}
			}

			if (currentLine.length() > 0) {
				iterator.add(currentLine.toString());
			}
		}
	}

	// Calculates the visible length (ignores formatting codes)
	private static int getVisibleLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == ChatColor.COLOR_CHAR) {
				// The color char is not counted and neither is the subsequent char:
				i++;
				continue;
			}

			length++;
		}
		return length;
	}

	// SENDING

	public static void sendMessage(CommandSender recipient, String message) {
		Validate.notNull(recipient, "recipient is null");
		Validate.notNull(message, "message is null");
		// Skip sending if message is empty: Allows disabling of messages.
		if (message.isEmpty()) return;

		// Send (potentially multi-line) message:
		for (String line : StringUtils.splitLines(message, true)) {
			recipient.sendMessage(line);
		}
	}

	public static void sendMessage(
			CommandSender recipient,
			String message,
			Map<? extends String, @NonNull ?> arguments
	) {
		// Replace message arguments and then send:
		sendMessage(recipient, StringUtils.replaceArguments(message, arguments));
	}

	public static void sendMessage(
			CommandSender recipient,
			String message,
			@NonNull Object... argumentPairs
	) {
		// Replace message arguments and then send:
		sendMessage(recipient, StringUtils.replaceArguments(message, argumentPairs));
	}

	private TextUtils() {
	}
}
